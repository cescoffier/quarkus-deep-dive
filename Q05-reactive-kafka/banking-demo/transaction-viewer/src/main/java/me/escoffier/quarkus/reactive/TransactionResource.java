package me.escoffier.quarkus.reactive;

import io.smallrye.reactive.messaging.annotations.Stream;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.StringReader;

@Path("/tx")
public class TransactionResource {

    @Inject @Stream("transactions") PublisherBuilder<String> stream;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> stream() {
        return stream
                .map(tx -> Json.createReader(new StringReader(tx)).readObject())
                .filter(json -> json.getString("account").equals("1111"))
                .map(JsonValue::toString)
                .buildRs();
    }
}
