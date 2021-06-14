package com.omprakashgautam.ipldashboard.controller;

import com.omprakashgautam.ipldashboard.model.Match;
import com.omprakashgautam.ipldashboard.model.Team;
import com.omprakashgautam.ipldashboard.repository.MatchRepository;
import com.omprakashgautam.ipldashboard.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author omprakash gautam
 * Created on 12-Jun-21 at 1:58 PM.
 */
@RestController
@Slf4j
@CrossOrigin
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    @Autowired
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Optional<Team> optionalTeam = teamRepository.findByTeamName(teamName);
        if (optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            List<Match> allByTeam1OrTeam2 = matchRepository.findLatestMatchesByTeam(teamName, 4);
            team.setMatches(allByTeam1OrTeam2);
            return team;
        }
        return null;
    }

    @GetMapping("/team")
    public List<Team> getTeams(){
        return teamRepository.findAll();
    }

    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year){
        LocalDate startDate = LocalDate.of(year,1,1);
        LocalDate endDate = LocalDate.of(year + 1,1,1);
        return matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
    }
}
