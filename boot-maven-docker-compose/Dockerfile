FROM java:8
ADD target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Xms256m","-Xmx512m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]