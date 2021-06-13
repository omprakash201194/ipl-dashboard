package com.omprakashgautam.ipldashboard.repository;

import com.omprakashgautam.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findAllByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    //List<Match> findAllByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(String team1, LocalDate startDate1,
    // LocalDate endDate1,String team2, LocalDate startDate2, LocalDate endDate2);

    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :startDate and " +
            ":endDate  order by date desc")
    List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    default List<Match> findLatestMatchesByTeam(String teamName, int count) {
        return findAllByTeam1OrTeam2OrderByDateDesc(teamName,teamName, PageRequest.of(0, count));
    }
}
