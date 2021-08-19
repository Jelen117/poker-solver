import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface PokerVariantInterface {
    default Hand findStraightFlushAndFlush(List<Card> composition){
        Map.Entry<Color, Long> aa = composition.stream().
                collect(Collectors.groupingBy(Card::getColor, Collectors.counting())).
                entrySet().stream().max(Map.Entry.comparingByValue()).get();
        if (aa.getValue() >= 5){
            List<Card> flushCards = composition.stream().filter(c -> c.getColor() == aa.getKey()).toList();
            Hand straightFlush = findStraight(flushCards);
            if (straightFlush != null){
                straightFlush.setHandType(HandType.StraightFlush);
                return straightFlush;
            }

        }
        return null;
    }

    default Hand findStraight(List<Card> composition){
        List<Card> straightLists = composition.stream()
                .collect(StraightCollector.splitCards()).stream().toList();
        if (straightLists.size() == 5){
            new Hand(HandType.Straight, straightLists.toArray(new Card[5]));
        }
        return null;
    }

    default Hand findStraight(Card[] board, Card[] hand){
        List<Card> composition = Stream.concat(Arrays.stream(board), Arrays.stream(hand)).sorted(Card::compareTo).toList();
        return findStraight(composition);
    }

    default Hand findGroups(List<Card> composition){
        Map<Rank, Long> map = composition.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        Stream<Long> stream = map.values().stream().filter((value) -> value.equals(4L));
        if (stream.findFirst() == null) System.out.println("aaaaa");
        map.values().stream().filter((value) -> value.equals(3L)).forEach(System.out::println);

        System.out.println("wartownik");
        return null;
    }

    default Hand findGroups(Card[] board, Card[] hand){
        List<Card> composition = Stream.concat(Arrays.stream(board), Arrays.stream(hand)).sorted(Card::compareTo).toList();
        return findGroups(composition);
    }

    default Hand findBestHand(Card[] board, Card[] hand){
        List<Card> composition = Stream.concat(Arrays.stream(board), Arrays.stream(hand)).sorted(Card::compareTo).toList();
        Hand hand1 = findStraightFlushAndFlush(composition);
        Hand hand2 = findStraight(composition);
        Hand hand3 = findGroups(composition);
        return null;
    }
}
