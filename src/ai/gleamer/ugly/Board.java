package ai.gleamer.ugly;

import java.util.ArrayList;
import java.util.List;

import static ai.gleamer.ugly.Game.PLAYER_LIMIT;

public class Board {
    final List<Player> players = new ArrayList<>();
    int currentPlayer = 0;

    public boolean addPlayer(String playerName) {
        if (players.size() < PLAYER_LIMIT) {
            players.add(new Player(playerName));
            System.out.println("There is " + players.size() + " players");
            return true;
        }
        return false;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public Player goToNextPlayer() {
        currentPlayer++;
        if (currentPlayer >= players.size()) currentPlayer = 0;
        return players.get(currentPlayer);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }
}
