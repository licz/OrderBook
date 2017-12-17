package org.test.cmc.engine;

import org.test.cmc.common.Order;
import org.test.cmc.common.OrderModification;
import org.test.cmc.common.Side;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leszek.sosnowski on 16/12/2017.
 */
public class OrderHandlerImpl implements OrderHandler {

    private Map<String, OrderBook> orderBooks = new HashMap<>();

    private Map<Long, Order> orders = new HashMap<>();

    @Override
    public void addOrder(Order newOrder) {

        orders.put(newOrder.getOrderId(), newOrder);

        instantiateIfNeeded(orderBooks, newOrder.getSymbol());
        orderBooks.get(newOrder.getSymbol()).addOrder(newOrder);
    }

    @Override
    public void modifyOrder(OrderModification orderModification) {
        Order orderToModify = orders.get(orderModification.getOrderId());

        orderBooks.get(orderToModify.getSymbol()).modifyOrder(orderToModify, orderModification.getQuantity(), orderModification.getPrize());
    }

    @Override
    public void removeOrder(long orderId) {
        Order orderToRemove = orders.get(orderId);
        orderBooks.get(orderToRemove.getSymbol()).removeOrder(orderToRemove);
    }

    @Override
    public double getCurrentPrize(String symbol, int quantity, Side side) {
        return orderBooks.get(symbol).getCurrentPrize(quantity, side);
    }

    private void instantiateIfNeeded(Map<String, OrderBook> orderBooks, String symbol) {
        if (!orderBooks.containsKey(symbol)) {
            orderBooks.put(symbol, new OrderBook(symbol));
        }
    }
}
