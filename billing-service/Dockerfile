# ---- Stage 1: Build the application ----
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only pom.xml and download dependencies first
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Build the application (skip tests if needed)
RUN mvn clean package -DskipTests

# ---- Stage 2: Run the application ----
FROM openjdk:21-jdk AS runner

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/billing-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4001

ENTRYPOINT ["java", "-jar", "app.jar"]
