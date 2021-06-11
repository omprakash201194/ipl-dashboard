package com.omprakashgautam.ipldashboard.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author omprakash gautam
 * Created on 11-Jun-21 at 10:15 PM.
 */
@Entity
@Data
public class Team {

    @Id
    private long id;
    private String teamName;
    private long totalMatches;
    private long totalWins;
}
