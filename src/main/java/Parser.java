//import one.util.streamex.StreamEx;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

//    public static Hand findBestHand(Card[] board, Card[] hand) {
//        List<Card> composition = Stream.concat(Arrays.stream(board), Arrays.stream(hand)).sorted(Card::compareTo).toList();
//        System.out.println(composition);
//        Hand HandHand = findFlushAndStraightFlush(composition);
//        System.out.println("eeeeee");
//        System.out.println(Arrays.toString(board));
//        findStraightOmaha(board, hand);
//        findGroups(composition);
//        return null;
//    }

//    private static Hand findFlushAndStraightFlush(List<Card> composition){
//        Map.Entry<Color, Long> aa = composition.stream().
//                collect(Collectors.groupingBy(Card::getColor, Collectors.counting())).
//                entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();
//        if (aa.getValue() >= 5){
//            List<Card> flushCards = composition.stream().filter(c -> c.getColor() == aa.getKey()).toList();
//            Hand straightFlush = findStraight(flushCards);
//            if (straightFlush != null){
//                straightFlush.setHandType(HandType.StraightFlush);
//                return straightFlush;
//            }
//        }
//        return null;
//    }
//
//    private static Hand findStraightOmaha(Card[] board, Card[] hand){
//        Set<Set<Card>> combinations1 = combinations(Sets.newHashSet(board), 3);
//        Set<Set<Card>> combinations2 = combinations(Sets.newHashSet(hand), 2);
//        Set<List<Set<Card>>> lista = cartesianProduct(combinations1, combinations2);
//        for (List<Set<Card>> s : lista){
//            List<Card> hand1 = Sets.newHashSet(Iterables.concat(s)).stream().sorted(Card::compareTo).collect(StraightCollector.splitCards());
//            if (hand1.size() == 5){
//                System.out.println("STREET");
//                System.out.println(hand1);
//            }
//
//        }
//        return null;
//    }

//    private static Hand findStraight(List<Card> composition) {
//        List<Card> straightLists = composition.stream()
//                .collect(StraightCollector.splitCards()).stream().toList();
//
//
//        System.out.println(straightLists);
//        if (straightLists.size() == 5){
//            new Hand(HandType.Straight, straightLists.toArray(new Card[5]));
//        }
//        return null;
//    }

//    private static Hand findGroups(List<Card> composition){
//        Map<Rank, Long> map =
//                composition.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting())).forEach((key, value) -> System.out.println(key + ": " + value));
//
//        return null;
//    }

    public static Card[] parseCardsInput(String text){
        int textLength = text.length();
        Card[] Hand = new Card[textLength/2];
        int i = 0, cardCounter = 0;
        while (i < textLength){
            Hand[cardCounter] = new Card();
            Hand[cardCounter].setRank(ranksMap.get(text.charAt(i)));
            ++i;
            Hand[cardCounter].setColor(colorsMap.get(text.charAt(i)));
            ++i;
            ++cardCounter;
        }
        System.out.println(Arrays.toString(Hand));
        return Hand;
    }

    public static ArrayList<Game> parseInput() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Game> games = new ArrayList<>();
        while (scanner.hasNextLine()){
            Game game = new Game();
            String[] line = scanner.nextLine().split(" ");
            game.setGameType(gameTypesMap.get(line[0]));
            game.setBoard(parseCardsInput(line[1]));
            String[] hands = Arrays.copyOfRange(line, 2, line.length);
            for (String hand : hands) {
                game.addPlayer(new Player(hand, parseCardsInput(hand)));
            }
            game.setVariant(variantsMap.get(game.getGameType()));
            games.add(game);

        }
        return games;
    }

    public static void toString(ArrayList<Game> games){
        for (Game game : games){
            System.out.println(game.getGameType());
            System.out.println(Arrays.toString(game.getBoard()));
            for (Player player : game.getPlayers()){
                System.out.println(player.toString());
            }

        }
    }

    public static String[] giveResult(Game game){
        game.getPlayers().forEach((player) -> game.getVariant().findBestHand(game.getBoard(), player.getStartingHand()));
        return null;
    }
}
