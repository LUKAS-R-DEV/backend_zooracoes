# Como Iniciar o Banco de Dados PostgreSQL

## Opção 1: Usando Docker Compose (Recomendado)

### 1. Verificar se o Docker está rodando
```bash
docker ps
```

Se der erro de permissão, adicione seu usuário ao grupo docker:
```bash
sudo usermod -aG docker $USER
```
Depois, faça logout e login novamente.

### 2. Iniciar o banco de dados
```bash
cd /home/lucas/Área\ de\ Trabalho/zooracoes-api
docker-compose up -d
```

### 3. Verificar se está rodando
```bash
docker-compose ps
```

Você deve ver o container `zooracoes-postgres` com status "Up".

### 4. Parar o banco (quando necessário)
```bash
docker-compose down
```

---

## Opção 2: PostgreSQL Local

Se você tem PostgreSQL instalado localmente:

### 1. Criar o banco de dados
```bash
sudo -u postgres psql
```

No psql:
```sql
CREATE DATABASE zooracoes;
CREATE USER admin WITH PASSWORD 'admin123';
GRANT ALL PRIVILEGES ON DATABASE zooracoes TO admin;
\q
```

### 2. Verificar conexão
```bash
psql -h localhost -U admin -d zooracoes
```

---

## Verificar se o banco está acessível

```bash
# Testar conexão
psql -h localhost -p 5432 -U admin -d zooracoes
```

Ou usando telnet:
```bash
telnet localhost 5432
```

---

## Configuração Atual

- **Host:** localhost
- **Porta:** 5432
- **Database:** zooracoes
- **Usuário:** admin
- **Senha:** admin123

Essas configurações estão em `src/main/resources/application.properties`.

---

## Solução Rápida

Se você não conseguir usar Docker, pode instalar PostgreSQL localmente:

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

Depois, siga os passos da Opção 2 acima.

