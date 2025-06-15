# Nombre del contenedor docker-compose
COMPOSE=docker-compose

# Target por defecto: levantar todo y ejecutar tests
all: up test

# Build de imágenes
build:
	$(COMPOSE) build

# Levanta solo la app en segundo plano
up:
	$(COMPOSE) up -d app

# Ejecuta los tests E2E (esperando a que la app esté arriba)
test:
	$(COMPOSE) run --rm e2e

# Apaga todos los contenedores
down:
	$(COMPOSE) down

# Limpia imágenes y volúmenes
clean:
	$(COMPOSE) down --volumes --rmi all

# Verifica logs en tiempo real de la app
logs:
	$(COMPOSE) logs -f app

# Reconstruye todo y ejecuta en foreground
rebuild:
	$(COMPOSE) up --build
