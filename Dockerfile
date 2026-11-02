# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw mvnw.cmd pom.xml ./
RUN chmod +x mvnw
# Download dependencies first (cached layer)
RUN ./mvnw dependency:resolve -B
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/movie-ticket-booking-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
