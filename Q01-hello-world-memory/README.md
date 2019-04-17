# Q01 - Java and RSS

Make sure to use HotSpot JDK and not GraalVM

Open in idea `idea .`  
Show simple app

* show `pom.xml`
* Show JAX-RS resource

    mvn package

Demo the app

    java -jar ./target/restful-endpoint-thorntail.jar

open http://localhost:8080/rest/hello

Then play with memory usage

    java -Xmx10m -Xms10m -jar ./target/restful-endpoint-thorntail.jar

Different OOME

    java -Xmx28m -Xms28m -jar ./target/restful-endpoint-thorntail.jar

Minimum heap size

    java -Xmx29m -Xms29m -jar ./target/restful-endpoint-thorntail.jar
    
    http :8080/rest/hello
    
    ps x -o pid,rss,command | grep java

RSS in kbytes

    jconsole
