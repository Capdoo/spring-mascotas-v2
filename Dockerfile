#for render
#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /target/spring-mascotas-backend-0.0.1-SNAPSHOT.jar mascotas.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","mascotas.jar"]


#for local
# FROM openjdk:11-jdk-slim
# COPY "./target/spring-mascotas-backend-0.0.1-SNAPSHOT.jar" "mascotas.jar"
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","mascotas.jar"]

