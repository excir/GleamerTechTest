package ai.gleamer.ugly;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Game {
    public static final int GAME_BOARD = 12;

    private static final int PLAYER_LIMIT = 6;
    private static final int QUESTION_SIZE = 50;
    private static final int GOLD_FOR_WIN = 6;
    final List<Player> players = new ArrayList<>();
    private final Questions questions;
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        Map<String, List<Integer>> categories = new LinkedHashMap<>(); // conserve l'ordre
        categories.put("Rock", List.of());
        categories.put("Pop", List.of(0, 4, 8));
        categories.put("Science", List.of(1, 5, 9));
        categories.put("Sports", List.of(2, 6, 10));
        System.out.println(categories);
        questions = new Questions(categories, QUESTION_SIZE);
    }

    public boolean isPlayable() {
        return this.players.size() >= 2;
    }

    public boolean addPlayer(String playerName) {
        if (players.size() < PLAYER_LIMIT) {
            players.add(new Player(playerName));
            System.out.println("There is " + players.size() + " players");
            return true;
        }
        return false;
    }

    public void goToNextPlayer() {
        currentPlayer++;
        if (currentPlayer >= players.size()) currentPlayer = 0;
    }

    public boolean roll(int roll) {
        Player player = players.get(currentPlayer);
        System.out.println(player.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(player.getName() + " is getting out of the penalty box");
                movePlayer(player, roll);
                return true;
            } else {
                isGettingOutOfPenaltyBox = false;
                System.out.println(player.getName() + " is not getting out of the penalty box");
                return false;
            }
        } else {
            movePlayer(player, roll);
            return true;
        }
    }

    private void movePlayer(Player player, int roll) {
        player.movePlayer(roll);
        String category = questions.getCurrentCategoryFromPosition(player.getPlace());
        askQuestion(category);
    }

    private void askQuestion(String category) {
        System.out.println(questions.getNextQuestionInCategory(category));
    }

    public boolean correctAnswer() {
        Player player = players.get(currentPlayer);
        System.out.println("Answer was correct!!!!");
        player.addGoldToPurse();

        if (player.isInPenaltyBox() && isGettingOutOfPenaltyBox) {
            player.setInPenaltyBox(false);
        }
        return didPlayerWin(player);
    }

    public void wrongAnswer() {
        Player player = players.get(currentPlayer);
        System.out.println("Question was incorrectly answered");
        System.out.println(player.getName() + " was sent to the penalty box");
        player.setInPenaltyBox(true);
    }

    private boolean didPlayerWin(Player player) {
        return player.getPurse() >= GOLD_FOR_WIN && !player.isInPenaltyBox();
    }

    public String getCurrentPlayerName() {
        Player player = players.get(currentPlayer);
        return player.getName();
    }
}
