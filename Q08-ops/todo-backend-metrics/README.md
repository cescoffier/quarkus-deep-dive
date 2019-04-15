# Quarkus Metrics

This application reuses the TODO Backend application but enables the metrics support

## Database

Run:

```bash
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 \
    --name postgres-quarkus-rest-http-crud -e POSTGRES_USER=restcrud \
    -e POSTGRES_PASSWORD=restcrud -e POSTGRES_DB=rest-crud \
    -p 5432:5432 postgres:10.5
```

## Prometheus

From the `Q08-ops/todo-backend-metrics` directory launch:

```bash
docker run -p 9090:9090 -v ${PWD}/prometheus/prometheus.yaml:/etc/prometheus/prometheus.yml prom/prometheus
```

If you are on Linux, edit `prometheus/prometheus.yaml` and replace `docker.for.mac.host.internal` with `localhost`

## Application

```bash
mvn compile quarkus:dev
```

Open: http://localhost:8080/ and http://localhost:8080/metrics

## Demo

1. First, open the `TodoResource` and show the `@Timed` and `@Counted` annotation on each method. 
1. Then show the `count` method and explain `@Gauge`
1. Open in a browser the application (http://localhost:8080)
1. Open in a browser the application (http://localhost:8080/metrics)
1. Explain the Prometheus format
1. Open Prometheus (http://localhost:9090)
1. Go to status -> target and check the endpoint is healthy
1. Go to "graph" -> and in the query field enter `application:io_quarkus_sample_todo_resource_number_of_items`
1. Click on Execute and select graph, reduce the time window to 5 minutes. 
1. In the application, create some items, mark some completed, and clear completed items
1. Check that the graph shows the fluctuation (interval 15s, so it may miss states)
1. Same with `application:io_quarkus_sample_todo_resource_get_all_time_seconds` (time)

