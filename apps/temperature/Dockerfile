FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY . /build
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/target/temperature-0.0.1.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]