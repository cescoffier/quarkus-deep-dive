package me.escoffier.quarkus.reactive;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class FraudDetector {

    private final Jsonb jsonb = JsonbBuilder.create();

    @Incoming("transactions")
    @Outgoing("frauds")
    public Flowable<Fraud> detectFraud(Flowable<String> transactions) {
        return transactions
            .map(tx -> jsonb.fromJson(tx, Transaction.class))
            .groupBy((Transaction::getAccount))
            .flatMap(group ->
                    group.buffer(5, 1)
                         .filter(list -> list.stream()
                              .mapToDouble(Transaction::getAmount)
                              .sum() > 500)
                         .map(Fraud::new));
    }

}
