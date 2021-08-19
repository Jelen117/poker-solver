//package main.java;

import java.util.ArrayList;

public class Game {
    public PokerVariantInterface getVariant() {
        return variant;
    }

    public void setVariant(PokerVariantInterface variant) {
        this.variant = variant;
    }

    private PokerVariantInterface variant;
    private GameType gameType;
    private Card[] board;
    private ArrayList<Player> players;

    public Game() {
        players = new ArrayList<Player>();
    }

    public GameType getGameType() {
        return gameType;
    }

    public Card[] getBoard() {
        return board;
    }

    public void setBoard(Card[] board) {
        this.board = board;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
