# Use Maven image with JDK preinstalled
FROM maven:3.8.3-openjdk-17

# Set working directory
WORKDIR /opt

# Copy all project files
COPY . /opt/

# If you need to replace application.properties, copy it directly
#COPY application.properties src/main/resources/application.properties

# Build the project (skip tests for faster build)
RUN mvn clean package -DskipTests

# Set working directory to target for running the JAR
WORKDIR /opt/target/

# Expose port
EXPOSE 8080

# Run the JAR
CMD ["java","-jar","student-registration-backend-0.0.1-SNAPSHOT.jar"]
