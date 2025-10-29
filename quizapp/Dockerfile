# ---------- Stage 1: Build the Spring Boot app ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project and build it
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Create lightweight runtime image ----------
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the same port as in application.properties
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
