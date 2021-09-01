import java.util.Comparator;

public class Card implements Comparable<Card> {
    private Color color;
    private Rank rank;
    private String textCard;

    private CardOrigin cardOrigin;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getTextCard() {
        return textCard;
    }

    public void setTextCard(String textCard) {
        this.textCard = textCard;
    }

    public CardOrigin getCardOrigin() {
        return cardOrigin;
    }

    public void setCardOrigin(CardOrigin cardOrigin) {
        this.cardOrigin = cardOrigin;
    }

    @Override
    public String toString() {
        return "Card{" +
                "color=" + color +
                ", rank=" + rank +
                '}';
    }

    @Override
    public int compareTo(Card card) {
        return Comparator.comparing(Card::getRank).reversed().compare(this, card);
    }
}
