package me.escoffier.quarkus.kafka;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MyKafkaProducer {

    private Random random = new Random();

    @Outgoing("my-stream")
    public Flowable<Integer> produce() {
        return Flowable.interval(1, TimeUnit.SECONDS)
                .map(msg -> random.nextInt(100));
    }

}
