FROM openjdk:17-jdk-slim

WORKDIR /app

COPY mamaclare.jar mamaclare.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mamaclare.jar"]