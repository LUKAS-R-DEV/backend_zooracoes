# ZooRacoes 🐾🏥

![GitHub repo size](https://img.shields.io/github/repo-size/Audri-Rian/ZooRacoes?style=for-the-badge)
![GitHub language count](https://img.shields.io/github/languages/count/Audri-Rian/ZooRacoes?style=for-the-badge)
![GitHub forks](https://img.shields.io/github/forks/Audri-Rian/ZooRacoes?style=for-the-badge)

## 📋 Sobre o Projeto

Plataforma unificada de gestão para pet shops e clínicas veterinárias. Centraliza o controle de tutores, pets, agendamentos, vacinas, prescrições e comunicação comercial.

**Principais funcionalidades:**
- 🐕 Gestão de tutores e pets
- 📅 Agendamentos de serviços e consultas
- 💉 Controle de vacinas com lembretes automáticos
- 📋 Prescrições digitais e calculadora de doses
- 📧 Comunicação automatizada (e-mail/WhatsApp)
- 📊 Dashboard de feedback e fidelização

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- Java 17+
- Maven
- Node.js 18+ e npm
- PostgreSQL (opcional para desenvolvimento inicial)

### Backend (Spring Boot)

```bash
cd demo
./mvnw spring-boot:run
# ou no Windows:
mvnw.cmd spring-boot:run
```

Backend rodando em `http://localhost:8080`

### Frontend (Vue.js)

```bash
cd frontend
npm install
npm run dev
```

Frontend rodando em `http://localhost:5173`

### ✅ O que já está configurado:

- ✅ CORS configurado no backend
- ✅ Axios configurado com interceptors
- ✅ Proxy configurado no Vite
- ✅ Health check endpoint (`/api/health`)
- ✅ Estrutura de rotas Vue Router
- ✅ Componentes básicos com UI moderna

---

## 💻 Tecnologias

### Backend
- Java 17
- Spring Boot 3.5.7
- PostgreSQL
- Redis

### Frontend
- Vue.js 3
- Vite
- Tailwind CSS
- Axios
- Vue Router

---

## 📚 Documentação

- **[📋 Documento MVP](docs/DocumentoMVP.md)** - Documentação completa do projeto
- **[🚀 Guia de Início](docs/IniciarProjeto.md)** - Configuração do ambiente
- **[🎨 Frontend README](frontend/README.md)** - Documentação do frontend

---

## 🧭 Navegação Rápida

### Documentos Principais
- 📖 [README Principal](README.md) - Visão geral do projeto
- 📋 [Documento MVP](docs/DocumentoMVP.md) - Especificações e requisitos
- 🚀 [Guia de Início](docs/IniciarProjeto.md) - Configuração do ambiente

### Por Tipo de Informação
- 🏗️ **Arquitetura**: [Documento MVP - Arquitetura](docs/DocumentoMVP.md#12-arquitetura-visão-geral)
- 📋 **Requisitos**: [Documento MVP - Requisitos](docs/DocumentoMVP.md#7-requisitos-funcionais-rf)
- 🚀 **Instalação**: [Guia de Início](docs/IniciarProjeto.md)
- 💻 **Frontend**: [Frontend README](frontend/README.md)
- 🔧 **Tecnologias**: [Documento MVP - Tecnologias](docs/DocumentoMVP.md#21-tecnologias-e-infraestrutura)

---

## 👥 Autores

- **Audri Rian**
- **Lucas Bezerra**
- **Vinicius Souza**
- **Samuel Lima**
- **Marina Grazielly**
- **Ranyeli**

---

## 📄 Licença

Este projeto está licenciado sob a Licença Apache 2.0 - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

**Feito com ❤️ para cuidar melhor dos nossos pets 🐾**
