# Como Atualizar as Tabelas do Banco de Dados

## Situação Atual

O projeto está configurado com `spring.jpa.hibernate.ddl-auto=update`, que atualiza automaticamente o esquema do banco quando você modifica as entidades. **Isso funciona, mas tem limitações:**

### Limitações do `ddl-auto=update`:
- ❌ Não remove colunas que você deletou da entidade
- ❌ Não remove tabelas que você deletou
- ❌ Pode causar problemas em produção
- ❌ Não mantém histórico de mudanças
- ❌ Dificulta trabalho em equipe

## Opções Disponíveis

### 1. **Update (Atual - Desenvolvimento)**
```properties
spring.jpa.hibernate.ddl-auto=update
```
- ✅ Adiciona novas colunas/tabelas automaticamente
- ✅ Modifica tipos de dados quando possível
- ❌ Não remove colunas/tabelas deletadas
- ⚠️ Use apenas em desenvolvimento

### 2. **Validate (Produção)**
```properties
spring.jpa.hibernate.ddl-auto=validate
```
- ✅ Apenas valida se o esquema está correto
- ✅ Não faz alterações
- ✅ Seguro para produção
- ❌ Requer migrations manuais

### 3. **Flyway (Recomendado - Profissional)**
- ✅ Controle total sobre mudanças
- ✅ Histórico de todas as alterações
- ✅ Funciona bem em equipe
- ✅ Pode fazer rollback
- ✅ Seguro para produção

## Configuração com Flyway (Recomendado)

### Passo 1: Adicionar Dependência

A dependência já foi adicionada ao `pom.xml`.

### Passo 2: Criar Migrations

Crie arquivos SQL em `src/main/resources/db/migration/`:

**Exemplo: `V1__Create_users_table.sql`**
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    role VARCHAR(50),
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP
);
```

**Exemplo: `V2__Add_phone_to_tutors.sql`**
```sql
ALTER TABLE tutors ADD COLUMN phone VARCHAR(20);
```

### Passo 3: Configuração

No `application.properties`:
```properties
# Desabilitar ddl-auto quando usar Flyway
spring.jpa.hibernate.ddl-auto=validate

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

### Como Funciona

1. **Ao iniciar a aplicação**, Flyway verifica quais migrations já foram executadas
2. **Executa apenas as novas** migrations em ordem numérica
3. **Registra no banco** quais migrations foram aplicadas
4. **Não executa novamente** migrations já aplicadas

### Convenções de Nomenclatura

- `V{versão}__{descrição}.sql`
- Exemplos:
  - `V1__Create_users_table.sql`
  - `V2__Add_email_index.sql`
  - `V3__Create_pets_table.sql`

## Workflow Recomendado

### Para Desenvolvimento Rápido:
1. Modifique a entidade Java
2. Reinicie a aplicação (com `ddl-auto=update`)
3. O Hibernate atualiza automaticamente

### Para Produção/Equipe:
1. Modifique a entidade Java
2. Crie uma migration SQL correspondente
3. Teste a migration localmente
4. Commit da migration junto com a entidade
5. Em produção, a migration será aplicada automaticamente

## Comandos Úteis

### Verificar status das migrations:
```bash
# Flyway cria uma tabela flyway_schema_history no banco
# Você pode consultar diretamente no PostgreSQL:
psql -U admin -d zooracoes -c "SELECT * FROM flyway_schema_history;"
```

### Resetar banco (CUIDADO - apaga tudo):
```bash
# No PostgreSQL:
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
```

## Migração do Projeto Atual

Se você já tem um banco funcionando e quer migrar para Flyway:

1. **Gerar migration inicial** baseada no estado atual:
   ```bash
   # Conecte no banco e exporte o schema:
   pg_dump -U admin -d zooracoes --schema-only > src/main/resources/db/migration/V1__Initial_schema.sql
   ```

2. **Configurar Flyway** no `application.properties`

3. **Testar** em um banco limpo

## Dicas

- ⚠️ **Nunca** modifique uma migration já aplicada em produção
- ✅ Sempre crie uma nova migration para mudanças
- ✅ Teste migrations em ambiente de desenvolvimento primeiro
- ✅ Use `validate` em produção, nunca `update` ou `create`


