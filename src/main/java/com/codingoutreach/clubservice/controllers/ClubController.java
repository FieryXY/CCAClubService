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
    @RequestMapping(path="/password/reset/request")
    public void resetPasswordCreate(@RequestBody Email body) {
        clubService.resetPasswordCreate(body.getUsername());

    }

    @CrossOrigin
    @GetMapping
    @RequestMapping(path="/password/reset/verify")
    public UUID resetPasswordVerify(@RequestBody PasswordCodeVerificationRequest request) {
        return clubService.verifyPasswordCode(request);
    }

    @CrossOrigin
    @PostMapping
    @RequestMapping(path="/password/reset")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
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
