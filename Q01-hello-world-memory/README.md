# Live coding read me

Open in idea `idea .`  
Show simple app

* show `pom.xml`

```
mvn package
```

Then play with memory usage

```
java -Xmx10m -Xms10m -jar ./target/restful-endpoint-thorntail.jar
```

Different OOME

```
java -Xmx28m -Xms28m -jar ./target/restful-endpoint-thorntail.jar
```

Minumum heap size

```
java -Xmx29m -Xms29m -jar ./target/restful-endpoint-thorntail.jar
```

```
http :8080/rest/hello

ps x -o pid,rss,command | grep java
#RSS in kbytes
jconsole
```

