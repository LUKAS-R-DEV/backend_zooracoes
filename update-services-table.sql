-- Script SQL para adicionar colunas price e duration à tabela services
-- Execute este script no banco de dados ANTES de reiniciar a aplicação
-- Isso evita erros quando o Hibernate tentar atualizar a tabela

-- Passo 1: Adicionar colunas como NULLABLE primeiro
ALTER TABLE services 
ADD COLUMN IF NOT EXISTS price DOUBLE PRECISION;

ALTER TABLE services 
ADD COLUMN IF NOT EXISTS duration INTEGER;

-- Passo 2: Atualizar registros existentes com valores padrão
-- (ajuste os valores conforme necessário)
UPDATE services 
SET price = 50.0 
WHERE price IS NULL;

UPDATE services 
SET duration = 30 
WHERE duration IS NULL;

-- Passo 3: Tornar as colunas NOT NULL
ALTER TABLE services 
ALTER COLUMN price SET NOT NULL,
ALTER COLUMN duration SET NOT NULL;

-- Verificar resultado
SELECT id, name, price, duration FROM services;


