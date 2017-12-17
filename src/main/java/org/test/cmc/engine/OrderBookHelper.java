package org.test.cmc.engine;

import org.test.cmc.common.OrderBookLevel;
import org.test.cmc.common.Side;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by leszek.sosnowski on 17/12/2017.
 */
public class OrderBookHelper {

    private boolean requiresRefreshing;

    private TreeMap<Integer, OrderBookLevel> bids;
    private TreeMap<Integer, OrderBookLevel> asks;

    private List<Integer> bidsLevelQuantities;
    private List<Integer> bidsLevelPrizes;

    private List<Integer> asksLevelQuantities;
    private List<Integer> asksLevelPrizes;

    public OrderBookHelper(TreeMap<Integer, OrderBookLevel> bids, TreeMap<Integer, OrderBookLevel> asks) {
        this.bids = bids;
        this.asks = asks;
    }

    public int getLevelRequiredToFulfillOrder(int quantity, Side side) {
        refreshIfRequired();

        List<Integer> correctQuantities = Side.SELL.equals(side) ? bidsLevelQuantities : asksLevelQuantities;
        int correctQuantity = new TreeSet<>(correctQuantities).ceiling(quantity);

        return ((ArrayList) correctQuantities).indexOf(correctQuantity);
    }

    public int getTotalPrizeForLevelsBelow(int level, Side side) {
        refreshIfRequired();

        List<Integer> correctPrizes = Side.SELL.equals(side) ? bidsLevelPrizes : asksLevelPrizes;

        return correctPrizes.get(level);
    }

    public int getTotalQuantityForLevelsBelow(int level, Side side) {
        refreshIfRequired();

        List<Integer> correctQuantities = Side.SELL.equals(side) ? bidsLevelQuantities : asksLevelQuantities;

        return correctQuantities.get(level);
    }

    private void refreshIfRequired() {
        if (requiresRefreshing) {
            bidsLevelPrizes = new ArrayList<>();
            bidsLevelQuantities = new ArrayList<>();
            asksLevelPrizes = new ArrayList<>();
            asksLevelQuantities = new ArrayList<>();
            refresh(bids, bidsLevelPrizes, bidsLevelQuantities);
            refresh(asks, asksLevelPrizes, asksLevelQuantities);
        }
    }

    private void refresh(TreeMap<Integer, OrderBookLevel> side, List<Integer> prizes, List<Integer> quantities) {
        int totalQuantity = 0;
        int totalPrize = 0;
        for (Map.Entry<Integer, OrderBookLevel> entry : side.entrySet()) {
            totalQuantity = totalQuantity + entry.getValue().getQuantity();
            totalPrize = totalPrize + entry.getValue().getTotalPrize();
            quantities.add(totalQuantity);
            prizes.add(totalPrize);
        }
        prizes.sort(Comparator.naturalOrder());
        quantities.sort(Comparator.naturalOrder());

        requiresRefreshing = false;
    }

    public void requiresRefreshing() {
        this.requiresRefreshing = true;
    }
}
