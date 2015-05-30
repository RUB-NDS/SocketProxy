# SocketProxy

Simple proxy designed to intercept and modify connections on the transport level. This means you can also modify TLS raw bytes.

In order to run this tool, you need Java 7 (or higher) and maven. You can compile it with:

$ cd SocketProxy

$ mvn clean package

Then, you can run it with:

$ cd target

$ java -jar SocketProxy-1.0-SNAPSHOT-jar-with-dependencies.jar

You can then define your listening port and forward address, and start the proxy.
