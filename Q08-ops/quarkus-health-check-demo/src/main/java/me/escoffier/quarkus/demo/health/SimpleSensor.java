package me.escoffier.quarkus.demo.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Health
public class SimpleSensor implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.builder().name("My simple sensor").up().build();
    }
}
