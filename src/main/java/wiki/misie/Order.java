package wiki.misie;

public record Order(
    Type type,
    double price,
    int quantity,
    long timestamp,
    int orderId) {

  public enum Type {
    BUY,
    SELL
  }

  public Order(Type type, double price, int quantity) {
    this(type, price, quantity, 0L, 0);
  }

  public Order withTimestamp(long timestamp) {
    return new Order(type, price, quantity, timestamp, orderId);
  }

  public Order reduceQuantity(int tradeQuantity) {
    return new Order(type, price, quantity - tradeQuantity, timestamp, orderId);
  }

  public boolean isBuy() {
    return type == Type.BUY;
  }

  @Override
  public String toString() {
    return "Order{" +
        "type=" + type +
        ", price=" + price +
        ", quantity=" + quantity +
        ", timestamp=" + timestamp +
        ", orderId=" + orderId +
        '}';
  }
}
