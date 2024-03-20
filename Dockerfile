# Base image
FROM eclipse-temurin:17-jdk-jammy as deps

# Working directory
WORKDIR /app

# Copying API server JAR file
COPY api-server/build/libs/api-server-1.0.0.jar .

# Command to run the API server
CMD ["java", "-jar", "api-server-1.0.0.jar"]