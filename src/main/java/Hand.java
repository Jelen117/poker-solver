import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Hand implements Comparable<Hand> {
    public void setHandType(HandType handType) {
        this.handType = handType;
    }

    public HandType getHandType() {
        return handType;
    }

    private HandType handType;

    public Card[] getCards() {
        return cards;
    }

    private Card[] cards;

    public String getTextHand() {
        return textHand;
    }

    private String textHand;

    public Hand(HandType handType, Card[] cards) {
        this.handType = handType;
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "handType=" + handType +
                ", cards=" + Arrays.toString(cards) +
                ", textHand='" + textHand + '\'' +
                '}';
    }

    @Override
    public int compareTo(Hand hand) {
//        System.out.println("AAAAAAAAA");
        return Comparator.comparing(Hand::getHandType).thenComparing((o1, o2) -> {
            Card[] cards1 = o1.getCards();
            Card[] cards2 = o2.getCards();
            for (int i = 0; i < cards1.length; i++) {
                int result = cards1[i].compareTo(cards2[i]);
                if (result != 0) return result;
            }
            return 0;
        }
        ).compare(hand, this);
    }
}
