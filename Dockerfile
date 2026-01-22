# -------- Build stage --------
FROM gradle:9.2.1-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# -------- Run stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Cloud Run uses $PORT; default to 8080 locally
ENV PORT=8080

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
