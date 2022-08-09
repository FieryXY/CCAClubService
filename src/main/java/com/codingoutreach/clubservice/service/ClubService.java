package com.codingoutreach.clubservice.service;


import com.codingoutreach.clubservice.ClubApplication;
import com.codingoutreach.clubservice.controllers.DO.PasswordCodeVerificationRequest;
import com.codingoutreach.clubservice.controllers.DO.ResetPasswordCreationRequest;
import com.codingoutreach.clubservice.controllers.DO.ResetPasswordRequest;
import com.codingoutreach.clubservice.controllers.DO.SocialCreationRequest;
import com.codingoutreach.clubservice.dos.FeaturedClubInformationDO;
import com.codingoutreach.clubservice.models.Email;
import com.codingoutreach.clubservice.repository.DTO.Category;
import com.codingoutreach.clubservice.repository.DTO.FeaturedClubInformation;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private ClubRepository clubRepository;

    @Autowired
    private JavaMailSender javaMailSender;

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

        String base64Image = null;

        if(club.getProfilePictureUrl() != null) {
            String path = "./src/main/resources/static/" + club.getProfilePictureUrl();
            //Read file from path as base64
            byte[] imageBytes = null;
            try {
                imageBytes = Files.readAllBytes(java.nio.file.Paths.get(path));
                base64Image = Base64.getEncoder().encodeToString(imageBytes);

                String imageType = "image/" + club.getProfilePictureUrl().split("\\.")[1];
                base64Image = "data:" + imageType + ";base64," + base64Image;
            } catch (IOException e) {
                System.out.println("Profile Picture Image Not Found");
            }
        }

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

    public void removeSocial(UUID clubId, SocialCreationRequest social) {
        clubRepository.removeSocial(clubId, social.getSocialName());
    }

    public List<FeaturedClubInformationDO> getFeaturedClubs() {
        List<FeaturedClubInformation> repoList = clubRepository.getFeaturedClubs();
        List<FeaturedClubInformationDO> toReturn = new ArrayList<FeaturedClubInformationDO>();
        for(FeaturedClubInformation info : repoList) {
            String clubName = clubRepository.getClubByClubId(info.getClubId()).getName();
            toReturn.add(new FeaturedClubInformationDO(info.getClubId(), clubName, info.getDescription(), info.getMediaURL()));
        }
        return toReturn;
    }

    public void resetPassword(UUID clubId, String password) {
        clubRepository.resetPassword(clubId, password);
    }



    public void resetPasswordCreate(String username) {
        List<Club> club = clubRepository.checkUsername(username);
        if (club.size() == 0) {
            throw new IllegalArgumentException("Username Not Found");
        }
        //Generate random 6 digit number code
        Random rand = new Random();
        String randCode = "";
        for (int i = 0; i < 6; i++) {
            randCode += rand.nextInt(10);
        }

        try {
            sendEmail(club.get(0).getEmail(), "Password Reset for " + club.get(0).getName(), "Your password reset link is: " + ClubApplication.WEBSITE_RESET_PASSWORD_URL + randCode);
        } catch (MessagingException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email could not be sent"
            );
        }
    }

    public boolean verifyPasswordCode(PasswordCodeVerificationRequest request) {
        List<ResetPasswordCreationRequest> passwordRequests = clubRepository.getResetPasswordRequests(request.getClubId());
        for(ResetPasswordCreationRequest r : passwordRequests) {
            if(r.getResetCode().equals(request.getResetCode())) {
                return true;
            }
        }
        return false;
    }

    public void resetPassword(ResetPasswordRequest request) {
        PasswordCodeVerificationRequest verificationRequest = new PasswordCodeVerificationRequest(request.getClubId(), request.getResetCode());
        if(verifyPasswordCode(verificationRequest)) {
            clubRepository.resetPassword(request.getClubId(), request.getNewPassword());
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid reset code"
            );
        }
    }

    public void sendEmail(String email, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(text, true);
        helper.setFrom("coding.outreach@gmail.com");
        helper.setTo(email);
        helper.setSubject(subject);
        javaMailSender.send(mimeMessage);
    }
}
