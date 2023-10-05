FROM openjdk:17-jdk-alpine
COPY . .
ARG JAR-FILE=build/*.jar
COPY ./build/libs/Erudite_Event_System-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]



#FROM maven:3.8.5-openjdk-17 AS build
##ARG JAR-FILE=build/*.jar
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17.0.1-jdk-slim
#COPY --from=build /target/StageOne-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar","/app.jar"]