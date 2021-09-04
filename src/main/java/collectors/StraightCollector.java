package collectors;

import model.Card;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class StraightCollector implements Collector<Card, List<Card>, List<Card>> {
    @Override
    public Supplier<List<Card>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Card>, Card> accumulator() {
        return (list, card) -> {
            if (list.size() == 0) list.add(card);
            else if (list.size() < 5) {
                int lastIndex = list.size() - 1;
                Card lastAdded = list.get(lastIndex);
                if (card.getRank().getValue() == lastAdded.getRank().getValue() - 1) list.add(card);
                else if (card.getRank().getValue() != lastAdded.getRank().getValue()) {
                    list.clear();
                    list.add(card);
                }
            }
        };
    }

    @Override
    public BinaryOperator<List<Card>> combiner() {
        return null;
    }

    @Override
    public Function<List<Card>, List<Card>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return new HashSet<>();
    }

    public static Collector<Card, List<Card>, List<Card>> splitCards(){
        return new StraightCollector();
    }
}
