# Simple REST

## Run

```bash
mvn compile quarkus:dev
```

Then:

```bash
http :8080
# Empty list
http POST :8080 name=a
# Check returned URL
http $URL
# Update
http PATH $URL name=a2
# Create another one
http POST :8080 name=b
# List all
http :8080
# Delete one
http DELETE $URL
# List all
http :8080
```

## Live coding session

_Prerequisites_:

* Delete the methods from the `MyRestResource` class (keep the class declaration and the list declaration).

1. Run `mvn compile quarkus:dev` 
1. Show the Product class, explain the `@NotBlank`
1. Open The `MyRestResource` class
1. Implement the `getAll` method:
    ```
    @GET
    public List<Product> getAll() {
        return products;
    }
    ```
1. Implement the `getOne` method:
    ```
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") String id) {
        Optional<Product> any = products.stream().filter(p -> p.getId().equals(id)).findAny();
        if (any.isPresent()) {
            return Response.ok(any.get()).build();
        } else {
            return Response.status(404).build();
        }
    }
    ```
    Explain the 404 case
    (_NOTE:_ would be better with JDK 11)
1. Implement `createOne`
    ```
    @POST
    public Response createOne(@Valid Product product) {
        products.add(product);
        return Response.created(UriBuilder.fromUri("/" + product.getId()).build()).build();
    }
    ```
    Explain the URI creation
1. Implement `updateOne`    
    ```
    @PATCH
    @Path("/{id}")
    public Response updateOne(@Valid Product product, @PathParam("id") String id) {
        Optional<Product> any = products.stream().filter(p -> p.getId().equals(id)).findAny();

        if (any.isPresent()) {
            any.get().setName(product.getName());
            return Response.ok(any.get()).build();
        } else {
            return Response.status(404).build();
        }
    }
    ```
1. Implement `deleteOne`
    ```
        @DELETE
        @Path("/{id}")
        public Response deleteOne(@PathParam("id") String id) {
            Optional<Product> any = products.stream().filter(p -> p.getId().equals(id)).findAny();
    
            if (any.isPresent()) {
                products.remove(any.get());
                return Response.noContent().build();
            } else {
                return Response.status(404).build();
            }
        }
    ```        
1. Run the `http` invocation from above
        