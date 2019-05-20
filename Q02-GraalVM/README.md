# Q02 - Using GraalVM by hand

Download GraalVM 1.0rc14
set GRAALVM_HOME and JAVA_HOME to graalvm

## simple app Main

    mkdir target
    javac -d target src/*.java
    java -cp target/ Main
    native-image -cp target/ Main

    time java -cp target/ Main
    time ./main

## with resource Main2

    javac  -d target src/*.java ; cp src/*.properties target/
    time java -cp target/ Main2
    
    native-image -H:IncludeResources='.*/*.properties$' -cp target/ Main2

## reflection Main3

Show Main3

    javac  -d target src/*.java ; cp src/*.properties target/
    time java -cp ./target/ Main3
    
    native-image -H:IncludeResources='.*/*.properties$' -cp target/ Main3
    ./main3

    native-image -H:IncludeResources='.*/*.properties$' -H:ReflectionConfigurationFiles=others/reflectconfig.json -cp target/ Main3
    ./main3

## memory usage: Main4

    javac  -d target src/*.java ; cp src/*.properties target/
    time java -cp ./target/ Main4
    ps -o pid,rss,command | grep java

    native-image -H:IncludeResources='.*/*.properties$' -H:ReflectionConfigurationFiles=others/reflectconfig.json -cp target/ Main4
    ./main4
    
    ps -o pid,rss,command | grep main

## Dependencies

Imagine doing that with all your dependencies and with so called "dynamic" frameworks
