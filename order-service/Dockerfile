# ---------- Stage 1: Build ----------
    FROM gradle:8.5.0-jdk21 AS builder
    WORKDIR /app
    
    # Copy Gradle build files and source
    COPY . .
    
    # Build the application (skip tests if needed)
    RUN gradle build --no-daemon
    
    # ---------- Stage 2: Run ----------
    FROM eclipse-temurin:21-jre-alpine
    
    # Set workdir in the new image
    WORKDIR /app
    
    # Copy the built JAR from the builder image
    COPY --from=builder /app/app/build/libs/*.jar app.jar
    
    # Expose the port your app uses (adjust if needed)
    EXPOSE 8080
    
    # Run the application
    ENTRYPOINT ["java", "-jar", "app.jar"]
    