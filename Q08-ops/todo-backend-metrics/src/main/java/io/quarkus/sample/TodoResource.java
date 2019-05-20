package io.quarkus.sample;

import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/api")
@Produces("application/json")
@Consumes("application/json")
public class TodoResource {

    @GET
    @Counted(name = "getAllCount", monotonic = true,
            description = "How many getAll calls have been done.")
    @Timed(name = "getAllTime",
            description = "How long does the getAll method takes.",
            unit = MetricUnits.MILLISECONDS)
    public List<Todo> getAll() {
        return Todo.listAll(Sort.by("order"));
    }

    @GET
    @Path("/{id}")
    public Todo getOne(@PathParam("id") Long id) {
        Todo entity = Todo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        return entity;
    }

    @POST
    @Transactional
    @Counted(name = "createCount", monotonic = true, description = "How many create calls have been done.")
    @Timed(name = "createTime", description = "How long does the create method takes.", unit = MetricUnits.MILLISECONDS)
    public Response create(@Valid Todo item) {
        item.persist();
        return Response.status(Status.CREATED).entity(item).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    @Counted(name = "updateCount", monotonic = true, description = "How many update calls have been done.")
    @Timed(name = "updateTime", description = "How long does the update method takes.", unit = MetricUnits.MILLISECONDS)
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

    @DELETE
    @Transactional
    @Counted(name = "deleteCompletedCount", monotonic = true, description = "How many deleteCompleted calls have been done.")
    @Timed(name = "deleteCompletedTime", description = "How long does the deleteCompleted method takes.", unit = MetricUnits.MILLISECONDS)
    public Response deleteCompleted() {
        Todo.deleteCompleted();
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Counted(name = "deleteOneCount", monotonic = true, description = "How many deleteOne calls have been done.")
    @Timed(name = "deleteOneTime", description = "How long does the deleteOne method takes.", unit = MetricUnits.MILLISECONDS)
    public Response deleteOne(@PathParam("id") Long id) {
        Todo entity = Todo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        entity.delete();
        return Response.noContent().build();
    }


    @Gauge(name = "numberOfItems", unit = MetricUnits.NONE,
            description = "Number of todo list items.")
    public long count() {
        return Todo.findCompleted().size();
    }

}