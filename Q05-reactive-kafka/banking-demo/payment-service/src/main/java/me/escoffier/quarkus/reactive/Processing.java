package me.escoffier.quarkus.reactive;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class Processing {

    private Jsonb jsonb;

    @PostConstruct
    public void init() {
        jsonb = JsonbBuilder.create();
    }

    @Incoming("internal")
    @Outgoing("payments")
    public String process(Payment payment) {
        payment.setKind("PAYMENT");
        return jsonb.toJson(payment);
    }

}
