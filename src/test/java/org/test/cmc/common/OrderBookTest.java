package org.test.cmc.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.cmc.engine.OrderBook;

/**
 * Created by leszek.sosnowski on 17/12/2017.
 */
public class OrderBookTest {
    private OrderBook orderBook = new OrderBook("test");

    @Before
    public void setUp() {
        orderBook.addOrder(new Order(1, "test", Side.SELL, 19, 12));
        orderBook.addOrder(new Order(2, "test", Side.SELL, 21, 17));
        orderBook.addOrder(new Order(3, "test", Side.SELL, 22, 7));
        orderBook.addOrder(new Order(4, "test", Side.BUY, 15, 10));
        orderBook.addOrder(new Order(5, "test", Side.BUY, 10, 26));
    }

    @Test
    public void shouldGetCorrectPricing1() {
        Assert.assertTrue(19d == orderBook.getCurrentPrize(6, Side.SELL));
    }

    @Test
    public void shouldGetCorrectPricing2() {
        Assert.assertTrue(Math.abs(orderBook.getCurrentPrize(17, Side.SELL) - 19.588d) < 0.001);
    }

    @Test
    public void shouldGetCorrectPricing3() {
        Assert.assertTrue(Math.abs(orderBook.getCurrentPrize(30, Side.SELL) - 20.233d) < 0.001);
    }

    @Test
    public void shouldGetCorrectPricing4() {
        Assert.assertTrue(15d == orderBook.getCurrentPrize(10, Side.BUY));
    }
}