package org.acme.rest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MyRestResource {

    private List<Product> products = new CopyOnWriteArrayList<>();

    @GET
    public List<Product> getAll() {
        return products;
    }

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

    @POST
    public Response createOne(@Valid Product product) {
        products.add(product);
        return Response.created(UriBuilder.fromUri("/" + product.getId()).build()).build();
    }

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

}
