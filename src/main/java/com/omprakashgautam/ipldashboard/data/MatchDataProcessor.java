package com.omprakashgautam.ipldashboard.data;

import com.omprakashgautam.ipldashboard.model.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

@Slf4j
public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        Match transformedMatch = new Match();
        transformedMatch.setId(Long.parseLong(matchInput.getId()));
        transformedMatch.setCity(matchInput.getCity());

        transformedMatch.setDate(LocalDate.parse(matchInput.getDate()));
        transformedMatch.setPlayerOfMatch(matchInput.getPlayer_of_match());
        transformedMatch.setVenue(matchInput.getVenue());

        //Set Team 1 and Team 2 depending on the innings order
        String firstInningTeam, secondInningTeam;

        if("bat".equalsIgnoreCase(matchInput.getToss_decision())) {
            firstInningTeam = matchInput.getToss_winner();
            secondInningTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            secondInningTeam = matchInput.getToss_winner();
            firstInningTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        }

        transformedMatch.setTeam1(firstInningTeam);
        transformedMatch.setTeam2(secondInningTeam);

        transformedMatch.setTossWinner(matchInput.getToss_winner());
        transformedMatch.setWinner(matchInput.getWinner());
        transformedMatch.setTossDecision(matchInput.getToss_decision());
        transformedMatch.setResult(matchInput.getResult());
        transformedMatch.setResultMargin(matchInput.getResult_margin());

        transformedMatch.setUmpire1(matchInput.getUmpire1());
        transformedMatch.setUmpire2(matchInput.getUmpire2());

        log.info("Converting (" + matchInput + ") into (" + transformedMatch + ")");

        return transformedMatch;
    }
}
