package wiki.misie;

import org.junit.jupiter.api.Test;
import wiki.misie.Order;
import wiki.misie.CentralLimitOrderBook;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CentralLimitOrderBookTest {

  @Test
  public void givenBuyOrders_whenAddedToOrderBook_thenShouldMatchOrdersInPriceTimePriority() {
    CentralLimitOrderBook orderBook = new CentralLimitOrderBook();

    Order buyOrder1 = new Order(Order.Type.BUY, 99, 1000).withTimestamp(1);
    Order buyOrder2 = new Order(Order.Type.BUY, 98, 1200).withTimestamp(2);
    Order buyOrder3 = new Order(Order.Type.BUY, 99, 500).withTimestamp(3);

    orderBook.addOrder(buyOrder1);
    orderBook.addOrder(buyOrder2);
    orderBook.addOrder(buyOrder3);

    orderBook.printOrderBook();

    PriorityQueue<Order> combinedOrders = orderBook.getCombinedOrders();
    Order firstOrder = combinedOrders.poll();
    assertEquals(1, firstOrder.orderId());

    Order secondOrder = combinedOrders.poll();
    assertEquals(3, secondOrder.orderId());

    Order thirdOrder = combinedOrders.poll();
    assertEquals(2, thirdOrder.orderId());
  }

  @Test
  public void givenBuyAndSellOrders_whenAddedToOrderBook_thenShouldMatchOrdersInPriceTimePriority() {
    CentralLimitOrderBook orderBook = new CentralLimitOrderBook();

    Order buyOrder1 = new Order(Order.Type.BUY, 99, 1000).withTimestamp(1);
    Order buyOrder2 = new Order(Order.Type.BUY, 98, 1200).withTimestamp(2);
    Order buyOrder3 = new Order(Order.Type.BUY, 99, 500).withTimestamp(3);

    Order sellOrder = new Order(Order.Type.SELL, 101, 2000).withTimestamp(4);

    orderBook.addOrder(buyOrder1);
    orderBook.addOrder(buyOrder2);
    orderBook.addOrder(buyOrder3);
    orderBook.addOrder(sellOrder);

    orderBook.printOrderBook();

    PriorityQueue<Order> combinedOrders = orderBook.getCombinedOrders();
    Order firstOrder = combinedOrders.poll();
    assertEquals(1, firstOrder.orderId());

    Order secondOrder = combinedOrders.poll();
    assertEquals(3, secondOrder.orderId());

    Order thirdOrder = combinedOrders.poll();
    assertEquals(2, thirdOrder.orderId());

    Order fourthOrder = combinedOrders.poll();
    assertEquals(4, fourthOrder.orderId());
  }

}
