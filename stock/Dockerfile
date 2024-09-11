# Use Amazon Corretto 22 as the base image
FROM amazoncorretto:22-alpine-jdk

# Set the working directory
WORKDIR /sysx/progs

# Create a directory for logs
RUN mkdir ./logs

# Expose the application's port
EXPOSE 8081

# Copy the Spring Boot application JAR file to the container
COPY ./target/stock-0.0.1-SNAPSHOT.jar ./

# Copy the application properties file to the container
COPY ./src/main/resources/application.properties ./

# Install PostgreSQL client tools for debugging (optional)
RUN apk add --no-cache postgresql-client

# Define the entrypoint and command to run the application with JVM options
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-Xss512k", "-Xms128m", "-Xmx256m", "-jar", "stock-0.0.1-SNAPSHOT.jar", "--spring.config.location=./application.properties"]


#FROM amazoncorretto:22
#COPY target/*.jar stockApp.jar
#EXPOSE 8081
#ENTRYPOINT ["java","-jar","stockApp.jar"]