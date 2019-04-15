package me.escoffier.quarkus.demo.health;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class MyResource {


    @GET
    public String hello() {
        return "hello";
    }
}
