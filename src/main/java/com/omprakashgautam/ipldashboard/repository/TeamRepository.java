package com.omprakashgautam.ipldashboard.repository;

import com.omprakashgautam.ipldashboard.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author omprakash gautam
 * Created on 12-Jun-21 at 2:00 PM.
 */
@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    Optional<Team> findByTeamName(String teamName);

}
