package com.codingoutreach.clubservice.service;


import com.codingoutreach.clubservice.repository.DTO.Category;
import com.codingoutreach.clubservice.security.JWTUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.codingoutreach.clubservice.dos.ClubInformation;
import com.codingoutreach.clubservice.dos.ClubSocialDO;
import com.codingoutreach.clubservice.repository.ClubRepository;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.ClubSocial;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }


    public List<ClubInformation> getAllClubs() {
        List<Club> club = clubRepository.getAllClubs();
        List<ClubInformation> clubs = new ArrayList<>();
        for (Club i : club) {
            clubs.add(getClubInformationByClubId(i.getClubID()));
        }
        return clubs;
    }
    
    public ClubInformation getClubInformationByClubId(UUID clubId) {
        Club club = clubRepository.getClubByClubId(clubId);
        List<ClubSocial> clubSocials = clubRepository.getClubSocialsByClubId(clubId);
        List<String> categories = clubRepository.getClubCategoriesByClubId(clubId);

        List<ClubSocialDO> clubSocialDOs = clubSocials.stream().map(clubSocial -> new ClubSocialDO(clubSocial.getSocialName(), clubSocial.getSocialLink())).collect(Collectors.toList());

        return new ClubInformation(
                club.getClubID(),
                club.getName(),
                club.getDescription(),
                club.getProfilePictureUrl(),
                clubSocialDOs,
                categories
        );
    }

    public List<String> getAllTags() {
        List<Category> categories = clubRepository.getAllCategories();

        return categories.stream().map(Category::getCategoryName).collect(Collectors.toList());
    }

    public void editSocials(String socialName, String socialLink, UUID socialId) {
        clubRepository.editSocials(socialName, socialLink, socialId);
    }

    public void addSocials(String socialName, String socialLink, UUID clubId) {
        clubRepository.addSocials(socialName, socialLink, clubId);
    }

    public void editTitle(String title, UUID clubId) {
        clubRepository.editTitle(title, clubId);
    }

    public void editDescription(String description, UUID clubId) {
        clubRepository.editDescription(description, clubId);
    }

    public void addTags(String categoryName, UUID clubId) {
        clubRepository.addTags(categoryName, clubId);
    }

    public void removeTags(String categoryName, UUID clubId) {
        clubRepository.removeTags(categoryName, clubId);
    }

    public String getClubUsernameByClubId(UUID clubId) {
        return clubRepository.findClubUserByClubId(clubId.toString()).getUsername();
    }

    public List<Club> getClubUsernames() {
        return clubRepository.getAllClubs();
    }
}
