package com.codingoutreach.clubservice.service;


import com.codingoutreach.clubservice.models.SocialCredentials;
import com.codingoutreach.clubservice.repository.DTO.Category;
import com.codingoutreach.clubservice.security.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import com.codingoutreach.clubservice.dos.ClubInformation;
import com.codingoutreach.clubservice.dos.ClubSocialDO;
import com.codingoutreach.clubservice.repository.ClubRepository;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.ClubSocial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

        List<ClubSocialDO> clubSocialDOs = clubSocials.stream().map(clubSocial -> new ClubSocialDO(clubSocial.getClubSocialId(), clubSocial.getSocialName(), clubSocial.getSocialLink())).collect(Collectors.toList());

        String base64Image = "templink";

        if(club.getProfilePictureUrl() != null) {
            String path = "./src/main/resources/static/" + club.getProfilePictureUrl();
            //Read file from path as base64
            byte[] imageBytes = null;
            try {
                imageBytes = Files.readAllBytes(java.nio.file.Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            base64Image = Base64.getEncoder().encodeToString(imageBytes);

            String imageType = "image/" + club.getProfilePictureUrl().split("\\.")[1];
            base64Image = "data:" + imageType + ";base64," + base64Image;
        }

        System.out.println(base64Image);

        return new ClubInformation(
                club.getClubID(),
                club.getName(),
                club.getDescription(),
                base64Image,
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

        if (socialName.trim().length() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Social name cannot be empty"
            );
        }

        UUID socialId = clubRepository.getSocialIdForSocialName(socialName, clubId);

        if(socialId != null) {
            clubRepository.editSocials(socialName, socialLink, socialId);
        }
        else {
            clubRepository.addSocials(socialName, socialLink, clubId);
        }
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

    public void removeSocial(SocialCredentials social) {
        clubRepository.removeSocial(social.getSocialId());
    }
}
