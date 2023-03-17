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

### example1.txt
It's file with orders from Example 1 from assignment where multiple orders are added none is matched, it's also covered with test givenOrdersWithNoMatch_whenAddedToOrderBook_thenShouldNotMatchOrders

This is the exact output of example1.txt and it matches requirements:

```sh
java -jar target/myartifactid-0.0-SNAPSHOT.jar example1.txt
4,B,99,50000
1,B,98,25500
2,S,100,500
3,S,100,10000
5,S,103,100
6,S,105,20000
```

### example2.txt
This is file which represents Example 2, which is the continuation of Example 1 but then  Buy 16000 @ 105p appears. This is output for Example2 which matches requirements too:

```sh
java -jar target/myartifactid-0.0-SNAPSHOT.jar example2.txt
trade 2,7,100.0,500
trade 3,7,100.0,10000
trade 5,7,103.0,100
trade 6,7,105.0,5400
4,B,99,50000
1,B,98,25500
6,S,105,14600
```

### orders3.txt
It's file with lots of random orders generated to present the working order book with many orders and trades happening live

## Unit Testing

Run `mvn test`

## Building from source 

Run `mvn install`