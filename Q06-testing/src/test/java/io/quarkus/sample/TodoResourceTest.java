package io.quarkus.sample;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.mapper.TypeRef;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.apache.http.HttpHeaders;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.*;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.Is.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoResourceTest {

    private static final PostgreSQLContainer database = new PostgreSQLContainer<>()
            .withDatabaseName("rest-crud")
            .withUsername("restcrud")
            .withPassword("restcrud")
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd ->
                    cmd
                            .withHostName("localhost")
                            .withPortBindings(new PortBinding(Ports.Binding.bindPort(5432), new ExposedPort(5432)))
            );

    @BeforeAll
    static void startDatabase() {
        database.start();
    }

    @AfterAll
    static void stopDatabase() {
        database.stop();
    }

    @Test
    @Order(1)
    void testInitialItems() {
        List<Todo> todos = get("/api").then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract().body().as(getTodoTypeRef());
        assertEquals(4, todos.size());

        get("/api/1").then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("title", is("Introduction to Quarkus"))
                .body("completed", is(true));
    }

    @Test
    @Order(2)
    void testAddingAnItem() {
        Todo todo = new Todo();
        todo.title = "testing the application";
        given()
                .body(todo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post("/api")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("title", is(todo.title))
                .body("completed", is(false))
                .body("id", is(5));

        List<Todo> todos = get("/api").then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract().body().as(getTodoTypeRef());
        assertEquals(5, todos.size());
    }

    @Test
    @Order(3)
    void testUpdatingAnItem() {
        Todo todo = new Todo();
        todo.title = "testing the application (updated)";
        todo.completed = true;
        given()
                .body(todo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .pathParam("id", 5)
                .when()
                .patch("/api/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("title", is(todo.title))
                .body("completed", is(true))
                .body("id", is(5));
    }

    @Test
    @Order(4)
    void testDeletingAnItem() {
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .pathParam("id", 5)
                .when()
                .delete("/api/{id}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        List<Todo> todos = get("/api").then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract().body().as(getTodoTypeRef());
        assertEquals(4, todos.size());
    }

    @Test
    @Order(5)
    void testDeleteCompleted() {
        delete("/api")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        List<Todo> todos = get("/api").then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract().body().as(getTodoTypeRef());
        assertEquals(3, todos.size());
    }

    @NotNull
    private TypeRef<List<Todo>> getTodoTypeRef() {
        return new TypeRef<List<Todo>>() {
            // Kept empty on purpose
        };
    }

}
