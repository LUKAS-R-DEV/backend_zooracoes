# Database Migrations

Esta pasta contém as migrations SQL do banco de dados usando Flyway.

## Como Usar

### Para Desenvolvimento Rápido (Atual)
O projeto está configurado com `spring.jpa.hibernate.ddl-auto=update`, então:
1. Modifique a entidade Java
2. Reinicie a aplicação
3. O Hibernate atualiza automaticamente

### Para Usar Flyway (Recomendado para Produção)

1. **Habilite Flyway** no `application.properties`:
   ```properties
   spring.flyway.enabled=true
   spring.jpa.hibernate.ddl-auto=validate
   ```

2. **Crie uma migration** com o padrão:
   ```
   V{versão}__{descrição}.sql
   ```
   
   Exemplos:
   - `V1__Create_users_table.sql`
   - `V2__Add_phone_to_tutors.sql`
   - `V3__Create_index_on_email.sql`

3. **Ao iniciar a aplicação**, Flyway executará automaticamente as migrations pendentes.

## Convenções

- **Versão**: Número sequencial (1, 2, 3, ...)
- **Separador**: Dois underscores `__`
- **Descrição**: Descrição curta em snake_case
- **Extensão**: `.sql`

## Exemplo de Migration

```sql
-- V2__Add_phone_to_tutors.sql
ALTER TABLE tutors 
ADD COLUMN phone VARCHAR(20);

-- Criar índice se necessário
CREATE INDEX idx_tutors_phone ON tutors(phone);
```

## Importante

- ⚠️ **Nunca** modifique uma migration já aplicada em produção
- ✅ Sempre crie uma nova migration para mudanças
- ✅ Teste migrations localmente antes de commitar
- ✅ Use transações quando possível (Flyway faz isso automaticamente)


