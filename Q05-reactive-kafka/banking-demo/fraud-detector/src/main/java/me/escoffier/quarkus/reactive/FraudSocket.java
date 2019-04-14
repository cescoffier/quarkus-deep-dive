package me.escoffier.quarkus.reactive;

import io.smallrye.reactive.messaging.annotations.Stream;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;

@ApplicationScoped
@ServerEndpoint("/frauds")
public class FraudSocket {

    @Inject @Stream("frauds") PublisherBuilder<Fraud> stream;

    @OnOpen
    public void opening(Session session) {
        stream
                .forEach(s ->
                        session.getAsyncRemote().sendText(JsonbBuilder.create().toJson(s)))
                .run();
    }

}
