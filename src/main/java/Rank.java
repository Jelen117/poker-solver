public enum Rank {
    Deuce(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9),
    Ten(10), Jack(11), Queen(12), King(13), Ace(14);
//    Ace(14), King(13), Queen(12), Jack(11);
    private final int value;
    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
