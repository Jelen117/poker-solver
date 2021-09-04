package model;

import java.util.Arrays;

public class Player implements Comparable<Player> {

    public Card[] getStartingHand() {
        return startingHand;
    }

    private final Card[] startingHand;
    private final String textStartingHand;

    public Player(String textStartingHand, Card[] startingHand) {
        this.textStartingHand = textStartingHand;
        this.startingHand = startingHand;
    }


    public String getTextStartingHand() {
        return textStartingHand;
    }

    @Override
    public String toString() {
        return "model.Player{" +
                "startingHand=" + Arrays.toString(startingHand) +
                '}';
    }

    @Override
    public int compareTo(Player o) {
        return this.textStartingHand.compareTo(o.getTextStartingHand());
    }
}
