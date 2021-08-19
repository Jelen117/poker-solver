import java.util.Arrays;

public class Player {
    private Card[] bestHand;

    public Card[] getStartingHand() {
        return startingHand;
    }

    private Card[] startingHand;
    private String textBestHand;
    private String textStartingHand;

    public Player(String textStartingHand, Card[] startingHand) {
        this.textStartingHand = textStartingHand;
        this.startingHand = startingHand;
    }


    public String getTextStartingHand() {
        return textStartingHand;
    }

    public void setTextStartingHand(String textStartingHand) {
        this.textStartingHand = textStartingHand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "startingHand=" + Arrays.toString(startingHand) +
                '}';
    }
}
