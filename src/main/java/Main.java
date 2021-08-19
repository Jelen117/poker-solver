//import one.util.streamex.StreamEx;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
	// write your code here
//        System.out.println("abc");
//        final int subListSize = 3;
//        List<Integer> list = List.of(1, 2, 4, 6, 7, 8, 9);
//        List<List<Integer>> sublists = IntStream.rangeClosed(0, list.size() - subListSize)
//                .mapToObj(i -> list.subList(i, i + subListSize))
//                .collect(Collectors.toList());
//        for (List<Integer> list1 : sublists){
//            System.out.println(list1);
//        }
//
//        List<Rank> weekDays = Arrays.asList(Rank.Ace, Rank.King, Rank.Jack, Rank.Ten, Rank.Nine);
//        System.out.println(StreamEx.of(weekDays)
//                .collapse((a, b) -> a.getValue() - b.getValue() == 1, Collectors.counting()).toList().toString());


//                .max(Comparator.naturalOrder()).get());

//        StreamEx.of
//        Card card1 = new Card();
//        card1.setColor(Color.Club);
//        card1.setRank(Rank.Eight);
//        Card card2 = new Card();
//        card2.setColor(Color.Diamond);
//        card2.setRank(Rank.Deuce);
//        Card card3 = new Card();
//        card3.setColor(Color.Heart);
//        card3.setRank(Rank.Jack);
//        Card card4 = new Card();
//        card4.setColor(Color.Heart);
//        card4.setRank(Rank.Jack);
//        Card card5 = new Card();
//        card5.setColor(Color.Spade);
//        card5.setRank(Rank.King);
//        Hand hand1 = new Hand(HandType.Flush, new Card[]{card1, card4, card1});
//        Hand hand2 = new Hand(HandType.Straight, new Card[]{card2});
//        Hand hand3 = new Hand(HandType.Flush, new Card[]{card5});
//        System.out.println(hand1.compareTo(hand2));
        ArrayList<Game> games = Parser.parseInput();
        Parser.toString(games);
        System.out.println(Arrays.toString(Parser.giveResult(games.get(0))));
    }
}
