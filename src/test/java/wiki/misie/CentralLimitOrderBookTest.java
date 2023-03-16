// package wiki.misie;

// import org.junit.jupiter.api.Test;
// import wiki.misie.CentralLimitOrderBook;
// import wiki.misie.Order;

// import java.util.List;
// import java.util.ArrayList;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// public class CentralLimitOrderBookTest {


// @Test
// public void testAddition() {
// System.out.println("test is run");
// assertEquals(2 + 2, 8);
// }
  
//   @Test
//   public void givenBuyAndSellOrders_whenAddedToOrderBook_thenShouldMatchOrdersInPriceTimePriority() {
//     CentralLimitOrderBook orderBook = new CentralLimitOrderBook();

//     orderBook.addOrder(new Order(Order.Type.BUY, 98, 25500, Long.parseLong("1000000000000"), 10000));
//     orderBook.addOrder(new Order(Order.Type.SELL, 105, 20000, Long.parseLong("1000000000001"), 10005));
//     orderBook.addOrder(new Order(Order.Type.SELL, 100, 500, Long.parseLong("1000000000002"), 10001));
//     orderBook.addOrder(new Order(Order.Type.SELL, 100, 10000, Long.parseLong("1000000000003"), 10002));
//     orderBook.addOrder(new Order(Order.Type.BUY, 99, 50000, Long.parseLong("1000000000004"), 10003));
//     orderBook.addOrder(new Order(Order.Type.SELL, 103, 100, Long.parseLong("1000000000005"), 10004));

//     List<Order> combinedOrders = new ArrayList<>(orderBook.getCombinedOrders());

//     assertEquals(6, combinedOrders.size());
//     assertEquals(10000, combinedOrders.get(0).orderId());
//     assertEquals(10003, combinedOrders.get(1).orderId());
//     assertEquals(10001, combinedOrders.get(2).orderId());
//     assertEquals(10002, combinedOrders.get(3).orderId());
//     assertEquals(10004, combinedOrders.get(4).orderId());
//     assertEquals(10005, combinedOrders.get(5).orderId());
//   }

// }
