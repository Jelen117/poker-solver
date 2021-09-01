import org.checkerframework.checker.nullness.Opt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.reverseOrder;

public class Game {
    public PokerVariantInterface getVariant() {
        return variant;
    }

    public void setVariant(PokerVariantInterface variant) {
        this.variant = variant;
    }

    private PokerVariantInterface variant;
    private GameType gameType;
    private Optional<Card[]> board;
    private ArrayList<Player> players;

    public Game() {
        players = new ArrayList<Player>();
    }

    public GameType getGameType() {
        return gameType;
    }

    public Optional<Card[]> getBoard() {
        return board;
    }

    public void setBoard(Optional<Card[]> board) {
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

    public String giveResult(){
        List<Map.Entry<Player, Hand>> handsList = players.stream().map((player) -> Map.entry(player, variant.findBestHand(board, player.getStartingHand()))).toList();
//        System.out.println(handsList.get(0).compareTo(handsList.get(1)));
        List<Map.Entry<Player, Hand>> entries = handsList.stream().sorted(Map.Entry.comparingByValue(Hand::compareTo)).toList();
//        System.out.println("dupa");
//        entries.forEach(System.out::println);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size() - 1; ++i){
            sb.append(entries.get(i).getKey().getTextStartingHand());
            if (entries.get(i).getValue().compareTo(entries.get(i+1).getValue()) < 0)
                sb.append(" ");
            else
                sb.append("=");
        }
        sb.append(entries.get(entries.size() - 1).getKey().getTextStartingHand());
        return sb.toString();
    }
}
