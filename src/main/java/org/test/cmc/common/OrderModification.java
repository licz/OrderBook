package org.test.cmc.common;

/**
 * Created by leszek.sosnowski on 17/12/2017.
 */
public class OrderModification {

    private final long orderId;
    private final int prize;
    private final int quantity;

    public OrderModification(long orderId, int prize, int quantity) {
        this.orderId = orderId;
        this.prize = prize;
        this.quantity = quantity;
    }

    public int getPrize() {
        return prize;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getOrderId() {
        return orderId;
    }
}
