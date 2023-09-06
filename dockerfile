FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar
CMD ["java", "-jar", "/app/user-service.jar"]