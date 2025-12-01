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

# ====================
# PASO CLAVE: DEFINIR LA URI DE MONGODB COMO ENV
# ====================
ENV MONGODB_URI="mongodb+srv://jarmandomartinez_db_user:jorgebased123@biblioteca.socaz6k.mongodb.net/biblioteca?retryWrites=true&w=majority"

# Exponer puerto (Render usa $PORT)
EXPOSE 8080

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
