package model;

import java.util.Comparator;

public class Card implements Comparable<Card> {
    private Color color;
    private Rank rank;

    public Card(Color color, Rank rank) {
        this.color = color;
        this.rank = rank;
    }

    public Card() {
    }

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

    @Override
    public String toString() {
        return "model.Card{" +
                "color=" + color +
                ", rank=" + rank +
                '}';
    }

    @Override
    public int compareTo(Card card) {
        return Comparator.comparing(Card::getRank).reversed().compare(this, card);
    }
}
