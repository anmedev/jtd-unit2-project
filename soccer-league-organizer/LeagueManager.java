import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.TeamsList;

public class LeagueManager {

  public static void main(String[] args) {

    // Load players from the Players class
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);

    /* Initialize the TeamList which will handle team management
    (creating teams, adding/removing players from a team, etc.) */
     TeamsList teamList = new TeamsList();

    // Executes the Soccer League Organizer
    teamList.run();
  }
}