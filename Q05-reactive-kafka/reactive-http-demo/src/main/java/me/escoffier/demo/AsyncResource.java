package me.escoffier.demo;

import io.vertx.axle.core.eventbus.EventBus;
import io.vertx.axle.core.eventbus.Message;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class AsyncResource {

    @GET
    @Path("/sync")
    public String synchronousHello() {
        return "Hello";
    }

    @GET
    @Path("/async")
    public CompletionStage<String> asyncHello() {
        return CompletableFuture.completedFuture("Async-Hello");
    }

    @Inject EventBus bus;
    @GET
    @Path("/async/{name}")
    public CompletionStage<String> message(@PathParam("name") String name) {
        return bus.<String>send("some-address", name)
                .thenApply(Message::body)
                .thenApply(String::toUpperCase);
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> stream() {
        return ReactiveStreams.of("a", "b", "c")
                .map(String::toUpperCase)
                .buildRs();
    }

}
