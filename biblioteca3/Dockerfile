# ====================
# STAGE 1: BUILD
# ====================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar sin tests
RUN mvn clean package -DskipTests

# ====================
# STAGE 2: RUNTIME
# ====================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Ejecutar la app (la URI se pasará en runtime)
ENTRYPOINT ["java", "-jar", "app.jar"]
