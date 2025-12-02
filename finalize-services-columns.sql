-- Script para tornar as colunas price e duration NOT NULL
-- Execute este script DEPOIS que a aplicação iniciar com sucesso
-- e você atualizar os registros existentes

-- Atualizar registros existentes com valores padrão (se ainda houver nulls)
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


