import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import wiki.misie.CentralLimitOrderBook;
import wiki.misie.Order;

class Main {
  public static void main(String[] args) throws IOException {
    CentralLimitOrderBook orderBook = new CentralLimitOrderBook();

    List<Order> orders = new ArrayList<>();
    if (args.length > 0) {
      if (args[0].endsWith(".txt")) {
        orders.addAll(readOrdersFromTxtFile(args[0]));
      } else {
        orders.addAll(readOrdersFromCommandLine(args));
      }
    } else {
      printManual();
    }

    for (Order order : orders) {
      orderBook.addOrder(order.withTimestamp(Instant.now().getEpochSecond()));
    }
    orderBook.matchOrders();
    orderBook.printOrderBook();
  }

  private static List<Order> readOrdersFromTxtFile(String fileName) {
    List<Order> orders = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
          System.out.println("Invalid input: " + line);
          continue;
        }
        double price;
        int quantity;
        try {
          price = Double.parseDouble(parts[1]);
          quantity = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
          System.out.println("Invalid input: " + line);
          continue;
        }
        if (parts[0].equalsIgnoreCase("buy")) {
          orders.add(new Order(Order.Type.BUY, price, quantity));
        } else if (parts[0].equalsIgnoreCase("sell")) {
          orders.add(new Order(Order.Type.SELL, price, quantity));
        } else {
          System.out.println("Invalid input: " + line);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading orders from file: " + e.getMessage());
    }
    return orders;
  }

  private static List<Order> readOrdersFromCommandLine(String[] args) {
    List<Order> orders = new ArrayList<>();
    for (String arg : args) {
      String[] parts = arg.split(" ");
      if (parts.length != 3) {
        System.out.println("Invalid input: " + arg);
        continue;
      }
      double price;
      int quantity;
      try {
        price = Double.parseDouble(parts[1]);
        quantity = Integer.parseInt(parts[2]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input: " + arg);
        continue;
      }
      if (parts[0].equalsIgnoreCase("buy")) {
        orders.add(new Order(Order.Type.BUY, price, quantity));
      } else if (parts[0].equalsIgnoreCase("sell")) {
        orders.add(new Order(Order.Type.SELL, price, quantity));
      } else {
        System.out.println("Invalid input: " + arg);
      }
    }
    return orders;
  }

  private static void printManual() {
    System.out.println("Arguments missing. Please provide input arguments in one of the following formats:");
    System.out.println("1. As command line arguments: \"buy 100.50 10\" \"sell 99.90 5\" ...");
    System.out.println(
        "2. As a text file path: orders.txt (with each order on a separate line in the file for more details see README.md)");
    System.exit(1);
  }
}
