package fr.devoxx;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello")
public class HelloResource {

    @Inject MyBean bean;
    @Inject @ConfigProperty(name = "city") Optional<String> city;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return bean.greeting();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city() {
        return city.orElse("valence");
    }

}