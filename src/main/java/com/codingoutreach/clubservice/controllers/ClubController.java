package com.codingoutreach.clubservice.controllers;

import com.codingoutreach.clubservice.controllers.DO.SocialCreationRequest;
import com.codingoutreach.clubservice.models.Description;
import com.codingoutreach.clubservice.models.SocialCredentials;
import com.codingoutreach.clubservice.models.Tags;
import com.codingoutreach.clubservice.models.Title;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.dos.ClubInformation;
import com.codingoutreach.clubservice.security.JWTUtil;
import com.codingoutreach.clubservice.service.ClubService;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void removeSocials(@RequestBody SocialCredentials body, @PathVariable("clubId") UUID clubId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!isValidUser(clubId, token)) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Unauthorized to edit this club's page"
            );
        }
        clubService.removeSocial(body);
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
