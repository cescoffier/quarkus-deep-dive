package me.escoffier.quarkus.demo.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Health
public class AnotherSensor implements HealthCheck {


    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.builder()
                .name("another-check")
                .up().build();
    }
}
