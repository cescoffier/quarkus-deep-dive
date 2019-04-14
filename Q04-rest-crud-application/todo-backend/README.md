# TODO Backend Application with Quarkus

The application illustrates the usage of JPA with Panache.

## Database

Run:

```bash
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 \
    --name postgres-quarkus-rest-http-crud -e POSTGRES_USER=restcrud \
    -e POSTGRES_PASSWORD=restcrud -e POSTGRES_DB=rest-crud \
    -p 5432:5432 postgres:10.5
```

## Application

```bash
mvn compile quarkus:dev
```

Open: http://localhost:8080/


## Live coding

_Prerequisites:_

1. Start the database
2. Remove the methods from the `TodoResource` class, keep the `opts` method

_Live coding:_

1. Explain the `Todo` class
    * Explain the `@Entity` and `PanacheEntity`
    * Explain the `@Column`
    * Explain the smart queries and actions    
    * Explain that getter and setter are not required

1. Run `mvn compile quarkus:dev`
1. Open the `application.properties` to show how is configured the JPA layer
1. Open the `imports.sql` file (fixtures)
1. Show that the web page is empty, optionally show the content of the table 
1. Open the `TodoResource` and implement `getAll`:
    ```
    @GET
    public List<Todo> getAll() {
        return Todo.listAll(Sort.by("order"));
    }
    ```
1.  Refresh web page, and show that we have our list   
1. Implement `getOne`
    ```
    @GET
    @Path("/{id}")
    public Todo getOne(@PathParam("id") Long id) {
        Todo entity = Todo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        return entity;
    }
    ```
    Explain the `WebApplicationException`
 1. Implement `create`   
    ```
    @POST
    @Transactional
    public Response create(@Valid Todo item) {
        item.persist();
        return Response.status(Status.CREATED).entity(item).build();
    }
    ```
    Explain `@Transactional` (write access)
    Explain `@Valid`
    Explain `persist`
    Go to page and add item
    If possible, show table content
1. Add `update` method:    
    ```
    @PATCH
    @Path("/{id}")
    @Transactional
    public Response update(@Valid Todo todo, @PathParam("id") Long id) {
        Todo entity = Todo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Item with id of " + id + " does not exist.", 404);
        }
        entity.id = id;
        entity.completed = todo.completed;
        entity.order = todo.order;
        entity.title = todo.title;
        entity.url = todo.url;
        return Response.ok(entity).build();
    }
    ```
1. Implement the two `delete` methods
    ```
    @DELETE
    @Transactional
    public Response deleteCompleted() {
        Todo.deleteCompleted();
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Long id) {
        Todo entity = Todo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        entity.delete();
        return Response.noContent().build();
    }    
    ```
    * Explain the smart actions
    * Explain the explicit delete