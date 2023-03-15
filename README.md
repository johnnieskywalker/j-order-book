# Order Book

## Example way to call with args from console

```sh
java -classpath .:target/dependency/* Main "buy 1000 99" "buy 500 99" "buy 500 98" "buy 1200 98" "sell 2000 101"
```

## Example with load from file

```sh
java -classpath .:target/dependency/* Main "orders.txt"
```

example orders.txt file

```
buy 100.5 10
sell 101.2 5
buy 99.5 20
sell 101.0 15
```