FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app


COPY pom.xml .
RUN mvn -B dependency:go-offline


COPY src ./src


RUN mvn -B clean package -DskipTests


FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring


COPY --from=build /app/target/*.jar app.jar

# Executar como utilizador não privilegiado
USER spring:spring

# Porta utilizada pela API
EXPOSE 8080

# Limitar a memória da JVM com base nos recursos do contentor
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
