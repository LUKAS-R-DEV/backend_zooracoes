#!/bin/bash

# Script para atualizar a tabela services com as novas colunas
# Este script executa a SQL no banco PostgreSQL via Docker

echo "🔄 Atualizando tabela services com colunas price e duration..."

# Verificar se o container está rodando
if ! docker ps | grep -q zooracoes-postgres; then
    echo "❌ Container zooracoes-postgres não está rodando!"
    echo "   Execute: docker-compose up -d"
    exit 1
fi

# Executar a SQL
docker exec -i zooracoes-postgres psql -U admin -d zooracoes << EOF
-- Adicionar colunas como NULLABLE primeiro
ALTER TABLE services 
ADD COLUMN IF NOT EXISTS price DOUBLE PRECISION;

ALTER TABLE services 
ADD COLUMN IF NOT EXISTS duration INTEGER;

-- Atualizar registros existentes com valores padrão
UPDATE services 
SET price = 50.0 
WHERE price IS NULL;

UPDATE services 
SET duration = 30 
WHERE duration IS NULL;

-- Tornar as colunas NOT NULL
ALTER TABLE services 
ALTER COLUMN price SET NOT NULL,
ALTER COLUMN duration SET NOT NULL;

-- Verificar resultado
SELECT id, name, price, duration FROM services;
EOF

if [ $? -eq 0 ]; then
    echo "✅ Tabela services atualizada com sucesso!"
    echo "   Agora você pode reiniciar a aplicação Spring Boot."
else
    echo "❌ Erro ao atualizar a tabela. Verifique os logs acima."
    exit 1
fi


