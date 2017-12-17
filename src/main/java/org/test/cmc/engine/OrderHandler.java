package org.test.cmc.engine;

import org.test.cmc.common.Order;
import org.test.cmc.common.OrderModification;
import org.test.cmc.common.Side;

/**
 * Created by leszek.sosnowski on 16/12/2017.
 */
public interface OrderHandler {

    void addOrder(Order order);
    void modifyOrder(OrderModification orderModification);
    void removeOrder(long orderId);
    double getCurrentPrize(String symbol, int quantity, Side side);
}
