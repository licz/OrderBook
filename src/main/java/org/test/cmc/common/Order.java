package org.test.cmc.common;

/**
 * Created by leszek.sosnowski on 16/12/2017.
 */
public class Order {
    private final long orderId;
    private String symbol;
    private int quantity;
    private int prize;
    private final Side side;

    public Order(long orderId, String symbol, Side side, int prize, int quantity) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.prize = prize;
        this.side = side;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public Side getSide() {
        return side;
    }
}
