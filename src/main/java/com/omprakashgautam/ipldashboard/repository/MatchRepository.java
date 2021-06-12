package com.omprakashgautam.ipldashboard.repository;

import com.omprakashgautam.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findAllByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    default List<Match> findLatestMatchesByTeam(String teamName, int count) {
        return findAllByTeam1OrTeam2OrderByDateDesc(teamName,teamName, PageRequest.of(0, count));
    }
}
