# 🐾 ZooRacoes - Sistema de Gestão de Rações

Sistema completo com backend Spring Boot e frontend Vue.js 3.

## 📋 Estrutura do Projeto

```
ZooRacoes/
├── demo/                 # Backend Spring Boot
│   └── src/
│       └── main/
│           ├── java/     # Código Java
│           └── resources/ # Configurações
└── frontend/             # Frontend Vue.js 3
    └── src/
        ├── views/        # Páginas
        ├── services/     # Serviços de API
        └── router/       # Rotas
```

## 🚀 Como Iniciar

### Backend (Spring Boot)

```bash
cd demo
./mvnw spring-boot:run
# ou no Windows:
mvnw.cmd spring-boot:run
```

O backend estará rodando em `http://localhost:8080`

### Frontend (Vue.js)

```bash
cd frontend
npm install
npm run dev
```

O frontend estará rodando em `http://localhost:5173`

## ✅ O que já está configurado:

- ✅ CORS configurado no backend para aceitar requisições do frontend
- ✅ Axios configurado com interceptors para autenticação
- ✅ Proxy configurado no Vite para desenvolvimento
- ✅ Health check endpoint (`/api/health`)
- ✅ Estrutura de rotas Vue Router
- ✅ Componentes básicos com UI moderna

## 📝 Próximos Passos

1. **Criar suas entidades no backend** (Controllers, Services, Repositories)
2. **Criar componentes Vue** para suas funcionalidades
3. **Implementar autenticação** se necessário
4. **Adicionar validações** nos formulários

## 🔧 Tecnologias

- **Backend**: Spring Boot 3.5.7, Java 17
- **Frontend**: Vue.js 3, Vite, Axios, Vue Router
- **Comunicação**: REST API

## 📚 Documentação

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Vue.js Docs](https://vuejs.org/)
- [Vite Docs](https://vitejs.dev/)

