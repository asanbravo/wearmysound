# Etapa 1: build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos todo el código al contenedor
COPY . .

# Construimos el proyecto y generamos el JAR
RUN mvn -pl boot -am clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos solo el jar construido desde la etapa anterior
COPY --from=build /app/boot/target/boot-0.0.1-SNAPSHOT.jar app.jar

# Puerto por defecto que Spring Boot usa
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]