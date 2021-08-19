import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class OmahaStraightCollector extends StraightCollector {
    private List<CardOrigin> origins;
//    private int numberBoard = 0, numberStartingHand = 0, numberBoth = 0;
    private Map<CardOrigin, Integer> cardOriginNumberMap;
    private boolean flag = false;

    @Override
    public Supplier<List<Card>> supplier() {
        this.origins = new ArrayList<>();
        this.cardOriginNumberMap = new HashMap<>();
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Card>, Card> accumulator() {

        return (list, card) -> {
            System.out.println(list);
            System.out.println(card.toString());
            if (list.size() == 0) {
                list.add(card);
                origins.add(card.getCardOrigin());
            }
            else {
                int lastIndex = list.size() - 1;
                Card lastAdded = list.get(lastIndex);
                int lastOriginIndex = origins.size() - 1;
                if (card.getRank().getValue() == lastAdded.getRank().getValue() - 1) {
                    list.add(card);
                    origins.add(card.getCardOrigin());
                }
                else if (card.getRank().getValue() == lastAdded.getRank().getValue()) {
                    if (origins.get(lastOriginIndex) != CardOrigin.BOTH) {
                        list.add(card);
                        origins.set(lastOriginIndex, CardOrigin.BOTH);
                    }
                }
                else {
                    list.clear();
                    origins.clear();
                    list.add(card);
                    origins.add(card.getCardOrigin());
                }
                if (origins.size() == 5){
                    int freeBoardCards = cardOriginNumberMap.get(CardOrigin.BOARD) + cardOriginNumberMap.get(CardOrigin.BOTH) - 3;
                    int freeHandCards = cardOriginNumberMap.get(CardOrigin.STARTING_HAND) + cardOriginNumberMap.get(CardOrigin.BOTH) - 2;
                    if (freeBoardCards >= 0 && freeHandCards >= 0){
                        flag = true;
                        int listIndex = 0;
                        for (CardOrigin co : origins){
                            if (co == CardOrigin.BOTH){
                                if (freeBoardCards > 0){
                                    if (list.get(listIndex).getCardOrigin() == CardOrigin.BOARD){
                                        list.remove(listIndex);
                                        freeBoardCards--;
                                    }
                                    else {

                                        freeHandCards--;
                                    }
                                }
                                else if (freeHandCards > 0){
                                    if (list.get(listIndex).getCardOrigin() == CardOrigin.STARTING_HAND){
                                        list.remove(listIndex);
                                        freeBoardCards--;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        origins.remove(0);
                        list.remove(0);
                    }
                }
            }
        };
    }

    @Override
    public Function<List<Card>, List<Card>> finisher() {
//        return (cards) -> {
//            return cards;
//        };
        return Function.identity();
    }
}
