# Multi-stage build para otimizar o tamanho da imagem final

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar arquivos de configuração do Maven primeiro (para cache de dependências)
COPY pom.xml .

# Baixar dependências (cache se pom.xml não mudar)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Instalar curl para health check
RUN apk add --no-cache curl

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar o JAR do stage de build
COPY --from=build /app/target/*.jar app.jar

# Mudar para usuário não-root
USER spring:spring

# Expor porta (Render usa variável de ambiente PORT)
EXPOSE 8080

# Health check (Render também verifica automaticamente)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/health || exit 1

# Comando para executar a aplicação
# Render define a variável PORT, então usamos ela
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=${PORT:-8080} app.jar"]
