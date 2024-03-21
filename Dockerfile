# 빌드를 위한 이미지
FROM eclipse-temurin:17-jdk-jammy as builder

# 작업 디렉토리 설정
WORKDIR /app

# 소스 코드 복사
COPY . .

# 소스 코드 빌드
RUN ./gradlew :api-server:build

# Base image
FROM eclipse-temurin:17-jdk-jammy as deps

# Working directory
WORKDIR /app

# Copying API server JAR file
COPY --from=builder /app/api-server/build/libs/api-server-1.0.0.jar .

# Command to run the API server
CMD ["java", "-jar", "api-server-1.0.0.jar"]