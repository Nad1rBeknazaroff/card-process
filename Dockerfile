# Use Maven image with JDK 17 for the build stage
FROM maven:3.8.6-eclipse-temurin-17-alpine AS BUILD_STAGE

# Set the working directory in the container
WORKDIR /home/maven

# Copy the project files to the container
COPY . .

# Run Maven to build the project
RUN mvn clean package -DskipTests

# Use a lightweight JDK 17 image for the runtime stage
FROM eclipse-temurin:17-jre-alpine

# Set environment variables
ENV ARTIFACT_NAME=card-process-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/app

# Copy the built jar file from the build stage to the runtime stage
COPY --from=BUILD_STAGE /home/maven/target/$ARTIFACT_NAME $APP_HOME/

# Set the working directory
WORKDIR $APP_HOME

# Specify the entry point for the container
ENTRYPOINT ["sh", "-c", "java -jar $ARTIFACT_NAME"]
