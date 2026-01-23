# =========================
# 1. BUILD STAGE
# =========================
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

# Копируем parent pom
COPY pom.xml .

# Копируем pom'ы модулей
COPY ewm-main-service/pom.xml ewm-main-service/pom.xml
COPY ewm-stats-service/pom.xml ewm-stats-service/pom.xml

# Копируем исходники main-service
COPY ewm-main-service/src ewm-main-service/src

# Собираем ТОЛЬКО main-service
RUN mvn -pl ewm-main-service -am clean package -DskipTests

# =========================
# 2. RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Копируем свежесобранный JAR
COPY --from=builder /build/ewm-main-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
