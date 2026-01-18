FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml pom.xml
COPY ewm-stats-service/pom.xml ewm-stats-service/pom.xml
COPY ewm-stats-service/stats-dto/pom.xml ewm-stats-service/stats-dto/pom.xml
COPY ewm-stats-service/stats-client/pom.xml ewm-stats-service/stats-client/pom.xml
COPY ewm-stats-service/stats-server/pom.xml ewm-stats-service/stats-server/pom.xml
COPY ewm-main-service/pom.xml ewm-main-service/pom.xml

RUN mvn -q -DskipTests dependency:go-offline

# Теперь копируем исходники и собираем только stats-server (с зависимыми модулями)
COPY ewm-stats-service ewm-stats-service
COPY ewm-main-service ewm-main-service

RUN mvn -q -pl ewm-stats-service/stats-server -am -DskipTests package


FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/ewm-stats-service/stats-server/target/*.jar app.jar

EXPOSE 9090

ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
