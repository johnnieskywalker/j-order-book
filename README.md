# Order Book

## Example way to call with args from console

```sh
java -classpath .:target/dependency/* Main "buy 1000 99" "buy 500 99" "buy 500 98" "buy 1200 98" "sell 2000 101"
```

## Example run with built jar file

```sh
java -jar target/myartifactid-0.0-SNAPSHOT.jar orders.txt
```

### Test files
#### orders.txt
orders.txt contain the same data as test, which is first example in  Assignment where trades occur
There's also test scenario that tests this scenari and proofs it works
givenAggressiveSellOrder_whenAddedToOrderBook_thenShouldMatchBuyOrdersInPriceTimePriority
orders.txt file
```
buy 99 1000
buy 99 500
buy 98 1200
sell 101 2000
sell 95 2000
```

### orders3.txt
It's file with lots of orders generated to present the working order book with many orders and trades happening live

## Unit Testing

Run `mvn test`

## Building from source 

Run `mvn install`