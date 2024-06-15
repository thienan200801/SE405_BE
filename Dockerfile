FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /IeltsTinder
COPY . .
RUN mvn clean package -DskipTests
CMD mvn spring-boot:run