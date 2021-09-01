import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Game> games = Parser.parseInput();
//        Parser.toString(games);
        games.forEach(game -> System.out.println(game.giveResult()));
    }
}
