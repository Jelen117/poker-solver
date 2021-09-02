import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.cartesianProduct;
import static com.google.common.collect.Sets.combinations;

public class OmahaHoldemPokerVariant implements PokerVariantInterface {
    @Override
    public Optional<Hand> findStraight(Card[] board, Card[] hand){
        Set<Set<Card>> combinations1 = combinations(Sets.newHashSet(board), 3);
        Set<Set<Card>> combinations2 = combinations(Sets.newHashSet(hand), 2);
        Set<List<Set<Card>>> lista = cartesianProduct(combinations1, combinations2);
        for (List<Set<Card>> s : lista){
            List<Card> hand1 = Sets.newHashSet(Iterables.concat(s)).stream()
                    .sorted(Card::compareTo)
                    .collect(StraightCollector.splitCards());
            if (hand1.size() == 5){
                return Optional.of(new Hand(HandType.Straight, hand1.toArray(Card[]::new)));
            }

        }
        return Optional.empty();
    }

    @Override
    public Hand findGroups(Card[] board, Card[] hand){
        Set<Set<Card>> combinations1 = combinations(Sets.newHashSet(board), 3);
        Set<Set<Card>> combinations2 = combinations(Sets.newHashSet(hand), 2);
        Set<List<Set<Card>>> lista = cartesianProduct(combinations1, combinations2);
        for (List<Set<Card>> s : lista){
            Map<Rank, Long> Hand = Sets.newHashSet(Iterables.concat(s)).stream()
                    .sorted(Card::compareTo)
                    .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
            if (Hand.size() == 5){
                System.out.println("STREET");
                System.out.println(Hand);
            }

        }
        return null;
    }
}
