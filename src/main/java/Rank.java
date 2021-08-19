public enum Rank {
    Ace(14), King(13), Queen(12), Jack(11), Ten(10), Nine(9), Eight(8),
    Seven(7), Six(6), Five(5), Four(4), Three(3), Deuce(2);
    private int value;
    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
