# =========================
# 1. BUILD STAGE
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Копируем parent pom
COPY pom.xml .

# Копируем pom'ы модулей
COPY ewm-main-service/pom.xml ewm-main-service/pom.xml
COPY ewm-stats-service/pom.xml ewm-stats-service/pom.xml

# Скачиваем зависимости (кеш)
RUN mvn -B -pl ewm-main-service -am dependency:go-offline

# Копируем исходники main-service
COPY ewm-main-service/src ewm-main-service/src

# Собираем ТОЛЬКО main-service
RUN mvn -pl ewm-main-service -am clean package -DskipTests

# =========================
# 2. RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Копируем JAR
COPY --from=builder /build/ewm-main-service/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]