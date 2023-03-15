package wiki.misie;

import java.time.Instant;
import java.util.*;

public class CentralLimitOrderBook {
  private final Map<String, Order> buyOrders;
  private final Map<String, Order> sellOrders;

  private final PriorityQueue<Order> buyPriorityQueue;
  private final PriorityQueue<Order> sellPriorityQueue;

  private int nextOrderId;

  public CentralLimitOrderBook() {
    buyOrders = new HashMap<>();
    sellOrders = new HashMap<>();

    buyPriorityQueue = new PriorityQueue<>((o1, o2) -> {
      int cmp = Double.compare(o2.getPrice(), o1.getPrice());
      if (cmp == 0) {
        return Long.compare(o1.getTimestamp(), o2.getTimestamp());
      }
      return cmp;
    });

    sellPriorityQueue = new PriorityQueue<>((o1, o2) -> {
      int cmp = Double.compare(o1.getPrice(), o2.getPrice());
      if (cmp == 0) {
        return Long.compare(o1.getTimestamp(), o2.getTimestamp());
      }
      return cmp;
    });

    nextOrderId = 1;
  }

  public void addOrder(Order order) {
    order.setOrderId(nextOrderId++);
    order.setTimestamp(Instant.now().getEpochSecond());

    if (order.isBuy()) {
      buyOrders.put(String.valueOf(order.getOrderId()), order);
      buyPriorityQueue.offer(order);
    } else {
      sellOrders.put(String.valueOf(order.getOrderId()), order);
      sellPriorityQueue.offer(order);
    }

    matchOrders();
  }

  private void matchOrders() {
    while (!buyPriorityQueue.isEmpty() && !sellPriorityQueue.isEmpty()
        && buyPriorityQueue.peek().getPrice() >= sellPriorityQueue.peek().getPrice()) {
      Order buyOrder = buyPriorityQueue.poll();
      Order sellOrder = sellPriorityQueue.poll();

      int tradeQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
      double tradePrice = sellOrder.getPrice();

      System.out.println("Trade executed: " + tradeQuantity + " shares at " + tradePrice);

      buyOrder.reduceQuantity(tradeQuantity);
      sellOrder.reduceQuantity(tradeQuantity);

      if (buyOrder.getQuantity() > 0) {
        buyPriorityQueue.offer(buyOrder);
      } else {
        buyOrders.remove(buyOrder.getOrderId());
      }

      if (sellOrder.getQuantity() > 0) {
        sellPriorityQueue.offer(sellOrder);
      } else {
        sellOrders.remove(sellOrder.getOrderId());
      }
    }
  }
}
