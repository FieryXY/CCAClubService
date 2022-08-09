package com.codingoutreach.clubservice.controllers;


import com.codingoutreach.clubservice.controllers.DO.ImageInsertionRequest;
import com.codingoutreach.clubservice.repository.ImageRepository;
import com.codingoutreach.clubservice.security.JWTUtil;
import com.codingoutreach.clubservice.service.ClubService;
import com.codingoutreach.clubservice.service.ImageService;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@CrossOrigin()
@RequestMapping("/api/file")
public class ImageController {

    @Autowired
    ImageService imageService;

    private ClubService clubService;
    private JWTUtil jwtUtil;

    @Autowired
    public ImageController(ClubService clubService, JWTUtil jwtUtil) {
        this.clubService = clubService;
        this.jwtUtil = jwtUtil;
    }

    public boolean isValidUser(UUID clubId, String token) {
        String username = clubService.getClubUsernameByClubId(clubId);
        return username.equals(jwtUtil.validateTokenAndRetrieveSubject(token.substring(7)));
    }

    @PostMapping
    @RequestMapping("/upload/pfp/{clubId}")
    public void uploadProfilePicture(@PathVariable("clubId") UUID clubId, @RequestParam("pfp") MultipartFile file, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws IOException {

        if(!isValidUser(clubId, token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String fileName = file.getOriginalFilename();
        String pathName = "img/pfp/" + clubId + fileName.substring(fileName.length() - 4);
        Path path = Paths.get("./src/main/resources/static/" + pathName);
        Files.write(path, file.getBytes());
        imageService.uploadProfilePicture(pathName, clubId);
    }

    @GetMapping
    @RequestMapping("/get/pfp/{clubId}")
    public byte[] getProfilePicture(@PathVariable("clubId") UUID clubId) {
        return imageService.getProfilePicture(clubId);
    }
}
