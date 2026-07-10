# Build stage
FROM maven:3.9-eclipse-temurin-25-alpine AS builder
WORKDIR /build

# Cache dependencies for faster builds
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]