package com.codingoutreach.clubservice.controllers;
import com.codingoutreach.clubservice.controllers.DO.ClubCreationRequest;
import com.codingoutreach.clubservice.models.LoginCredentials;
import com.codingoutreach.clubservice.repository.ClubRepository;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.ClubUser;
import com.codingoutreach.clubservice.security.JWTUtil;
import com.codingoutreach.clubservice.service.ClubUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private ClubRepository clubRepository;
    @Autowired private ClubUserService clubUserService;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @CrossOrigin
    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody ClubCreationRequest user){
        Club userClub = clubUserService.signUpUser(user);
        String token = jwtUtil.generateToken(userClub.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

    @CrossOrigin
    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());


            List<ClubUser> clubUserList = clubRepository.findByEmail(body.getEmail());
            if (clubUserList.size() == 0) {
                // Returns email failure
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Email");
            } else if (clubUserList.size() != 1) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Too many emails");
            }
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(clubUserList.get(0).getEmail());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc){
            // Returns password failure
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Password");
        }
    }


}