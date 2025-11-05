# ZooRacoes Frontend

Frontend Vue.js 3 para o sistema ZooRacoes.

## 🚀 Como começar

### Pré-requisitos
- Node.js 18+ instalado
- npm ou yarn

### Instalação

```bash
cd frontend
npm install
```

### Desenvolvimento

```bash
npm run dev
```

O frontend estará disponível em `http://localhost:5173`

### Build para produção

```bash
npm run build
```

## 📁 Estrutura do Projeto

```
frontend/
├── src/
│   ├── views/          # Páginas/rotas
│   ├── services/       # Serviços de API
│   ├── router/         # Configuração de rotas
│   ├── App.vue         # Componente principal
│   └── main.js         # Entry point
├── index.html
├── vite.config.js      # Configuração do Vite
├── tailwind.config.js  # Configuração do Tailwind CSS
├── postcss.config.js   # Configuração do PostCSS
└── package.json
```

## 🎨 Tailwind CSS

O projeto utiliza Tailwind CSS para estilização. As classes utilitárias estão disponíveis em todos os componentes.

### Classes customizadas disponíveis:

- `.btn-primary` - Botão primário
- `.btn-secondary` - Botão secundário  
- `.card` - Card com sombra e padding

### Cores personalizadas:

- `primary-50` até `primary-900` - Paleta de cores primária do projeto

## 🔌 Integração com Backend

O frontend está configurado para se comunicar com o backend Spring Boot na porta 8080.

A configuração de CORS já está configurada no backend para permitir requisições do frontend.

## 📝 Scripts Disponíveis

- `npm run dev` - Inicia servidor de desenvolvimento
- `npm run build` - Cria build de produção
- `npm run preview` - Preview do build de produção

## 🛠️ Tecnologias

- **Vue.js 3** - Framework JavaScript
- **Vite** - Build tool
- **Tailwind CSS** - Framework CSS utilitário
- **Vue Router** - Roteamento
- **Axios** - Cliente HTTP

