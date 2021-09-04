import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.cartesianProduct;
import static com.google.common.collect.Sets.combinations;

public class OmahaHoldemPokerVariant implements PokerVariantInterface {

    @Override
    public Optional<Hand> findStraightFlushAndFlush(Card[] board, Card[] hand){
        Set<Set<Card>> combinations1 = combinations(Sets.newHashSet(board), 3);
        Set<Set<Card>> combinations2 = combinations(Sets.newHashSet(hand), 2);
        Set<List<Set<Card>>> set = cartesianProduct(combinations1, combinations2);
        Optional<Hand> bestFlush = Optional.empty();
        for (List<Set<Card>> list : set){
            Optional<Hand> res = PokerVariantInterface.findStraightFlushAndFlush(Sets.newHashSet(Iterables.concat(list)).stream().sorted(Card::compareTo).toList());
            if (res.isPresent() && bestFlush.isEmpty())
                bestFlush = res;
            else if (res.isPresent() && bestFlush.get().compareTo(res.get()) < 0)
                bestFlush = res;
        }
        return bestFlush;
    }

    @Override
    public Optional<Hand> findStraight(Card[] board, Card[] hand){
        Set<Set<Card>> combinations1 = combinations(Sets.newHashSet(board), 3);
        Set<Set<Card>> combinations2 = combinations(Sets.newHashSet(hand), 2);
        Set<List<Set<Card>>> lista = cartesianProduct(combinations1, combinations2);
        for (List<Set<Card>> s : lista){
            List<Card> hand1 = Sets.newHashSet(Iterables.concat(s)).stream()
                    .sorted(Card::compareTo)
                    .collect(StraightCollector.splitCards());
            if (hand1.size() >= 5){
                return Optional.of(new Hand(HandType.Straight, hand1.subList(0, 5).toArray(Card[]::new)));
            }
        }
        return Optional.empty();
    }

    @Override
    public Hand findGroups(Card[] board, Card[] hand){
        Set<Set<Card>> combinations1 = combinations(Sets.newHashSet(board), 3);
        Set<Set<Card>> combinations2 = combinations(Sets.newHashSet(hand), 2);
        Set<List<Set<Card>>> set = cartesianProduct(combinations1, combinations2);
        List<Card> composition = Lists.newArrayList(Arrays.copyOfRange(board, 0, 3));
        composition.addAll(Lists.newArrayList(Arrays.copyOfRange(hand, 0, 2)));
        Hand bestHand = new Hand(HandType.HighCard, composition.toArray(new Card[0]));
        for (List<Set<Card>> list : set){
            Hand res = PokerVariantInterface.findHand(Sets.newHashSet(Iterables.concat(list)).stream().sorted(Card::compareTo).toList());
            if (bestHand.compareTo(res) < 0)
                bestHand = res;
        }
        return bestHand;
    }
}
