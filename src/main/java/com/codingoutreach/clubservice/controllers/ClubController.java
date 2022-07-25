package com.codingoutreach.clubservice.controllers;


import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.dos.ClubInformation;
import com.codingoutreach.clubservice.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;
import static org.springframework.web.bind.annotation.RequestMethod.GET;




@RestController
@RequestMapping(path ="/api/club")
@CrossOrigin
public class ClubController {

    private ClubService clubService;


    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }


//     Get All Clubs
    @GetMapping
    @RequestMapping(path = "/list")
    public List<Club> getClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping
    @RequestMapping(path="/tags/list")
    public List<String> getAllTags() {
        return clubService.getAllTags();
    }

    /**
     * @param clubId ID of Club
     * @return All information needed to load Club Profile page for club identified with {@code clubId}
     */
    @GetMapping
    @RequestMapping(path="/information/{clubId}")
    public ClubInformation getClubInformationByClubId(@PathVariable("id") UUID clubId) {
        return clubService.getClubInformationByClubId(clubId);
    }
}
