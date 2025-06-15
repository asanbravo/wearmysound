# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Crea el directorio de trabajo
WORKDIR /e2e

# Copiamos solo la carpeta del proyecto de pruebas
COPY config_test/e2e/definitions/cucumber /e2e

# Pre-descarga dependencias (offline build optimizado)
RUN mvn -f pom.xml dependency:go-offline

# Compila y empaqueta el proyecto (opcional, si tienes test que necesitan compilación previa)
RUN mvn -f pom.xml clean package -DskipTests

# Etapa 2: Imagen final para ejecutar los tests
FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /e2e

# Copiamos el proyecto ya resuelto por Maven
COPY --from=builder /e2e /e2e

# Le damos permisos al script si lo usas
RUN chmod +x run-e2e.sh || true

# Comando por defecto (sobrescribible por docker-compose)
ENTRYPOINT ["bash", "-c", "./run-e2e.sh"]
