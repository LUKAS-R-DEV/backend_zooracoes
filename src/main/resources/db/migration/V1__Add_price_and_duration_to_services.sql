-- Migration: Adicionar colunas price e duration à tabela services
-- Esta migration é segura mesmo se já existirem dados na tabela

-- Passo 1: Adicionar colunas como NULLABLE primeiro
ALTER TABLE services 
ADD COLUMN IF NOT EXISTS price DOUBLE PRECISION;

ALTER TABLE services 
ADD COLUMN IF NOT EXISTS duration INTEGER;

-- Passo 2: Atualizar registros existentes com valores padrão razoáveis
-- (ajuste os valores conforme necessário para seu negócio)
UPDATE services 
SET price = 50.0 
WHERE price IS NULL;

UPDATE services 
SET duration = 30 
WHERE duration IS NULL;

-- Passo 3: Agora tornar as colunas NOT NULL (após preencher valores)
ALTER TABLE services 
ALTER COLUMN price SET NOT NULL,
ALTER COLUMN duration SET NOT NULL;

