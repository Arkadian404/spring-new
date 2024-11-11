# 1: BUILD
#Get maven
FROM maven:3.9.8-amazoncorretto-21 AS build
#Set up working directory
WORKDIR /app
#Copy pom file
COPY pom.xml .
#Copy source code
COPY src ./src
#Build jar file from source code
RUN mvn package -DskipTests

#2: CREATE
#Get jdk 21
FROM amazoncorretto:21.0.4
#Set working directory
WORKDIR /app
#Copy jar files from build image
COPY --from=build /app/target/*.jar app.jar
#Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]