FROM openjdk:17

COPY target/personapi-0.0.1-SNAPSHOT.jar /app/personapi-0.0.1-SNAPSHOT.jar

WORKDIR /app

EXPOSE 8080

CMD ["java","-jar","personapi-0.0.1-SNAPSHOT.jar"]