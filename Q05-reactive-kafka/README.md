# Quarkus Reactive Demo

This directory contains a set of demo around _reactive_ in Quarkus.

## Build

```bash
mvn clean package
```

## Prerequisites

Run Kafka with:

```bash
docker-compose up
```

## Reactive HTTP demo

_Main directory_: reactive-http-demo

_Prerequisites:_
1. Delete the `asynchronousHello`, `message` and `stream` methods from `AsyncResource`
2. Delete the `greeting` method from `MyAsyncMessageReceiver`

_Live coding_

1. Open the `pom.xml` file and explain the various dependencies
1. Open the `AsyncResource` and discuss the synchronous method
1. Run `mvn compile quarkus:dev` and open http://localhost:8080/sync
1. Add the `asyncHello` method:
    ```
    @GET
    @Path("/async")
    public CompletionStage<String> asyncHello() {
        return CompletableFuture.completedFuture("Async-Hello");
    }
    ```
1. Ok, but it's not really async, let's add a bit of asynchrony with the event bus
1. Add:
    ``` 
    @Inject EventBus bus;
    @GET
    @Path("/async/{name}")
    public CompletionStage<String> message(@PathParam("name") String name) {
        return bus.<String>send("some-address", name)
                .thenApply(Message::body)
                .thenApply(String::toUpperCase);
    }
    ```    
1. Try with http://localhost:8080/async/quarkus - explain the internal server error (no one is listening)
1. In `MyAsyncMessageReceiver`, add:
    ```
    @ConsumeEvent("some-address")
    public String greeting(String greeting) {
        return "Hello " + greeting;
    }
    ```
1. Retry http://localhost:8080/async/quarkus
1. Let's play with streams also
1. Add in the `AsyncResource` class:        
    ```
    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> stream() {
        return ReactiveStreams.of("a", "b", "c")
                .map(String::toUpperCase)
                .buildRs();
    }
    ```
1. Open Chrome to http://localhost:8080/stream

## Demo Hello Kafka 

This demo illustrates how to send messages to Kafka and consume them from Kafka.

### Kafka Consumer

1. Introduce Reactive Messaging and the Kafka connector
1. Open `me.escoffier.quarkus.kafka.MyKafkaConsumer`
1. Explain `@Incoming`
1. Open `application.properties`
1. Explain the Kafka configuration
1. Run:
    ```bash
    cd Q05-reactive-kafka/hello-kafka-demo/quarkus-kafka-consumer
    mvn compile quarkus:dev
    ```    

### Kafka Producer

1. Open `me.escoffier.quarkus.kafka.MyKafkaProducer`
1. Explain `@Outgoing`
1. Open `application.properties`
1. Explain the Kafka configuration
1. Run:
    ```bash
    cd Q05-reactive-kafka/hello-kafka-demo/quarkus-kafka-producer
    mvn compile quarkus:dev
    ```    
1. Show the output
    
## Banking demo
        
### Payment Service

**Important topics**

* Usage of an `Emitter` to send a message to Kafka
* Kafka configuration (`application.properties)
* Usage of JSON-B to map `Payment to `String`

```bash
cd payment-service
mvn compile quarkus:dev
```

Listening on port `8081`.

Send payments with:

```bash
http :8081/payment account=1111 amount:=10
http :8081/payment account=1112 amount:=520
http :8081/payment account=1113 amount:=50
http :8081/payment account=1111 amount:=45
http :8081/payment account=1112 amount:=22
http :8081/payment account=1113 amount:=345
http :8081/payment account=1111 amount:=3
http :8081/payment account=1112 amount:=67
http :8081/payment account=1113 amount:=99
http :8081/payment account=1111 amount:=100
http :8081/payment account=1112 amount:=345
http :8081/payment account=1113 amount:=678
```

_Optional_: Use [Kafka Tool](http://www.kafkatool.com/) to check topic content:

1. Connect to localhost
2. Navigate to `Clusters -> Topics -> Transactions`
3. Click on data
4. Click on the green arrow

### Transaction Viewer

**Important topics**

* From Kafka to Websocket
* Injection of stream (`@Stream`)

```bash
cd transaction-viewer
mvn compile quarkus:dev
``` 

Listening on port `8082`

1. Open your browser to http://localhost:8082
2. By default, it shows the transactions for the account `1111`.
3. Open `me.escoffier.quarkus.reactive.TransactionSocket` and change the account to `1112`.
4. Save and refresh the browser
5. Send another payment:
```bash
http :8081/payment account=1112 amount:=1234
```
6. It should appear automatically

IMPORTANT: Do not refresh the browser, as the message have been acknowledged, you will only see the new ones.

### Fraud Detector

**Important topics**

* Processing of stream to to complex stream logic

```bash
cd fraud-detector
mvn compile quarkus:dev
``` 

Listening on port `8083`

1. Open your browser to http://localhost:8083
2. By default it detects fraud with a threshold set to 5000
3. Generate a fraud:
```bash
http :8081/payment account=1112 amount:=2234
http :8081/payment account=1112 amount:=200
http :8081/payment account=1112 amount:=300
http :8081/payment account=1112 amount:=2000
http :8081/payment account=1112 amount:=1000
```
4. The page should list it immediately
5. Edit `me.escoffier.quarkus.reactive.FraudDetector` and extend the threshold
6. Refresh the page

IMPORTANT: Do not refresh the browser, as the message have been acknowledged, you will only see the new ones.

 