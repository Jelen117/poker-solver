//import one.util.streamex.StreamEx;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.cartesianProduct;
import static java.util.Map.entry;

public class Parser {
    private static final Map<Character, Rank> ranksMap = Map.ofEntries(
            entry('A', Rank.Ace),
            entry('K', Rank.King),
            entry('Q', Rank.Queen),
            entry('J', Rank.Jack),
            entry('T', Rank.Ten),
            entry('9', Rank.Nine),
            entry('8', Rank.Eight),
            entry('7', Rank.Seven),
            entry('6', Rank.Six),
            entry('5', Rank.Five),
            entry('4', Rank.Four),
            entry('3', Rank.Three),
            entry('2', Rank.Deuce)
    );

    private static final Map<Character, Color> colorsMap = Map.ofEntries(
            entry('h', Color.Heart),
            entry('d', Color.Diamond),
            entry('c', Color.Club),
            entry('s', Color.Spade)
    );

    private static final Map<String, GameType> gameTypesMap = Map.ofEntries(
            entry("texas-holdem", GameType.Holdem),
            entry("omaha-holdem", GameType.Omaha),
            entry("five-card-draw", GameType.Draw)
    );

    private static final Map<GameType, PokerVariantInterface> variantsMap = Map.ofEntries(
            entry(GameType.Holdem, new TexasHoldemPokerVariant()),
            entry(GameType.Omaha, new OmahaHoldemPokerVariant()),
            entry(GameType.Draw, new FiveCardDrawPokerVariant())
    );

    public static Card[] parseCardsInput(String text){
        int textLength = text.length();
        Card[] hand = new Card[textLength/2];
        int i = 0, cardCounter = 0;
        while (i < textLength){
            hand[cardCounter] = new Card();
            hand[cardCounter].setRank(ranksMap.get(text.charAt(i)));
            ++i;
            hand[cardCounter].setColor(colorsMap.get(text.charAt(i)));
            ++i;
            ++cardCounter;
        }
        return hand;
    }

    public static ArrayList<Game> parseInput() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Game> games = new ArrayList<>();
        while (scanner.hasNextLine()){
            Game game = new Game();
            String[] hands;
            String[] line = scanner.nextLine().split(" ");
            game.setGameType(gameTypesMap.get(line[0]));
            if (game.getGameType() != GameType.Draw) {
                game.setBoard(Optional.of(parseCardsInput(line[1])));
                hands = Arrays.copyOfRange(line, 2, line.length);
            }
            else {
                game.setBoard(Optional.empty());
                hands = Arrays.copyOfRange(line, 1, line.length);
            }
            Arrays.stream(hands).forEach((hand) -> game.addPlayer(new Player(hand, parseCardsInput(hand))));
            game.setVariant(variantsMap.get(game.getGameType()));
            games.add(game);
        }
        return games;
    }

    public static void toString(ArrayList<Game> games){
        for (Game game : games){
            System.out.println(game.getGameType());
//            System.out.println(Arrays.toString(
            if (game.getBoard().isPresent())
                System.out.println(Arrays.toString(game.getBoard().get()));
//                    game.getBoard().ifPresent(()System.out::println);
//                    c.ifPresent(c -> System.out.println(c.get()));
            for (Player player : game.getPlayers()){
                System.out.println(player.toString());
            }

        }
    }
}
