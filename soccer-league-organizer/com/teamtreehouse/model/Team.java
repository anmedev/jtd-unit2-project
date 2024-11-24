package com.teamtreehouse.model;
import java.util.*;

public class Team {
    // Private String field that stores the team's name
    private String teamName;
    // Private String field that stores the coach's name
    private String coachName;
    // Private List field that stores all the teams in the soccer league
    private List<Player> playersList;

    // Constructor to initialize the Team object
    public Team(String teamName, String coachName) {
        this.teamName = teamName;
        this.coachName = coachName;
        this.playersList = new ArrayList<>();
    }

    // Getter method to retrieve the team's name
    public String getTeamName() {
        return teamName;
    }
    // Getter method to retrieve the coach's name
    public String getCoachName() {
        return coachName;
    }

    // Getter method to retrieve the players on a team
    public List<Player> getPlayersList() {
        return playersList;
    }

    // Checks if a team exceeds 11 players
    public boolean addPlayer(Player player) {
        return (playersList.size() >= 11) ? false : playersList.add(player);
    }

    @Override
    public String toString() {
        return String.format("Team: %s, Coach: %s %n", teamName, coachName);
    }
}
