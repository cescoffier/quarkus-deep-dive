package me.escoffier.quarkus.demo.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
@Health
public class AnotherSensor implements HealthCheck {

    Random random = new Random();

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name("another sensor");
        if (random.nextBoolean()) {
            return builder.up().withData("message", "lucky you!").build();
        } else {
            return builder.down().withData("message", "not that lucky").build();
        }
    }
}
