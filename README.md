# Order Book

## Example way to call with args from console

```sh
java -classpath .:target/dependency/* Main "buy 1000 99" "buy 500 99" "buy 500 98" "buy 1200 98" "sell 2000 101"
```

## Example with load from file

```sh
java -classpath .:target/dependency/* Main "orders.txt"
```
orders.txt contain the same data as test givenAggressiveSellOrder_whenAddedToOrderBook_thenShouldMatchBuyOrdersInPriceTimePriority
It's the example where trades occur
example orders.txt file

```
buy 99 1000
buy 99 500
buy 98 1200
sell 101 2000
sell 95 2000
```

## Unit Testing

Run `mvn test`

## Building from source 

Run `mvn install`