package org.acme.rest;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Product {

    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String name;

    public String getId() {
        return id;
    }

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }
}
