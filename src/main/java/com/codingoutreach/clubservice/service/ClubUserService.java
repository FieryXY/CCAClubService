package com.codingoutreach.clubservice.service;

import com.codingoutreach.clubservice.controllers.DO.ClubCreationRequest;
import com.codingoutreach.clubservice.repository.ClubRepository;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.ClubUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClubUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final static String TOO_MANY_USERS_MSG = "too many users associated with email %s";
    private final ClubRepository clubRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<ClubUser> club = clubRepository.findByUsername(username);
        if (club.size() == 0) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
        } else if (club.size() != 1) {
            throw new UsernameNotFoundException(String.format(TOO_MANY_USERS_MSG, username));
        }

        return club.get(0);
    }

    public Club signUpUser(ClubCreationRequest clubCreationRequest) {
        boolean isValidEmail = emailService.test(clubCreationRequest.getUsername()); // So far only returns true
        if (!isValidEmail) {
            throw new IllegalStateException("Username not valid");
        }
        boolean userExists = (clubRepository.findByUsername(clubCreationRequest.getUsername()).size() != 0);

        if (userExists) {
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(clubCreationRequest.getPassword());

        UUID club_id = UUID.randomUUID();

        Club clubNew = new Club(club_id, clubCreationRequest.getUsername(), clubCreationRequest.getEmail(), encodedPassword, clubCreationRequest.getName(),
                                clubCreationRequest.getDescription(), clubCreationRequest.getMeet_time(), "templink");

        clubRepository.createNewClub(clubNew);

        return clubNew;
    }
}
