package org.test.cmc.engine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.cmc.common.Side;

import static org.test.cmc.engine.ExampleData.buildExampleOrderBookFromReadMe;

/**
 * Created by leszek.sosnowski on 17/12/2017.
 */
public class OrderHandlerTest {

    private OrderHandler orderHandler = new OrderHandlerImpl();

    @Before
    public void setUp() {
        buildExampleOrderBookFromReadMe(orderHandler);
    }

    @Test
    public void shouldGetCorrectPricing1() {
        Assert.assertTrue(19d == orderHandler.getCurrentPrize("MSFT", 6, Side.SELL));
    }

    @Test
    public void shouldGetCorrectPricing2() {
        Assert.assertTrue(Math.abs(orderHandler.getCurrentPrize("MSFT", 17, Side.SELL) - 19.588d) < 0.001);
    }

    @Test
    public void shouldGetCorrectPricing3() {
        Assert.assertTrue(Math.abs(orderHandler.getCurrentPrize("MSFT", 30, Side.SELL) - 20.233d) < 0.001);
    }

    @Test
    public void shouldGetCorrectPricing4() {
        Assert.assertTrue(15d == orderHandler.getCurrentPrize("MSFT", 10, Side.BUY));
    }
}