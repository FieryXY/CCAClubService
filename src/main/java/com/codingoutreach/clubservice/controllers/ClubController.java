package com.codingoutreach.clubservice.controllers;

import com.codingoutreach.clubservice.controllers.DO.PasswordCodeVerificationRequest;
import com.codingoutreach.clubservice.controllers.DO.ResetPasswordRequest;
import com.codingoutreach.clubservice.controllers.DO.SocialCreationRequest;
import com.codingoutreach.clubservice.dos.FeaturedClubInformationDO;
import com.codingoutreach.clubservice.models.*;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.dos.ClubInformation;
import com.codingoutreach.clubservice.security.JWTUtil;
import com.codingoutreach.clubservice.service.ClubService;
import javassist.NotFoundException;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping(path ="/api/club")
@CrossOrigin
public class ClubController {

    private ClubService clubService;
    private JWTUtil jwtUtil;

    @Autowired
    public ClubController(ClubService clubService, JWTUtil jwtUtil) {
        this.clubService = clubService;
        this.jwtUtil = jwtUtil;
    }

    public boolean isValidUser(UUID clubId, String token) {
        String username = clubService.getClubUsernameByClubId(clubId);
        return username.equals(jwtUtil.validateTokenAndRetrieveSubject(token.substring(7)));
    }
//     Get All Clubs
    @CrossOrigin
    @GetMapping
    @RequestMapping(path = "/list")
    public List<ClubInformation> getClubs() {
        return clubService.getAllClubs();
    }

    @CrossOrigin
    @GetMapping
    @RequestMapping(path="/tags/list")
    public List<String> getAllTags() {
        return clubService.getAllTags();
    }

    @GetMapping
    @RequestMapping(path="/featured")
    public List<FeaturedClubInformationDO> getFeaturedClubs() {
        return clubService.getFeaturedClubs();
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/edit/socials/change/{clubId}")
    public void editSocials(@RequestBody SocialCredentials body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }
        clubService.editSocials(body.getSocialName(), body.getSocialLink(), body.getSocialId());
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/edit/socials/remove/{clubId}")
    public void removeSocials(@RequestBody SocialCreationRequest body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }
        clubService.removeSocial(clubId, body);
    }



    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/edit/title/{clubId}")
    public void editTitle(@RequestBody Title body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }
        clubService.editTitle(body.getName(), clubId);
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/edit/description/{clubId}")
    public void editDescription(@RequestBody Description body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }
        clubService.editDescription(body.getDescription(), clubId);
    }


    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/edit/socials/add/{clubId}")
    public void addSocials(@RequestBody SocialCreationRequest body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }

        if(body.getSocialLink().trim().length() == 0) {
            clubService.removeSocial(clubId, body);
        }

        clubService.addSocials(body.getSocialName(), body.getSocialLink(), clubId);
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/edit/tags/add/{clubId}")
    public void addTags(@RequestBody Tags body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }
        clubService.addTags(body.getCategoryName(), clubId);
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/edit/tags/remove/{clubId}")
    public void removeTags(@RequestBody Tags body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }
        clubService.removeTags(body.getCategoryName(), clubId);
    }

    @CrossOrigin
    @GetMapping
    @RequestMapping(path="/list/username")
    public HashMap<String, Integer> getClubUsernames() {
        List<Club> clubs = clubService.getClubUsernames();
        HashMap<String, Integer> usernames = new HashMap<>();
        for (Club i : clubs) {
            usernames.put(i.getUsername(), 1);
        }
        return usernames;
    }


    @Autowired
    private JavaMailSender javaMailSender;

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/password/reset/email")
    public void resetPasswordEmail(@RequestBody Email body) throws MessagingException{
        if (!clubService.checkEmail(body.getEmail())) {
            throw new IllegalArgumentException("Email Not Found");
        } else {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = body.getText();
            helper.setText(htmlMsg, true);
            helper.setFrom("coding.outreach@gmail.com");
            helper.setTo(body.getEmail());
            helper.setSubject(body.getSubject());
            javaMailSender.send(mimeMessage);
        }
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/password/reset/request/{clubId}")
    public void resetPasswordCreate(@PathVariable("clubId") UUID clubId) {
        clubService.resetPasswordCreate(clubId);

    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/password/reset/verify/{clubId}")
    public void resetPasswordVerify(@PathVariable("clubId") UUID clubId, @RequestBody PasswordCodeVerificationRequest request) {
        clubService.verifyPasswordCode(request);
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/password/reset/{clubId}")
    public void resetPassword(@PathVariable("clubId") UUID clubId, @RequestBody ResetPasswordRequest request) {
        clubService.resetPassword(request);
    }

    /**
     * @param clubId ID of Club
     * @return All information needed to load Club Profile page for club identified with {@code clubId}
     */

    @CrossOrigin
    @GetMapping
    @RequestMapping(path="/information/{clubId}")
    public ClubInformation getClubInformationByClubId(@PathVariable("clubId") UUID clubId) {
        return clubService.getClubInformationByClubId(clubId);
    }
}
