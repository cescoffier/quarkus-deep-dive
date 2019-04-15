# Health Check Demo

## Run

```bash
mvn compile quarkus:dev
```

Then open browser to http://localhost:8080/health.


## Live coding instructions

_Prerequisites:_

* Remove the `AnotherSensor` and `SimplerSensor` classes

_Instructions:_

1. Run `mvn clean compile quarkus:dev`
1. Show `pom.xml` file and explain `quarkus-smallrye-health`
1. Open http://localhost:8080/health
1. Create `me.escoffier.quarkus.demo.health.SimpleSensor`:
    ```
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
    ```
1. Refresh http://localhost:8080/health
1. Also show `curl -v http://localhost:8080/health` and mention the `200 OK`
1. Create `me.escoffier.quarkus.demo.health.AnotherSensor`:
    ```
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
    ```
1. Explain the composite structure
1. Run curl a few times until you get a `503`
    

