package wiki.misie;

public class Order {
  public enum Type {
    BUY,
    SELL
  }

  private Type type;
  private double price;
  private int quantity;
  private long timestamp;
  private int orderId;

  public Order(Type type, double price, int quantity) {
    this.type = type;
    this.price = price;
    this.quantity = quantity;
  }

  public void reduceQuantity(int tradeQuantity) {
    quantity = quantity - tradeQuantity;
  }

  public Type getType() {
    return type;
  }

  public boolean isBuy() {
    return type == Type.BUY;
  }

  public double getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
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
