package me.escoffier.quarkus.kafka;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyKafkaConsumer {

    @Incoming("my-stream")
    public void consume(int value) {
        System.out.println("Received: " + value);
    }

}
