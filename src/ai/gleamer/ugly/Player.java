package ai.gleamer.ugly;

import static ai.gleamer.ugly.Game.GAME_BOARD;

public class Player {
    private final String name;
    private int place = 0;
    private int purse = 0;
    private boolean inPenaltyBox = false;

    public Player(String name) {
        this.name = name;
        System.out.println(name + " was added");
    }

    public void movePlayer(int roll) {
        this.place += roll;
        if (this.place >= GAME_BOARD) {
            this.place -= GAME_BOARD;
        }
        System.out.println(this.name + "'s new location is " + this.place);
    }

    public void addGoldToPurse() {
        this.purse++;
        System.out.println(this.name + " now has " + this.purse + " Gold Coins.");
    }

    public String getName() {
        return name;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }


}
