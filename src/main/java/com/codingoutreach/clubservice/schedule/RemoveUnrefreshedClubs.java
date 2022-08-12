package com.codingoutreach.clubservice.schedule;

import com.codingoutreach.clubservice.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;

import java.time.Instant;
import java.util.List;

public class RemoveUnrefreshedClubs implements ApplicationRunner {


    @Autowired
    ClubService clubService;

    @Value("${earliest_refresh_date}")
    private String earliestRefreshDate;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> argList = args.getOptionValues("should-remove-unrefreshed-clubs");
        if(argList.size() > 0 && argList.get(0).equals("true")) {
            Instant earliestRefreshDateInstant = Instant.parse(earliestRefreshDate);
            clubService.deleteUnrefreshedClubs(earliestRefreshDateInstant);
        }
    }
}
