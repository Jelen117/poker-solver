import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.combinations;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.reverseOrder;
import static one.util.streamex.MoreCollectors.maxAll;

public interface PokerVariantInterface {

    default Optional<Hand> findStraightFlushAndFlush(List<Card> composition){
        Map.Entry<Color, Long> maxColor = composition.stream()
                .collect(Collectors.groupingBy(Card::getColor, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        if (maxColor.getValue() >= 5){
            List<Card> flushCards = composition.stream()
                    .filter(c -> c.getColor() == maxColor.getKey())
                    .sorted(Card::compareTo)
                    .toList();
            Optional<Hand> straightFlush = findStraight(flushCards);
            if (straightFlush.isPresent()){
                straightFlush.get().setHandType(HandType.StraightFlush);
                return straightFlush;
            }
            return Optional.of(new Hand(HandType.Flush, flushCards.subList(0,5).toArray(new Card[5])));
        }
        return Optional.empty();
    }

    default Optional<Hand> findStraight(List<Card> composition){
        List<Card> straightLists = composition.stream()
                .collect(StraightCollector.splitCards()).stream()
                .toList();
        if (straightLists.size() >= 5)
            return Optional.of(new Hand(HandType.Straight, straightLists.toArray(new Card[5])));
        return Optional.empty();
    }

    default Optional<Hand> findStraight(Card[] board, Card[] hand){
        List<Card> composition = Stream.concat(Arrays.stream(board), Arrays.stream(hand))
                .sorted(Card::compareTo)
                .toList();
        return findStraight(composition);
    }

    static Hand findHand(List<Card> list){
        List<Long> values = list.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()))
                .values().stream()
                .sorted(reverseOrder())
                .toList();
        if (values.get(0) == 4L) return new Hand(HandType.Quad, list.toArray(new Card[5]));
        if (values.get(0) == 3L){
            if (values.get(1) == 2L) return new Hand(HandType.FullHouse, list.toArray(new Card[5]));
            else return new Hand(HandType.Set, list.toArray(new Card[5]));
        }
        if (values.get(0) == 2L){
            if (values.get(1) == 2L) return new Hand(HandType.TwoPairs, list.toArray(new Card[5]));
            else return new Hand(HandType.Pair, list.toArray(new Card[5]));
        }
        return new Hand(HandType.HighCard, list.toArray(new Card[5]));
    }

    default Hand findGroups(List<Card> composition){
        Set<Set<Card>> combinations = combinations(Sets.newHashSet(composition), 5);
        Hand bestHand = new Hand(HandType.HighCard, composition.subList(0, 5).toArray(new Card[0]));
        for (Set<Card> set : combinations){
            Hand res = findHand(set.stream().sorted(Card::compareTo).toList());
            if (bestHand.compareTo(res) < 0)
                bestHand = res;
        }
        return bestHand;
    }

    default Hand findGroups(Card[] board, Card[] hand){
        List<Card> composition = Stream.concat(Arrays.stream(board), Arrays.stream(hand)).sorted(Card::compareTo).toList();
        return findGroups(composition);
    }

    default Hand findHighCard(List<Card> composition){
        return new Hand(HandType.HighCard, composition.subList(0, 5).toArray(new Card[5]));
    }

    default Hand findBestHand(Optional<Card[]> board, Card[] hand){
        List<Card> composition;
        if (board.isPresent())
            composition = Stream.concat(Arrays.stream(board.get()), Arrays.stream(hand)).sorted(Card::compareTo).toList();
        else composition = Arrays.stream(hand).sorted(Card::compareTo).toList();
        Hand bestHand = findHighCard(composition);
        Optional<Hand> hand1 = findStraightFlushAndFlush(composition);
        if (hand1.isPresent() && bestHand.compareTo(hand1.get()) < 0) {
            bestHand = hand1.get();
        }
//        System.out.println(bestHand);
        Optional<Hand> hand2 = findStraight(composition);
//        System.out.println("hand2");
//        System.out.println(hand2);
        if (hand2.isPresent() && bestHand.compareTo(hand2.get()) < 0) {
            bestHand = hand2.get();
        }
//        System.out.println(bestHand);
//        System.out.println("bestHand3");
        Hand hand3 = findGroups(composition);
//        System.out.println("hand3");
//        System.out.println(hand3);
        if (bestHand.compareTo(hand3) < 0) {
            bestHand = hand3;
        }
//        System.out.println(bestHand);

        return bestHand;
    }
}
