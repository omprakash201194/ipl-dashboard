package com.omprakashgautam.ipldashboard.data;

import com.omprakashgautam.ipldashboard.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * @author omprakash gautam
 * Created on 11-Jun-21 at 9:57 PM.
 */
@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            Map<String, Team> teamData = new HashMap<>();

            entityManager.createQuery("select m.team1 , count(*) from Match m group by m.team1", Object[].class)
                    .getResultList().stream().map(t -> new Team((String)t[0],(long) t[1])).forEach(team -> teamData.put(team.getTeamName(), team));

            entityManager.createQuery("select m.team2 , count(*) from Match m group by m.team2", Object[].class)
                    .getResultList().forEach(te -> {
                        Team team = teamData.get((String)te[0]);
                        team.setTotalMatches(team.getTotalMatches() + (long) te[1]);
                    });

            entityManager.createQuery("select m.winner, count(*) from Match m group by m.winner", Object[].class)
                    .getResultList()
                    .forEach(te -> {
                        Team t = teamData.get((String)te[0]);
                        if(t != null )t.setTotalWins((long) te[1]);
                    });

            teamData.values().forEach(entityManager::persist);
            teamData.values().forEach(System.out::println);
        }
    }
}
