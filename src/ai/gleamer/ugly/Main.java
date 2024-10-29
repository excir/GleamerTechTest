package ai.gleamer.ugly;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int getRand(int start, int end) {
        return (int) (Math.random() * (end - start + 1));
    }

    public static void main(String[] args) {
        Game game = new Game();
        boolean inGame = true;
        List<Boolean> playersInserted = new ArrayList<>();
        playersInserted.add(game.addPlayer("ONE"));
        playersInserted.add(game.addPlayer("TWO"));
        playersInserted.add(game.addPlayer("THREE"));
        playersInserted.add(game.addPlayer("FOUR"));
        playersInserted.add(game.addPlayer("FIVE"));
        playersInserted.add(game.addPlayer("SIX"));
//        playersInserted.add(game.add("SEVEN"));
        if (playersInserted.stream().anyMatch(b -> !b)) {
            System.out.println("Cannot insert all players");
            return;
        }

        if (game.isPlayable()) {
            System.out.println("Jeux jouable");
            System.out.println("-------");
            while (inGame) {
                boolean playerHasMoved = game.roll(getRand(0, 6));
                if (playerHasMoved) {
                    int goodAnswer = getRand(0, 1);
                    if (goodAnswer == 1) {
                        if (game.correctAnswer()) {
                            System.out.println("The winner is : " + game.getCurrentPlayerName());
                            inGame = false;
                        }
                    } else {
                        game.wrongAnswer();
                    }
                }
                game.goToNextPlayer();
                System.out.println("----------");
            }
        } else {
            System.out.println("Jeux non jouable");
        }

    }
}