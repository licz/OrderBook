package org.test.cmc.engine;

import com.google.common.annotations.VisibleForTesting;
import org.test.cmc.common.Order;
import org.test.cmc.common.OrderBookLevel;
import org.test.cmc.common.Side;

import java.util.Collections;
import java.util.TreeMap;

/**
 * Created by leszek.sosnowski on 16/12/2017.
 */
public class OrderBook {
    private final String symbol;

    private TreeMap<Integer, OrderBookLevel> bids;
    private TreeMap<Integer, OrderBookLevel> asks;

    private OrderBookHelper orderBookHelper;

    public OrderBook(String symbol) {
        this.symbol = symbol;
        this.bids = new TreeMap<>();
        this.asks = new TreeMap<>(Collections.reverseOrder());
        orderBookHelper = new OrderBookHelper(bids, asks);
    }

    public void addOrder(Order order) {
        TreeMap<Integer, OrderBookLevel> orderBookSide = getAppropriateSide(order.getSide());

        addOrder(orderBookSide, order);

        orderBookHelper.requiresRefreshing();
    }

    public void modifyOrder(Order orderToModify, int newQuantity, int newPrize) {
        TreeMap<Integer, OrderBookLevel> orderBookSide = getAppropriateSide(orderToModify.getSide());

        OrderBookLevel currentBookLevel = orderBookSide.get(orderToModify.getPrize());

        if (orderToModify.getPrize() != newPrize) {
            currentBookLevel.removeOrder(orderToModify);
            if (currentBookLevel.getOrders().isEmpty()) {
                orderBookSide.remove(orderToModify.getPrize());
            }
            orderToModify.setPrize(newPrize);
            orderToModify.setQuantity(newQuantity);
            addOrder(orderBookSide, orderToModify);
        } else {
            currentBookLevel.modifyQuantityBy(-orderToModify.getQuantity());
            orderToModify.setPrize(newPrize);
            orderToModify.setQuantity(newQuantity);
            currentBookLevel.modifyQuantityBy(newQuantity);
        }

        orderBookHelper.requiresRefreshing();
    }

    public void removeOrder(Order orderToRemove) {
        TreeMap<Integer, OrderBookLevel> orderBookSide = getAppropriateSide(orderToRemove.getSide());

        OrderBookLevel currentBookLevel = orderBookSide.get(orderToRemove.getPrize());

        currentBookLevel.removeOrder(orderToRemove);
        if (currentBookLevel.getOrders().isEmpty()) {
            orderBookSide.remove(orderToRemove.getPrize());
        }

        orderBookHelper.requiresRefreshing();
    }

    private void addOrder(TreeMap<Integer, OrderBookLevel> orderBookSide, Order order) {
        if (!orderBookSide.containsKey(order.getPrize())) {
            orderBookSide.put(order.getPrize(), new OrderBookLevel(order.getPrize(), order.getQuantity(), order));
        } else {
            orderBookSide.get(order.getPrize()).addOrder(order);
        }
    }

    public double getCurrentPrize(int quantity, Side side) {
        TreeMap<Integer, OrderBookLevel> orderBookSide = getAppropriateSideForPricing(side);

        int levelRequired = orderBookHelper.getLevelRequiredToFulfillOrder(quantity, side);

        if (levelRequired == 0) {
            return (double) orderBookSide.firstEntry().getValue().getPrize();
        }

        int totalPrizeForLevelsBelow = orderBookHelper.getTotalPrizeForLevelsBelow(levelRequired, side);
        int totalQuantityForLevelsBelow = orderBookHelper.getTotalQuantityForLevelsBelow(levelRequired, side);

        int quantityRequiredFromHighestLevel = quantity - totalQuantityForLevelsBelow;
        int totalPrizeForHighestLevel = ((OrderBookLevel) orderBookSide.values().toArray()[levelRequired]).getPrize() * quantityRequiredFromHighestLevel;

        return (double) (totalPrizeForLevelsBelow + totalPrizeForHighestLevel) / quantity;
    }

    private TreeMap<Integer,OrderBookLevel> getAppropriateSideForPricing(Side side) {
        return Side.BUY.equals(side) ? asks : bids;
    }

    private TreeMap<Integer, OrderBookLevel> getAppropriateSide(Side side) {
        return Side.BUY.equals(side) ? asks : bids;
    }

    @VisibleForTesting
    void setAsks(TreeMap<Integer, OrderBookLevel> asks) {
        this.asks = asks;
    }

    @VisibleForTesting void setBids(TreeMap<Integer, OrderBookLevel> bids) {
        this.bids = bids;
    }
}
