package me.escoffier.demo;

import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyAsyncMessageReceiver {

    @ConsumeEvent("some-address")
    public String greeting(String greeting) {
        return "Hello " + greeting;
    }

}
