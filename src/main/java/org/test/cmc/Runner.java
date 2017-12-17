package org.test.cmc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.test.cmc.common.Order;
import org.test.cmc.common.OrderModification;
import org.test.cmc.common.Side;
import org.test.cmc.engine.OrderHandler;
import org.test.cmc.engine.OrderHandlerImpl;

import java.util.Scanner;

/**
 * Created by leszek.sosnowski on 16/12/2017.
 */
@Component
public class Runner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private OrderHandler orderHandler = new OrderHandlerImpl();

    private static long ID = 0l;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        sayHello();

        Scanner in = new Scanner(System.in);
        String line;

        while (!"quit".equals(line = in.nextLine())) {
            String[] args = line.split("\\s+");
            if (line.contains("add")) {
                orderHandler.addOrder(
                        new Order(
                                ID++,
                                args[1],
                                Side.valueOf(args[2]),
                                Integer.valueOf(args[3]),
                                Integer.valueOf(args[4])
                        )
                );
            } else if (line.contains("modify")) {
                orderHandler.modifyOrder(
                        new OrderModification(
                                Long.valueOf(args[1]),
                                Integer.valueOf(args[2]),
                                Integer.valueOf(args[3])
                        )
                );
            } else if (line.contains("remove")) {
                orderHandler.removeOrder(
                        Integer.valueOf(args[1])
                );
            } else if (line.contains("prize")) {
                logger.info("currentPrize: " +
                        orderHandler.getCurrentPrize(
                                args[1],
                                Integer.valueOf(args[2]),
                                Side.valueOf(args[3])
                        )
                );
            }
        }
    }

    private void sayHello() {
        logger.info("Welcome to the OrderBook simulator!");
        logger.info(" - to add order: add <symbol> <side> <prize> <quantity>");
        logger.info(" - to modify order: modify <orderId> <prize> <quantity>");
        logger.info(" - to remove order: remove <orderId>");
        logger.info(" - to get current prize: prize <symbol> <quantity> <side>");
        logger.info(" - where side: BUY or SELL");
    }
}
