import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.combinations;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
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
        List<Card> filtered = new ArrayList<>(composition);
//        List<Card> filtered = composition.stream().filter(card -> card.getRank() == Rank.Ace).map(card -> new Card(card.getColor(), Rank.AceLow)).toList();
                filtered.addAll(composition.stream().filter(card -> card.getRank() == Rank.Ace).map(card -> new Card(card.getColor(), Rank.AceLow)).toList());
//        composition.addAll(filtered);
//        if(filtered.stream().filter(card -> card.getRank() == Rank.Ace).toList().size() > 0)
//            filtered.forEach(System.out::println);
        List<Card> straightLists = filtered.stream()
                .collect(StraightCollector.splitCards()).stream()
                .toList();
//        System.out.println("eee");
//        System.out.println(straightLists);
        if (straightLists.size() >= 5)
            return Optional.of(new Hand(HandType.Straight, straightLists.subList(0, 5).toArray(new Card[5])));
        return Optional.empty();
    }

    default Optional<Hand> findStraight(Card[] board, Card[] hand){
        List<Card> composition = Stream.concat(Arrays.stream(board), Arrays.stream(hand))
                .sorted(Card::compareTo)
                .toList();
        return findStraight(composition);
    }

    static Hand findHand(List<Card> list){
        Map<Rank, Long> map = list.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        List<Map.Entry<Rank, Long>> sorted = map.entrySet().stream().sorted(reverseOrder(Map.Entry.comparingByKey())).sorted(reverseOrder(Map.Entry.comparingByValue())).toList();
        List<Card> sortedList = new ArrayList<>();
//        sorted.stream().filter(card -> card.getRank() == r)
        for (Map.Entry<Rank, Long> rankLongEntry : sorted) {
            Rank r = rankLongEntry.getKey();
            sortedList.addAll(list.stream().filter(card -> card.getRank() == r).toList());
        }
        if (sorted.get(0).getValue() == 4L) return new Hand(HandType.Quad, sortedList.toArray(new Card[5]));
        if (sorted.get(0).getValue() == 3L){
            if (sorted.get(1).getValue() == 2L) return new Hand(HandType.FullHouse, sortedList.toArray(new Card[5]));
            else return new Hand(HandType.Set, sortedList.toArray(new Card[5]));
        }
        if (sorted.get(0).getValue() == 2L){
            if (sorted.get(1).getValue() == 2L) return new Hand(HandType.TwoPairs, sortedList.toArray(new Card[5]));
            else return new Hand(HandType.Pair, sortedList.toArray(new Card[5]));
        }
        return new Hand(HandType.HighCard, sortedList.toArray(new Card[5]));
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
        hand1 = findStraight(composition);
        if (hand1.isPresent() && bestHand.compareTo(hand1.get()) < 0) {
            bestHand = hand1.get();
        }
        Hand hand3 = findGroups(composition);
        if (bestHand.compareTo(hand3) < 0) {
            bestHand = hand3;
        }
        return bestHand;
    }
}
