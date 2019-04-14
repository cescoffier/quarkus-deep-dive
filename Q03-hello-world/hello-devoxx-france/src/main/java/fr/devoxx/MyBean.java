package fr.devoxx;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MyBean {

    @Inject @ConfigProperty(name = "greeting") String greeting;

    public String greeting() {
        return greeting;
    }
}
