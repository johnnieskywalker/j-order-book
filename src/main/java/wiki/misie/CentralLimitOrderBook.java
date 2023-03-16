package wiki.misie;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class CentralLimitOrderBook {
  private int nextOrderId = 1;
  private Map<String, Order> buyOrders = new HashMap<>();
  private Map<String, Order> sellOrders = new HashMap<>();
  private PriorityQueue<Order> buyPriorityQueue = new PriorityQueue<>(
      Comparator.comparingDouble(Order::price).reversed().thenComparingLong(Order::timestamp));
  private PriorityQueue<Order> sellPriorityQueue = new PriorityQueue<>(
      Comparator.comparingDouble(Order::price).thenComparingLong(Order::timestamp));

  public void addOrder(Order order) {
    order = new Order(order.type(), order.price(), order.quantity(), Instant.now().getEpochSecond(), nextOrderId++);
    if (order.isBuy()) {
      buyOrders.put(String.valueOf(order.orderId()), order);
      buyPriorityQueue.add(order);
    } else {
      sellOrders.put(String.valueOf(order.orderId()), order);
      sellPriorityQueue.add(order);
    }
    matchOrders();
  }

  private void matchOrders() {
    while (!buyPriorityQueue.isEmpty() && !sellPriorityQueue.isEmpty()
        && buyPriorityQueue.peek().price() >= sellPriorityQueue.peek().price()) {
      Order buyOrder = buyPriorityQueue.poll();
      Order sellOrder = sellPriorityQueue.poll();

      int tradeQuantity = Math.min(buyOrder.quantity(), sellOrder.quantity());
      double tradePrice = sellOrder.price();

      // Update order quantities
      buyOrder = new Order(buyOrder.type(), buyOrder.price(), buyOrder.quantity() - tradeQuantity, buyOrder.timestamp(),
          buyOrder.orderId());
      sellOrder = new Order(sellOrder.type(), sellOrder.price(), sellOrder.quantity() - tradeQuantity,
          sellOrder.timestamp(), sellOrder.orderId());

      if (buyOrder.quantity() > 0) {
        buyOrders.put(String.valueOf(buyOrder.orderId()), buyOrder);
        buyPriorityQueue.add(buyOrder);
      } else {
        buyOrders.remove(String.valueOf(buyOrder.orderId()));
      }

      if (sellOrder.quantity() > 0) {
        sellOrders.put(String.valueOf(sellOrder.orderId()), sellOrder);
        sellPriorityQueue.add(sellOrder);
      } else {
        sellOrders.remove(String.valueOf(sellOrder.orderId()));
      }
    }
  }

  public void printOrderBook() {
    PriorityQueue<Order> combinedOrders = getCombinedOrders();

    while (!combinedOrders.isEmpty()) {
      Order order = combinedOrders.poll();
      String side = order.isBuy() ? "B" : "S";
      System.out.printf("%s,%s,%.0f,%d%n", order.orderId(), side, order.price(), order.quantity());
    }
  }

  PriorityQueue<Order> getCombinedOrders() {
    PriorityQueue<Order> combinedOrders = new PriorityQueue<>(Comparator
        .comparing(Order::price)
        .thenComparing(Order::timestamp)
        .thenComparing(Order::orderId));

    combinedOrders.addAll(buyPriorityQueue);
    combinedOrders.addAll(sellPriorityQueue);

    return combinedOrders;
  }

}
