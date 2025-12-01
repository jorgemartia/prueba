# ====================
# STAGE 1: BUILD
# ====================
FROM maven:3.9.6-eclipse-temurin-25 AS build

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
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copiar JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto (Render usa $PORT)
EXPOSE 8080

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
