package com.teamtreehouse.model;

import java.io.IOException;
import java.util.*;

public class TeamsList {
    private List<Team> teams;
    Scanner scanner = new Scanner(System.in);

    public TeamsList() {
        this.teams = new ArrayList<>();
    }

    public void run() {
        String userSelection;
        do {
            System.out.println("\nMenu");
            System.out.println("Create - Create a new team");
            System.out.println("Add - Add a player to the team");
            System.out.println("Remove - Remove a player from a team");
            System.out.println("Report - View a report of a team by height");
            System.out.println("Balance - View the League Balance Report");
            System.out.println("Roster - View roster");
            System.out.println("Quit - Exits the program");
            System.out.print("\nSelect an option: ");

            try {
                userSelection = scanner.nextLine();
                switch (userSelection.toLowerCase()) { // Handle input case insensitivity
                    case "create":
                        createTeam();
                        break;
                    case "add":
                        addPlayerToTeam();
                        break;
                    case "remove":
                        removePlayerFromTeam();
                        break;
                    case "report":
                        viewHeightReport();
                        break;
                    case "balance":
                        viewLeagueBalanceReport();
                        break;
                    case "roster":
                        viewTeamRoster();
                        break;
                    case "quit":
                        System.out.println("Thank you for using our program!");
                        return; // This line will end the program, so we should leave it as is for quitting.
                    default:
                        System.out.printf("Invalid choice. Try again. %n");
                }
            } catch (IOException ioe) {
                System.out.printf("An error occurred. Please try again. %n");
                ioe.printStackTrace();
            }
        } while (true); // Ensures the loop continues until "quit" is selected
    }


    // Helper method to display teams in alphabetical order
    private void displayTeamsAlphabetically() {
        // Sort the teams list alphabetically by team name
        teams.sort(Comparator.comparing(Team::getTeamName));

        System.out.println("\nAvailable Teams:");
        for (int i = 0; i < teams.size(); i++) {
            System.out.printf("%d.) %s coached by %s%n", i + 1, teams.get(i).getTeamName(), teams.get(i).getCoachName());
        }
    }

    // Helper method to display players in alphabetical order
    private void displayPlayersAlphabetically(List<Player> players) {
        players.sort(Player::compareTo);

        System.out.println("\nAvailable Players:");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.printf("%d.) %s %s (%d inches - %s)%n",
                    i + 1,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches(),
                    player.isPreviousExperience() ? "experienced" : "inexperienced");
        }
    }

    // Method to view a height report for a selected team
    private void viewHeightReport() {
        if (teams.isEmpty()) {
            System.out.println("No teams available. Please create a team first.");
            return;
        }

        // Display teams alphabetically
        displayTeamsAlphabetically();

        System.out.print("Select a team (enter number): ");
        int teamIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (teamIndex < 0 || teamIndex >= teams.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return;
        }

        Team selectedTeam = teams.get(teamIndex);
        List<Player> players = selectedTeam.getPlayersList();

        if (players.isEmpty()) {
            System.out.println("No players on this team.");
            return;
        }

        // Group players by height
        Map<String, List<Player>> groupedPlayers = groupPlayersByHeight(players);

        // Display the height report
        System.out.printf("Height Report for Team: %s%n", selectedTeam.getTeamName());
        for (Map.Entry<String, List<Player>> entry : groupedPlayers.entrySet()) {
            System.out.println(entry.getKey());
            List<Player> heightGroup = entry.getValue();
            for (Player player : heightGroup) {
                System.out.printf("    %d.) %s %s (%d inches - %s)%n",
                        players.indexOf(player) + 1,
                        player.getFirstName(),
                        player.getLastName(),
                        player.getHeightInInches(),
                        player.isPreviousExperience() ? "experienced" : "inexperienced");
            }
        }
        // The method finishes, and control will go back to the main menu because it's not returning or exiting.
    }

    // Method to view the roster of a selected team
    private void viewTeamRoster() {
        if (teams.isEmpty()) {
            System.out.println("No teams available. Please create a team first.");
            return;
        }

        // Display teams alphabetically
        displayTeamsAlphabetically();

        System.out.print("Select a team (enter number): ");
        int teamIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (teamIndex < 0 || teamIndex >= teams.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return;
        }

        Team selectedTeam = teams.get(teamIndex);
        List<Player> players = selectedTeam.getPlayersList();

        if (players.isEmpty()) {
            System.out.println("No players on this team.");
            return;
        }

        // Display the team and player roster
        System.out.printf("\nRoster for Team: %s coached by %s%n", selectedTeam.getTeamName(), selectedTeam.getCoachName());
        for (Player player : players) {
            System.out.printf("%s %s (Height: %d inches, Experience Level: %s)%n",
                    player.getFirstName(), player.getLastName(),
                    player.getHeightInInches(),
                    player.isPreviousExperience() ? "Experienced" : "Inexperienced");
        }
    }

    // New method to generate and display the league balance report
    private void viewLeagueBalanceReport() {
        if (teams.isEmpty()) {
            System.out.println("No teams available. Please create a team first.");
            return;
        }


        Map<String, Map<String, Integer>> leagueBalance = new LinkedHashMap<>();

        for (Team team : teams) {
            int experiencedCount = 0;
            int inexperiencedCount = 0;

            for (Player player : team.getPlayersList()) {
                if (player.isPreviousExperience()) {
                    experiencedCount++;
                } else {
                    inexperiencedCount++;
                }
            }

            // Store counts in the map
            Map<String, Integer> counts = new HashMap<>();
            counts.put("Experienced", experiencedCount);
            counts.put("Inexperienced", inexperiencedCount);
            leagueBalance.put(team.getTeamName(), counts);
        }

        // Display the report
        System.out.println("\nLeague Balance Report:");
        for (Map.Entry<String, Map<String, Integer>> entry : leagueBalance.entrySet()) {
            String teamName = entry.getKey();
            Map<String, Integer> counts = entry.getValue();
            System.out.printf("Team: %s - Experienced: %d, Inexperienced: %d%n",
                    teamName, counts.get("Experienced"), counts.get("Inexperienced"));
        }
    }


    private void createTeam() throws IOException {
            System.out.print("What is the team name? ");
            String teamName = scanner.nextLine();
            System.out.print("What is the coach's name? ");
            String coachName = scanner.nextLine();

            Team newTeam = new Team(teamName, coachName);
            teams.add(newTeam);
            System.out.printf("Team %s coached by %s added %n", teamName, coachName);
        }

        // Method to add a player to a team
        private void addPlayerToTeam() {
            if (teams.isEmpty()) {
                System.out.println("No teams available. Please create a team first.");
                return;
            }

            // Display teams alphabetically
            displayTeamsAlphabetically();

        System.out.print("\nSelect a team (enter number): ");
        int teamIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (teamIndex < 0 || teamIndex >= teams.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return;
        }
        Team selectedTeam = teams.get(teamIndex);

        // Filter available players (not already in teams)
        List<Player> availablePlayers = getAvailablePlayers();
        if (availablePlayers.isEmpty()) {
            System.out.println("No available players to add.");
            return;
        }

        // Displays players in alphabetical order
        displayPlayersAlphabetically(availablePlayers);


        System.out.print("\nSelect a player to add (enter number): ");
        int playerIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (playerIndex < 0 || playerIndex >= availablePlayers.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return;
        }
        Player selectedPlayer = availablePlayers.get(playerIndex);

        // Add player to team
        if (selectedTeam.addPlayer(selectedPlayer)) {
            System.out.printf("Player %s %s added to team %s coached by %s.%n",
                    selectedPlayer.getFirstName(),
                    selectedPlayer.getLastName(),
                    selectedTeam.getTeamName(),
                    selectedTeam.getCoachName());
        } else {
            System.out.println("Team is already full. Player not added.");
        }
    }

    // Method to remove player from team
    private void removePlayerFromTeam() {
        if (teams.isEmpty()) {
            System.out.println("No teams available. Please create a team first.");
            return;
        }

        // Display teams in alphabetical order
        displayTeamsAlphabetically();

        System.out.print("Select a team (enter number): ");
        int teamIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (teamIndex < 0 || teamIndex >= teams.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return;
        }

        Team selectedTeam = teams.get(teamIndex);

        // Display players in the selected team
        List<Player> players = selectedTeam.getPlayersList();
        if (players.isEmpty()) {
            System.out.println("No players on this team to remove.");
            return;
        }

        // Displays players in alphabetical order
        displayPlayersAlphabetically(players);

        // Prompt for player selection
        System.out.print("Select a player to remove (enter number): ");
        int playerIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (playerIndex < 0 || playerIndex >= players.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return;
        }

        // Remove the selected player
        Player removedPlayer = players.remove(playerIndex);
        System.out.printf("Player %s %s has been removed from team %s.%n",
                removedPlayer.getFirstName(),
                removedPlayer.getLastName(),
                selectedTeam.getTeamName());
    }



    // Helper method to find unassigned players
    private List<Player> getAvailablePlayers() {
        List<Player> availablePlayers = new ArrayList<>();
        Player[] allPlayers = Players.load();

        // Check if player is already in a team
        for (Player player : allPlayers) {
            boolean isAssigned = false;
            for (Team team : teams) {
                if (team.getPlayersList().contains(player)) {
                    isAssigned = true;
                    break;
                }
            }
            if (!isAssigned) {
                availablePlayers.add(player);
            }
        }
        return availablePlayers;
    }

    private Map<String, List<Player>> groupPlayersByHeight(List<Player> players) {
        Map<String, List<Player>> heightGroups = new LinkedHashMap<>();
        heightGroups.put("35-40 inches", new ArrayList<>());
        heightGroups.put("41-46 inches", new ArrayList<>());
        heightGroups.put("47-50 inches", new ArrayList<>());

        for (Player player : players) {
            int height = player.getHeightInInches();
            if (height >= 35 && height <= 40) {
                heightGroups.get("35-40 inches").add(player);
            } else if (height >= 41 && height <= 46) {
                heightGroups.get("41-46 inches").add(player);
            } else if (height >= 47 && height <= 50) {
                heightGroups.get("47-50 inches").add(player);
            }
        }
        return heightGroups;
    }


}

