package ai.gleamer.ugly;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Game {
    public static final int GAME_BOARD = 12;
    public static final int PLAYER_LIMIT = 6;

    private static final int QUESTION_SIZE = 50;
    private static final int GOLD_FOR_WIN = 6;
    private final Questions questions;
    private final Board board = new Board();

    private Player currentPlayer;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        // Génération des catégories
        Map<String, List<Integer>> categories = new LinkedHashMap<>(); // conserve l'ordre
        categories.put("Rock", List.of());
        categories.put("Pop", List.of(0, 4, 8));
        categories.put("Science", List.of(1, 5, 9));
        categories.put("Sports", List.of(2, 6, 10));
        System.out.println(categories);
        // Génération des questions
        questions = new Questions(categories, QUESTION_SIZE);
    }

    public boolean isPlayable() {
        return board.getPlayerCount() >= 2;
    }

    public boolean addPlayer(String playerName) {
        boolean addedPlayer = board.addPlayer(playerName);
        if (currentPlayer == null) {
            currentPlayer = board.getCurrentPlayer();
        }
        return addedPlayer;
    }

    public void goToNextPlayer() {
        this.currentPlayer = board.goToNextPlayer();
    }

    public boolean roll(int roll) {
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                movePlayer(currentPlayer, roll);
                return true;
            } else {
                isGettingOutOfPenaltyBox = false;
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
                return false;
            }
        } else {
            movePlayer(currentPlayer, roll);
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
        System.out.println("Answer was correct!!!!");
        currentPlayer.addGoldToPurse();

        if (currentPlayer.isInPenaltyBox() && isGettingOutOfPenaltyBox) {
            currentPlayer.setInPenaltyBox(false);
        }
        return didPlayerWin(currentPlayer);
    }

    public void wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);
    }

    private boolean didPlayerWin(Player player) {
        return player.getPurse() >= GOLD_FOR_WIN && !player.isInPenaltyBox();
    }

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }
}
