# Quarkus Hello World


## Live coding

_Prerequisites_:
* Delete the `hello-deboxx-france` directory
* In the terminal, be sure to have set the `GRAALVM_HOME` environment variable
* having _minikube_ running is a plus

### Project generation and run

1. Generate project
```bash
mvn io.quarkus:quarkus-maven-plugin:0.13.3:create \
    -DprojectGroupId=fr.devoxx \
    -DprojectArtifactId=hello-devoxx-france \
    -DclassName="fr.devoxx.HelloResource" \
    -Dpath="/hello"
```
1. Navigate to the directory and launch the application
```bash
cd hello-devoxx-france
mvn compile quarkus:dev
```
1. Explain the `pom.xml` file (BOM, extensions, tests)
1. Open the `HelloResource` class, explain the code 
1. Open `application.properties` and `META-INF/resources` (static resources)
1. Open browser to http://localhost:8080
1. Open browser to http://localhost:8080/hello
1. Change the greeting message in the `HelloResource`, refresh browser

### Add method

1. Add method: 
    ```
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city() {
        return "paris";
    }
    ```
1. Open browser to `http://localhost:8080/hello/city`

### Configuration

1. In the resource, add 
    ```
    @Inject @ConfigProperty(name = "greeting") String greeting;
    ```
1. Change the hello method to return the greeting message:
    ```
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return greeting;
    }
    ```    
1. Open browser to `http://localhost:8080/hello
1. Explain the error
1. Open the `application.properties` file and add:
    ```
    greeting = hello
    ``` 
1. Refresh browser
1. In the resource class, add:
    ```
    @Inject @ConfigProperty(name = "city") Optional<String> city;
    ```
1. Replace the `city` method with:
    ```
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city() {
        return city.orElse("valence");
    }
    ```
1. Open browser to `http://localhost:8080/hello/city`

### Introduce a bean

1. Create class `MyBean` in the `fr.devoxx` package with the following content:
    ```
    @ApplicationScoped
    public class MyBean {
    
        @Inject @ConfigProperty(name = "greeting") String greeting;
    
        public String greeting() {
            return greeting;
        }
    }
    ```            
2. Update the content of `HelloResource` to become:
    ```
    @Inject MyBean bean;
       // ...
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return bean.greeting();
    }
    ```    
3. Open browser to `http://localhost:8080/hello`

### Native packaging

1. Exit application (`CTRL+C` in the terminal)
1. Launch `mvn clean package -Pnative`    
1. While it's packaging, open `pom.xml` and explain:
    * the `native` profile
    * the `failsafe` configuration
1. Run application: 

### Linux executable creation

1. Exit application (`CTRL+C` in the terminal)    
1. Run: ` mvn clean package -Pnative -Dnative-image.docker-build=true`
1. While building, explain the architecture issue
1. While building, explain the Docker file (native)
1. Once built, try to run it and fail (on Mac) with: exec format error:
    ```
    ./target/hello-devoxx-france-1.0-SNAPSHOT-runner 
    ```
1. Build container with: `docker build -f src/main/docker/Dockerfile.native -t quarkus/hello-devoxx-france .`
1. Run container with: `docker run -i --rm -p 8080:8080 quarkus/hello-devoxx-france`

### Kubernetes

1. Start _minikube_ is not done yet:
    ```
    minikube start
    ```
1. then run:
    ```
    eval $(minikube docker-env)
    docker build -f src/main/docker/Dockerfile.native \
     -t quarkus/hello-devoxx-france .        
    ```
    Mention that this is the equivalent to pushing the image to a docker registry
1. Create application with:
    ```
    kubectl run quarkus-quickstart --image=quarkus/hello-devoxx-france:latest --port=8080 --image-pull-policy=IfNotPresent
    kubectl expose deployment quarkus-quickstart --type=NodePort
    ```  
1. Test application with:
    ```
    curl $(minikube service quarkus-quickstart --url)/hello
    ```      
    
    