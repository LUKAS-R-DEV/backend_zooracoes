# 🚀 Guia de Deploy no Render

Este guia explica como fazer o deploy do backend ZoorAções no Render.

## 📋 Pré-requisitos

1. Conta no [Render](https://render.com)
2. Repositório GitHub com o código
3. Banco de dados PostgreSQL (pode criar no Render)

## 🔧 Configuração no Render

### 1. Criar Web Service

1. Acesse o [Dashboard do Render](https://dashboard.render.com)
2. Clique em **"New +"** → **"Web Service"**
3. Conecte seu repositório GitHub
4. Selecione o repositório `backend_zooracoes`

### 2. Configurações do Build

- **Name**: `zooracoes-api` (ou o nome que preferir)
- **Region**: Escolha a região mais próxima (ex: `Oregon (US West)`)
- **Branch**: `main`
- **Root Directory**: Deixe vazio (raiz do projeto)
- **Runtime**: `Docker`
- **Dockerfile Path**: `Dockerfile` (deve estar na raiz)
- **Docker Context**: `.` (ponto)

### 3. Variáveis de Ambiente

Adicione as seguintes variáveis de ambiente no Render:

```env
# Database (use o banco PostgreSQL do Render)
SPRING_DATASOURCE_URL=jdbc:postgresql://[HOST]:[PORT]/[DATABASE]
SPRING_DATASOURCE_USERNAME=[USERNAME]
SPRING_DATASOURCE_PASSWORD=[PASSWORD]

# JWT Secret (gere uma chave segura)
API_JWT_SECRET=sua_chave_secreta_jwt_aqui_minimo_256_bits

# Seed (opcional - desabilite em produção)
ZOORACOES_SEED_ENABLED=false

# JPA (recomendado para produção)
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

# Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_ZOORACOES_API=INFO
```

**Como obter as credenciais do PostgreSQL no Render:**

1. Crie um **PostgreSQL Database** no Render
2. Na página do banco, copie:
   - **Internal Database URL** (para uso interno)
   - **External Database URL** (para uso externo)
3. Use o formato: `jdbc:postgresql://[host]:[port]/[database]`

### 4. Health Check

O Render detecta automaticamente o health check através do Dockerfile. O endpoint `/health` já está configurado e público.

### 5. Porta

O Render define automaticamente a variável `PORT`. O Dockerfile já está configurado para usar essa variável.

## 🗄️ Criar Banco de Dados PostgreSQL

1. No Dashboard do Render, clique em **"New +"** → **"PostgreSQL"**
2. Configure:
   - **Name**: `zooracoes-db`
   - **Database**: `zooracoes`
   - **User**: Será gerado automaticamente
   - **Region**: Mesma região do Web Service
3. Após criar, copie as credenciais e adicione nas variáveis de ambiente do Web Service

## 🔐 Segurança

### JWT Secret

Gere uma chave secreta segura para JWT:

```bash
# No terminal
openssl rand -base64 32
```

Ou use um gerador online: https://generate-secret.vercel.app/32

### CORS

Atualize o `SecurityConfig.java` para permitir o domínio do frontend:

```java
corsConfig.setAllowedOrigins(java.util.List.of(
    "http://localhost:5173",  // Desenvolvimento
    "https://seu-frontend.render.com"  // Produção
));
```

## 📝 Arquivos Necessários

Certifique-se de que estes arquivos estão no repositório:

- ✅ `Dockerfile` (na raiz)
- ✅ `.dockerignore` (na raiz)
- ✅ `pom.xml`
- ✅ `src/` (código fonte)

## 🚀 Deploy

1. Após configurar tudo, clique em **"Create Web Service"**
2. O Render irá:
   - Fazer build da imagem Docker
   - Executar o container
   - Verificar o health check
3. Aguarde o deploy completar (pode levar alguns minutos)

## ✅ Verificação

Após o deploy, teste:

```bash
# Health check
curl https://seu-app.onrender.com/health

# Deve retornar: "OK - ZoorAções API funcionando!"
```

## 🔄 Atualizações

O Render faz deploy automático quando você faz push para a branch `main`.

Para deploy manual:
1. Vá para o Web Service
2. Clique em **"Manual Deploy"** → **"Deploy latest commit"**

## 📊 Monitoramento

- **Logs**: Acesse a aba "Logs" no dashboard do Render
- **Metrics**: Visualize CPU, memória e requisições
- **Health**: Status do health check

## 🐛 Troubleshooting

### Erro de conexão com banco
- Verifique se as variáveis de ambiente estão corretas
- Use o **Internal Database URL** do Render
- Verifique se o banco está na mesma região

### Build falha
- Verifique os logs de build
- Certifique-se que o Dockerfile está correto
- Verifique se todas as dependências estão no `pom.xml`

### Aplicação não inicia
- Verifique os logs de runtime
- Confirme que a porta está configurada corretamente
- Verifique se o JAR foi gerado corretamente

### Health check falha
- Verifique se o endpoint `/health` está acessível
- Confirme que a aplicação iniciou completamente
- Verifique os logs para erros de inicialização

## 📚 Recursos

- [Documentação Render](https://render.com/docs)
- [Docker no Render](https://render.com/docs/docker)
- [PostgreSQL no Render](https://render.com/docs/databases)

## 💡 Dicas

1. **Free Tier**: O Render oferece tier gratuito, mas o serviço "hiberna" após 15 minutos de inatividade
2. **Upgrade**: Para produção, considere o plano pago para evitar hibernação
3. **Backup**: Configure backups automáticos do banco de dados
4. **Domínio**: Você pode adicionar um domínio customizado no Render

