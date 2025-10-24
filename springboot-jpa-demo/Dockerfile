# Use an official OpenJDK image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory inside the container
WORKDIR /app

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port your app runs on
EXPOSE 8080

# Run the Spring Boot jar
ENTRYPOINT ["java","-jar","target/springboot-jpa-demo-1.0.0.jar"]
