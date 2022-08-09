package com.codingoutreach.clubservice.repository;


import com.codingoutreach.clubservice.ClubApplication;
import com.codingoutreach.clubservice.controllers.DO.ResetPasswordCreationRequest;
import com.codingoutreach.clubservice.repository.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.codingoutreach.clubservice.repository.DTO.Club;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


@Repository
public class ClubRepository {
    // SQL Queries
    private final String GET_ALL_CLUBS = "SELECT * FROM club";

    private final String FIND_CLUB_USER_BY_USERNAME = "SELECT club_id, username, encoded_password FROM club WHERE username=?";

    private final String SAVE_CLUB = "INSERT INTO club VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final String FIND_CLUB_USER_BY_ID = "SELECT club_id, username, encoded_password FROM club WHERE club_id=?";

    private final String GET_CLUB_BY_ID_SQL = "SELECT * FROM club WHERE club_id=?";
    
    private final String GET_SOCIALS_BY_CLUB_ID_SQL = "SELECT * FROM socials WHERE club_id=?";
    
    private final String GET_CATEGORIES_BY_CLUB_ID_SQL = "SELECT category.category_name FROM club_categories LEFT JOIN category ON club_categories.category_id=category.category_id WHERE club_id=?";
    
    private final String GET_ALL_CATEGORIES = "SELECT * FROM category";

    private final String EDIT_SOCIALS = "UPDATE socials " +
                                        "SET social_name=?, social_link=? WHERE social_id=?";

    private final String ADD_SOCIALS = "INSERT INTO socials VALUES (?, ?, ?, ?)";

    private final String EDIT_TITLE = "UPDATE club " +
                                      "SET name=? WHERE club_id=?";

    private final String EDIT_DESCRIPTION = "UPDATE club " +
                                            "SET description=? WHERE club_id=?";

    private final String ADD_TAGS = "INSERT INTO club_categories VALUES (?, ?, ?)";

    private final String GET_TAG_ID = "SELECT * FROM category WHERE category_name=?";

    private final String REMOVE_TAGS = "DELETE FROM club_categories WHERE club_id=? AND category_id=?";

    private final String REMOVE_SOCIAL = "DELETE FROM socials WHERE club_id=? AND social_name=?";

    private final String SOCIAL_EXISTS = "SELECT * FROM socials WHERE club_id=? AND social_name=?";

    private final String RESET_PASSWORD = "UPDATE club " +
                                          "SET password=? WHERE club_id=?";

    private final String VALID_EMAIL = "SELECT 1 FROM club WHERE email=?";
    private final String GET_FEATURED_CLUBS = "SELECT * FROM featured_clubs";

    private final String INSERT_RESET_PASSWORD = "INSERT INTO reset_password_requests VALUES (?, ?, ?, ?)";

    private final String GET_RESET_PASSWORD_BY_CLUB_ID = "SELECT * FROM reset_password_requests WHERE club_id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClubRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Row Mappers

    public RowMapper<Category> mapCategory() {
        return (resultSet, i) -> {
            UUID categoryId = UUID.fromString(resultSet.getString("category_id"));
            String categoryName = resultSet.getString("category_name");
            return new Category(categoryId, categoryName);
        };
    }

    public RowMapper<ClubCategory> mapClubCategory() {
        return (resultSet, i) -> {
            UUID clubCategoryId = UUID.fromString(resultSet.getString("id"));
            UUID clubId = UUID.fromString(resultSet.getString("club_id"));
            UUID categoryId = UUID.fromString(resultSet.getString("category_id"));

            return new ClubCategory(clubCategoryId, clubId, categoryId);
        };
    }

    public RowMapper<ClubSocial> mapClubSocial() {
        return (resultSet, i) -> {
            UUID clubSocialId = UUID.fromString(resultSet.getString("social_id"));
            UUID clubId = UUID.fromString(resultSet.getString("club_id"));
            String socialName = resultSet.getString("social_name");
            String socialLink = resultSet.getString("social_link");

            return new ClubSocial(clubSocialId, clubId, socialName, socialLink);
        };
    }




    // Turns a column of data into Club object
    // Turns a column of data into Club object
    public RowMapper<Club> mapClub() {
        return ((resultSet, i) -> {
            UUID clubID = UUID.fromString(resultSet.getString("club_id"));
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");
            String encoded_password = resultSet.getString("encoded_password");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String profile_picture_url = resultSet.getString("profile_picture_url");
            String meet_time = resultSet.getString("meet_time");
            return new Club(clubID, username, email, encoded_password, name, description, meet_time, profile_picture_url);
        });
    }


    // Login
    public RowMapper<ClubUser> mapLogin() {
        return ((resultSet, i) -> {
            UUID clubID = UUID.fromString(resultSet.getString("club_id"));
            String username = resultSet.getString("username");
            String encoded_password = resultSet.getString("encoded_password");
            return new ClubUser(clubID, username, encoded_password);
        });
    }

    public RowMapper<FeaturedClubInformation> mapFeaturedClubs() {
        return ((resultSet, i) -> {
            UUID clubId = UUID.fromString(resultSet.getString("club_id"));
            String textContent = resultSet.getString("text_content");
            String mediaURL = resultSet.getString("media_url");

            return new FeaturedClubInformation(clubId, textContent, mediaURL);
        });
    }

    public RowMapper<ResetPasswordCreationRequest> mapResetPasswordRequest() {
        return ((resultSet, i) -> {
            UUID requestId = UUID.fromString(resultSet.getString("request_id"));
            UUID clubId = UUID.fromString(resultSet.getString("club_id"));
            String resetCode = resultSet.getString("reset_code");
            Instant expirationDate = Instant.parse(resultSet.getString("expiration_date"));
            return new ResetPasswordCreationRequest(requestId, clubId, resetCode, expirationDate);
        });
    }


    public List<Club> getAllClubs() {
        return jdbcTemplate.query(GET_ALL_CLUBS, mapClub());
    }

    public List<ClubUser> findByUsername(String username) {
        return jdbcTemplate.query(FIND_CLUB_USER_BY_USERNAME, new Object[] {username}, mapLogin());
    }

    public ClubUser findClubUserByClubId(String clubId) {
        return jdbcTemplate.queryForObject(FIND_CLUB_USER_BY_ID, new Object[]{clubId}, mapLogin());
    }

    public int createNewClub(Club club) {
        return jdbcTemplate.update(SAVE_CLUB, club.getClubID(), club.getUsername(), club.getEmail(), club.getEncodedPassword(),
                club.getName(), club.getDescription(), club.getMeetTime(),
                club.getProfilePictureUrl());
    }
    
    public Club getClubByClubId(UUID clubId) {
        return jdbcTemplate.queryForObject(GET_CLUB_BY_ID_SQL, new Object[]{clubId}, mapClub());
    }

    public List<ClubSocial> getClubSocialsByClubId(UUID clubId) {
        return jdbcTemplate.query(GET_SOCIALS_BY_CLUB_ID_SQL, new Object[]{clubId}, mapClubSocial());
    }

    public List<String> getClubCategoriesByClubId(UUID clubId) {
        return jdbcTemplate.query(GET_CATEGORIES_BY_CLUB_ID_SQL, new Object[]{clubId}, (resultSet, i) -> resultSet.getString("category_name"));
    }

    public List<Category> getAllCategories() {
        return jdbcTemplate.query(GET_ALL_CATEGORIES, mapCategory());
    }

    public void editSocials(String socialName, String socialLink, UUID socialId) {
        jdbcTemplate.update(EDIT_SOCIALS, socialName, socialLink, socialId);
    }

    public void addSocials(String socialName, String socialLink, UUID clubId) {
        UUID socialId = UUID.randomUUID();
        jdbcTemplate.update(ADD_SOCIALS, socialId, clubId, socialName, socialLink);
    }

    public void editTitle(String title, UUID clubId) {
        jdbcTemplate.update(EDIT_TITLE, title, clubId);
    }

    public void editDescription(String description, UUID clubId) {
        jdbcTemplate.update(EDIT_DESCRIPTION, description, clubId);
    }

    public UUID getTagId(String categoryName) {
        List<Category> temp = jdbcTemplate.query(GET_TAG_ID, new Object[] {categoryName}, mapCategory());
        return temp.get(0).getCategoryId();
    }

    public void addTags(String categoryName, UUID clubId) {
        UUID id = UUID.randomUUID();
        UUID categoryId = getTagId(categoryName);
        jdbcTemplate.update(ADD_TAGS, id, clubId, categoryId);
    }

    public void removeTags(String categoryName, UUID clubId) {
        UUID categoryId = getTagId(categoryName);
        jdbcTemplate.update(REMOVE_TAGS, clubId, categoryId);
    }

    public void removeSocial(UUID clubId, String socialName) {
        jdbcTemplate.update(REMOVE_SOCIAL, clubId, socialName);
    }

    //Check if Social Exists for Club ID by Social Name
    public UUID getSocialIdForSocialName(String socialName, UUID clubId) {
        List<ClubSocial> temp = jdbcTemplate.query(SOCIAL_EXISTS, new Object[] {clubId, socialName}, mapClubSocial());
        if (temp.size() == 0) {
            return null;
        }
        return temp.get(0).getClubSocialId();
    }

    public void resetPassword(UUID clubId, String password) {
        jdbcTemplate.update(RESET_PASSWORD, password, clubId);
    }

    public List<Club> checkEmail(String email) {
        return jdbcTemplate.query(VALID_EMAIL, new Object[]{email}, mapClub());
    }
    public List<FeaturedClubInformation> getFeaturedClubs() {
        return jdbcTemplate.query(GET_FEATURED_CLUBS, mapFeaturedClubs());
    }


    public int insertResetPasswordRequest(UUID clubId, String resetCode) {

        Instant expirationInstant = Instant.now().plus(ClubApplication.SECONDS_UNTIL_PASSWORD_REQUEST_EXPIRATION,
                ChronoUnit.SECONDS);

        //Expiration Instant to Timestamp
        Timestamp expirationTimestamp = Timestamp.from(expirationInstant);

        return jdbcTemplate.update(INSERT_RESET_PASSWORD, UUID.randomUUID(), clubId, resetCode,
                expirationTimestamp);

    }

    //Get Reset Password Request
    public List<ResetPasswordCreationRequest> getResetPasswordRequests(UUID clubId) {
        return jdbcTemplate.query(GET_RESET_PASSWORD_BY_CLUB_ID, new Object[]{clubId}, mapResetPasswordRequest());
    }




}

