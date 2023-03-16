package wiki.misie;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import static java.util.Collections.unmodifiableMap;

public class CentralLimitOrderBook {
  private int nextOrderId = 1;
  private Map<String, Order> buyOrders = new HashMap<>();
  private Map<String, Order> sellOrders = new HashMap<>();

  Comparator<Order> orderComparator = Comparator
      .comparing(Order::isBuy, Comparator.reverseOrder())
      .thenComparing(o -> o.isBuy() ? o.price() : -o.price(), Comparator.reverseOrder())
      .thenComparing(Order::timestamp)
      .thenComparing(Order::orderId);

  private PriorityQueue<Order> buyPriorityQueue = new PriorityQueue<>(orderComparator);
  private PriorityQueue<Order> sellPriorityQueue = new PriorityQueue<>(orderComparator);

  public void addOrder(Order order) {
    Order orderWithOrderId = order.withOrderId(nextOrderId++);
    if (orderWithOrderId.isBuy()) {
      buyOrders.put(String.valueOf(orderWithOrderId.orderId()), orderWithOrderId);
      buyPriorityQueue.add(orderWithOrderId);
    } else {
      sellOrders.put(String.valueOf(orderWithOrderId.orderId()), orderWithOrderId);
      sellPriorityQueue.add(orderWithOrderId);
    }
    matchOrders();
  }

public void matchOrders() {
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

        // Print the trade
        System.out.println("trade " + sellOrder.orderId() + "," + buyOrder.orderId() + "," + tradePrice + "," + tradeQuantity);
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

  public PriorityQueue<Order> getCombinedOrders() {
    PriorityQueue<Order> combinedOrders = new PriorityQueue<>(orderComparator);

    combinedOrders.addAll(buyPriorityQueue);
    combinedOrders.addAll(sellPriorityQueue);

    return combinedOrders;
  }

  public Map<String, Order> getBuyOrders() {
    return unmodifiableMap(buyOrders);
  }

  public Map<String, Order> getSellOrders() {
    return unmodifiableMap(sellOrders);
  }

  
}
