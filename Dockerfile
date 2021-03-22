# Based on https://pete-woods.com/2019/02/production-grade-spring-boot-docker-images/

# Build step
FROM openjdk:11-jdk-slim AS java-build
WORKDIR /app/
# Copy the files that affect dependencies (Maven pom and wrapper)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
# Download our dependencies into this layer
RUN ./mvnw dependency:go-offline
# Now copy the source (which doesn't affect the dependencies)
COPY src src
# Now build
RUN ./mvnw package



# Image step
FROM openjdk:11-jre-slim
COPY --from=java-build /app/target/searchcli-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
