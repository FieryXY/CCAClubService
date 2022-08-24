package com.codingoutreach.clubservice.repository;

import com.codingoutreach.clubservice.repository.DTO.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Repository
public class ImageRepository {
    private final JdbcTemplate jdbcTemplate;
    private ClubRepository clubRepository;
    @Autowired
    public ImageRepository(JdbcTemplate jdbcTemplate, ClubRepository clubRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.clubRepository = clubRepository;
    }

    private final String UPLOAD_PROFILE_PICTURE = "UPDATE club " +
                                                  "SET profile_picture_url=? WHERE club_id=?";

    public void uploadProfilePicture(String path, UUID clubId) {
        jdbcTemplate.update(UPLOAD_PROFILE_PICTURE, path, clubId);
    }

    public byte[] getProfilePicture(UUID clubId) {
        Club club = clubRepository.getClubByClubId(clubId);
        String pfpUrl = "./src/main/resources/static/" + club.getProfilePictureUrl();
        try {
            BufferedImage pfpImage = ImageIO.read(new File(pfpUrl));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(pfpImage, pfpUrl.substring(pfpUrl.length() - 3), baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Image Not Found", e);
        }
    }



}
