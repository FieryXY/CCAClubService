package com.codingoutreach.clubservice.repository;

import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.ClubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ClubRepository {
    private final String GET_ALL_CLUBS = "SELECT * FROM club";

    private final String FIND_CLUB_BY_EMAIL = "SELECT club_id, email, encoded_password FROM club WHERE email=?";

    private final String SAVE_CLUB = "INSERT INTO club VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String FIND_CLUB_BY_ID = "SELECT club_id, email, encoded_password FROM club WHERE club_id=?";

    private final JdbcTemplate jdbc;

    @Autowired
    public ClubRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public List<Club> getAllClubs() {
        return jdbc.query(GET_ALL_CLUBS, mapClub());
    }

    public List<ClubUser> findByEmail(String email) {
        return jdbc.query(FIND_CLUB_BY_EMAIL, new Object[] {email}, mapLogin());
    }

    public List<ClubUser> findClubById(UUID clubId) {
        return jdbc.query(FIND_CLUB_BY_ID, new Object[]{clubId}, mapLogin());
    }

    public int createNewClub(Club club) {
        return jdbc.update(SAVE_CLUB, club.getClubID(), club.getEmail(), club.getEncoded_password(),
                club.getName(), club.getDescription(), club.getMeet_time(),
                club.getProfile_picture_url());
    }





    // Turns a column of data into Club object
    public RowMapper<Club> mapClub() {
        return ((resultSet, i) -> {
            UUID clubID = UUID.fromString(resultSet.getString("club_id"));
            String email = resultSet.getString("email");
            String encoded_password = resultSet.getString("encoded_password");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String meet_time = resultSet.getString("meet_time");
            String profile_picture_url = resultSet.getString("profile_picture_url");
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
