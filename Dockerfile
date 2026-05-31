# ── Etapa 1: compilar ────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Descarga dependencias primero (capa cacheada si pom.xml no cambia)
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

# ── Etapa 2: imagen final liviana ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/quarkus-app/lib/       ./lib/
COPY --from=build /app/target/quarkus-app/*.jar       ./
COPY --from=build /app/target/quarkus-app/app/        ./app/
COPY --from=build /app/target/quarkus-app/quarkus/    ./quarkus/

EXPOSE 8080
CMD ["java", "-jar", "quarkus-run.jar"]
