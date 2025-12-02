# Arquitetura do Frontend - ZooRacoes

## Estrutura em Camadas

```
frontend/
├── src/
│   ├── api/                    # Camada de API (Comunicação HTTP)
│   │   ├── client.js          # Cliente Axios configurado
│   │   └── endpoints/         # Endpoints organizados por domínio
│   │       ├── auth.js
│   │       ├── tutors.js
│   │       ├── pets.js
│   │       ├── schedules.js
│   │       ├── services.js
│   │       ├── vaccines.js
│   │       ├── prescriptions.js
│   │       └── analytics.js
│   │
│   ├── stores/                 # Camada de Estado (Gerenciamento de Estado)
│   │   └── auth.js            # Store de autenticação
│   │
│   ├── composables/            # Camada de Lógica de Negócio (Composables Vue)
│   │   ├── useAuth.js
│   │   ├── useTutors.js
│   │   ├── usePets.js
│   │   ├── useSchedules.js
│   │   ├── useServices.js
│   │   ├── useVaccines.js
│   │   ├── usePrescriptions.js
│   │   └── useAnalytics.js
│   │
│   ├── components/             # Camada de Apresentação (Componentes Reutilizáveis)
│   │   ├── layout/            # Componentes de Layout
│   │   │   ├── AppLayout.vue
│   │   │   ├── Sidebar.vue
│   │   │   └── Header.vue
│   │   ├── common/            # Componentes Comuns
│   │   │   ├── Button.vue
│   │   │   ├── Input.vue
│   │   │   ├── Card.vue
│   │   │   ├── Table.vue
│   │   │   ├── Modal.vue
│   │   │   └── Loading.vue
│   │   └── forms/             # Componentes de Formulário
│   │       ├── TutorForm.vue
│   │       ├── PetForm.vue
│   │       ├── ScheduleForm.vue
│   │       ├── ServiceForm.vue
│   │       ├── VaccineForm.vue
│   │       └── PrescriptionForm.vue
│   │
│   ├── views/                  # Camada de Apresentação (Páginas/Telas)
│   │   ├── auth/
│   │   │   └── Login.vue
│   │   ├── dashboard/
│   │   │   └── Dashboard.vue
│   │   ├── tutors/
│   │   │   ├── TutorList.vue
│   │   │   └── TutorForm.vue
│   │   ├── pets/
│   │   │   ├── PetList.vue
│   │   │   ├── PetForm.vue
│   │   │   └── PetDetail.vue
│   │   ├── schedules/
│   │   │   ├── ScheduleList.vue
│   │   │   └── ScheduleForm.vue
│   │   ├── services/
│   │   │   ├── ServiceList.vue
│   │   │   └── ServiceForm.vue
│   │   ├── vaccines/
│   │   │   └── VaccineForm.vue
│   │   └── prescriptions/
│   │       └── PrescriptionForm.vue
│   │
│   ├── router/                 # Camada de Roteamento
│   │   ├── index.js
│   │   └── guards.js          # Guards de autenticação
│   │
│   ├── utils/                  # Camada de Utilitários
│   │   ├── formatters.js      # Formatação de dados
│   │   ├── validators.js      # Validações
│   │   └── constants.js       # Constantes
│   │
│   ├── styles/                 # Camada de Estilos
│   │   ├── main.css           # Estilos globais
│   │   ├── variables.css      # Variáveis CSS
│   │   ├── layout.css         # Estilos de layout
│   │   └── components.css     # Estilos de componentes
│   │
│   ├── App.vue                 # Componente raiz
│   └── main.js                 # Entry point
│
└── package.json
```

## Descrição das Camadas

### 1. **Camada de API** (`api/`)
Responsável por toda comunicação HTTP com o backend.
- `client.js`: Configuração do Axios (interceptors, baseURL, etc.)
- `endpoints/`: Funções que fazem chamadas HTTP específicas

### 2. **Camada de Estado** (`stores/`)
Gerenciamento de estado global da aplicação.
- Stores reativas usando Composition API

### 3. **Camada de Lógica de Negócio** (`composables/`)
Lógica de negócio reutilizável usando Composition API.
- Composables que combinam API + Estado + Lógica

### 4. **Camada de Apresentação** (`components/` e `views/`)
Componentes visuais e páginas.
- `components/`: Componentes reutilizáveis
- `views/`: Páginas completas

### 5. **Camada de Roteamento** (`router/`)
Configuração de rotas e navegação.
- Definição de rotas
- Guards de autenticação

### 6. **Camada de Utilitários** (`utils/`)
Funções auxiliares e utilitários.
- Formatação, validação, constantes

### 7. **Camada de Estilos** (`styles/`)
Estilos CSS organizados.
- CSS simples, sem frameworks

## Fluxo de Dados

```
View → Composable → API → Backend
  ↓       ↓         ↓
State   Logic    HTTP
```

## Princípios

1. **Separação de Responsabilidades**: Cada camada tem uma responsabilidade específica
2. **Reutilização**: Composables e componentes são reutilizáveis
3. **Manutenibilidade**: Código organizado e fácil de manter
4. **Testabilidade**: Camadas isoladas facilitam testes



