package controller;

import interfaces.FiveCardDrawPokerVariant;
import interfaces.OmahaHoldemPokerVariant;
import interfaces.PokerVariantInterface;
import interfaces.TexasHoldemPokerVariant;
import model.*;

import java.util.*;
import static java.util.Map.entry;

public class Solver {
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

    private static final String gamesRegex = "texas-holdem|omaha-holdem|five-card-draw";
    private static final String cardRegex = "(([AKQJT]|[2-9])[hcsd])";
    private static final String boardRegex = cardRegex + "{5}";
    private static final String texasHoldemHandsRegex = "(" + cardRegex + "{2}\\s?)*";
    private static final String omahaHoldemHandsRegex = "(" + cardRegex + "{4}\\s?)*";
    private static final String fiveCardDrawHandsRegex = "(" + cardRegex + "{5}\\s?)*";

    private static final Map<GameType, String> regexesMap = Map.ofEntries(
            entry(GameType.Holdem, texasHoldemHandsRegex),
            entry(GameType.Omaha, omahaHoldemHandsRegex),
            entry(GameType.Draw, fiveCardDrawHandsRegex)
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

    public static void parseInput() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            System.out.println(process(scanner.nextLine()));
        }
    }

    public static String process(String line){
        String[] gameData = line.split(" ");
        Game game = new Game();
        String[] hands;

        if (gameData[0].matches(gamesRegex))
            game.setGameType(gameTypesMap.get(gameData[0]));
        else
            return "Invalid game type. model.Game format: " + gamesRegex;

        if (game.getGameType() != GameType.Draw) {
            if (gameData[1].matches(boardRegex))
                game.setBoard(Optional.of(parseCardsInput(gameData[1])));
            else
                return "Invalid board. Board format: " + boardRegex;
            hands = Arrays.copyOfRange(gameData, 2, gameData.length);
        }
        else {
            game.setBoard(Optional.empty());
            hands = Arrays.copyOfRange(gameData, 1, gameData.length);
        }

        if (String.join(" ", hands).matches(regexesMap.get(game.getGameType())))
            Arrays.stream(hands).forEach((hand) -> game.addPlayer(new Player(hand, parseCardsInput(hand))));
        else
            return "Invalid hands format. Hands format: " + regexesMap.get(game.getGameType());
        game.setVariant(variantsMap.get(game.getGameType()));
        return game.giveResult();
    }

    public static void toString(ArrayList<Game> games){
        games.forEach(game -> System.out.println(game.toString()));
    }
}
