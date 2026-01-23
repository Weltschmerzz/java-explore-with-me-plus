# =========================
# 1. BUILD STAGE
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Копируем весь проект (обязательно для multi-module)
COPY . .

# Кешируем зависимости
RUN mvn -B -pl ewm-main-service -am dependency:go-offline

# Собираем только main-service
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