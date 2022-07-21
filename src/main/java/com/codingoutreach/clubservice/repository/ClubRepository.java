package com.codingoutreach.clubservice.repository;


import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.ClubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.codingoutreach.clubservice.repository.DTO.Category;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.ClubCategory;
import com.codingoutreach.clubservice.repository.DTO.ClubSocial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



import java.util.List;
import java.util.UUID;


@Repository
public class ClubRepository {
    //SQL Queries
    private final String GET_ALL_CLUBS = "SELECT * FROM club";

    private final String FIND_CLUB_USER_BY_EMAIL = "SELECT club_id, email, encoded_password FROM club WHERE email=?";

    private final String SAVE_CLUB = "INSERT INTO club VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String FIND_CLUB_USER_BY_ID = "SELECT club_id, email, encoded_password FROM club WHERE club_id=?";
    
    private final String GET_CLUB_BY_ID_SQL = "SELECT * FROM club WHERE club_id=?";
    
    private final String GET_SOCIALS_BY_CLUB_ID_SQL = "SELECT * FROM socials WHERE club_id=?";
    
    private final String GET_CATEGORIES_BY_CLUB_ID_SQL = "SELECT category.category_name FROM club_categories LEFT JOIN category ON club_categories.category_id=category.category_id WHERE club_id=?";
    
    private final String GET_ALL_CATEGORIES = "SELECT * FROM category";



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClubRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Club> getAllClubs() {
        return jdbcTemplate.query(GET_ALL_CLUBS, mapClub());
    }

    public List<ClubUser> findByEmail(String email) {
        return jdbcTemplate.query(FIND_CLUB_USER_BY_EMAIL, new Object[] {email}, mapLogin());
    }

    public List<ClubUser> findClubById(UUID clubId) {
        return jdbcTemplate.query(FIND_CLUB_USER_BY_ID, new Object[]{clubId}, mapLogin());
    }

    public int createNewClub(Club club) {
        return jdbcTemplate.update(SAVE_CLUB, club.getClubID(), club.getEmail(), club.getEncodedPassword(),
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
            String email = resultSet.getString("email");
            String encoded_password = resultSet.getString("encoded_password");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String profile_picture_url = resultSet.getString("profile_picture_url");
            String meet_time = resultSet.getString("meet_time");
            return new Club(clubID, email, encoded_password, name, description, meet_time, profile_picture_url);
        });
    }


    // Login
    public RowMapper<ClubUser> mapLogin() {
        return ((resultSet, i) -> {
           UUID clubID = UUID.fromString(resultSet.getString("club_id"));
           String email = resultSet.getString("email");
           String encoded_password = resultSet.getString("encoded_password");
           return new ClubUser(clubID, email, encoded_password);
        });
    }

}
