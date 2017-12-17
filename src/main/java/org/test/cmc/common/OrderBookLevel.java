package org.test.cmc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leszek.sosnowski on 16/12/2017.
 */
public class OrderBookLevel {
    private int prize;
    private int quantity;
    private List<Order> orders;

    public OrderBookLevel(int prize, int quantity, Order order) {
        this.prize = prize;
        this.quantity = quantity;
        this.orders = new ArrayList(Arrays.asList(order));
    }

    public void addOrder(Order order) {
        quantity = quantity + order.getQuantity();
        orders.add(order);
    }

    public void removeOrder(Order order) {
        quantity = quantity - order.getQuantity();
        orders.remove(order);
    }

    public void modifyQuantityBy(int number) {
        quantity = quantity + number;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrize() {
        return prize;
    }

    public int getTotalPrize() {
        return prize * quantity;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
