# ZooRacoes API - Documentação

## Base URL
```
http://localhost:8080
```

## Autenticação

A API usa autenticação JWT (JSON Web Token). Para obter um token, faça login no endpoint `/auth/login`.

### Como usar o token:
1. Faça login em `/auth/login` para obter o token
2. Inclua o token no header `Authorization` de todas as requisições:
   ```
   Authorization: Bearer <seu-token-aqui>
   ```

---

## Endpoints

### 🔐 Autenticação (`/auth`)

#### POST `/auth/login`
Autentica um usuário e retorna um token JWT.

**Request Body:**
```json
{
  "email": "usuario@example.com",
  "password": "senha123"
}
```

**Response 200:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### POST `/auth/register`
Registra um novo usuário. **Requer permissão ADMIN.**

**Request Body:**
```json
{
  "name": "Nome do Usuário",
  "email": "usuario@example.com",
  "password": "senha123",
  "role": "ADMIN" // ou "GESTOR" ou "FUNCIONARIO"
}
```

**Response 201:**
```
Usuário criado com sucesso.
```

#### GET `/auth/me`
Retorna os dados do usuário autenticado.

**Response 200:**
```json
{
  "id": 1,
  "name": "Nome do Usuário",
  "email": "usuario@example.com",
  "role": "ADMIN",
  "active": true
}
```

---

### 👥 Tutores (`/tutors`)

#### POST `/tutors`
Cria um novo tutor. **Requer permissão ADMIN ou GESTOR.**

**Request Body:**
```json
{
  "name": "João Silva",
  "email": "joao@example.com",
  "phone": "(11) 99999-9999",
  "address": "Rua Exemplo, 123"
}
```

#### GET `/tutors`
Lista todos os tutores (paginado).

**Query Parameters:**
- `page` (opcional): Número da página (padrão: 0)
- `size` (opcional): Tamanho da página (padrão: 10)
- `sort` (opcional): Campo para ordenação (padrão: "name")

**Exemplo:** `GET /tutors?page=0&size=10&sort=name`

#### GET `/tutors/{id}`
Busca um tutor por ID.

#### PUT `/tutors/{id}`
Atualiza um tutor. **Requer permissão ADMIN ou GESTOR.**

#### DELETE `/tutors/{id}`
Desativa um tutor (soft delete). **Requer permissão ADMIN ou GESTOR.**

---

### 🐾 Pets (`/pets`)

#### POST `/pets`
Cria um novo pet. **Requer permissão ADMIN ou GESTOR.**

**Request Body:**
```json
{
  "name": "Rex",
  "species": "Cachorro",
  "breed": "Labrador",
  "weight": 25.5,
  "birthDate": "2020-01-15",
  "tutorId": 1
}
```

#### GET `/pets`
Lista todos os pets (paginado).

**Query Parameters:**
- `species` (opcional): Filtrar por espécie
- `tutorId` (opcional): Filtrar por tutor
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página
- `sort` (opcional): Campo para ordenação

**Exemplo:** `GET /pets?species=Cachorro&tutorId=1&page=0&size=10`

#### GET `/pets/{id}`
Busca um pet por ID.

#### GET `/pets/tutor/{tutorId}`
Lista todos os pets de um tutor específico.

#### PUT `/pets/{id}`
Atualiza um pet. **Requer permissão ADMIN ou GESTOR.**

#### DELETE `/pets/{id}`
Desativa um pet (soft delete). **Requer permissão ADMIN ou GESTOR.**

---

### 📅 Agendamentos (`/schedules`)

#### POST `/schedules`
Cria um novo agendamento. **Requer permissão ADMIN ou GESTOR.**

**Request Body:**
```json
{
  "dateTime": "2024-12-20T10:00:00",
  "petId": 1,
  "serviceId": 1,
  "status": "AGENDADO",
  "notes": "Consulta de rotina"
}
```

**Status possíveis:** `AGENDADO`, `CONCLUIDO`, `CANCELADO`

#### GET `/schedules`
Lista todos os agendamentos (paginado).

**Query Parameters:**
- `date` (opcional): Filtrar por data (formato: YYYY-MM-DD)
- `serviceId` (opcional): Filtrar por serviço
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

#### GET `/schedules/{id}`
Busca um agendamento por ID.

#### PUT `/schedules/{id}`
Atualiza um agendamento. **Requer permissão ADMIN ou GESTOR.**

#### DELETE `/schedules/{id}`
Cancela um agendamento. **Requer permissão ADMIN ou GESTOR.**

---

### 🏥 Serviços (`/services`)

#### POST `/services`
Cria um novo serviço. **Requer permissão ADMIN.**

**Request Body:**
```json
{
  "name": "Consulta Veterinária",
  "description": "Consulta de rotina",
  "price": 150.00,
  "duration": 30
}
```

#### GET `/services`
Lista todos os serviços.

#### GET `/services/{id}`
Busca um serviço por ID.

#### PUT `/services/{id}`
Atualiza um serviço. **Requer permissão ADMIN.**

#### DELETE `/services/{id}`
Desativa um serviço (soft delete). **Requer permissão ADMIN.**

---

### 💉 Vacinas (`/vaccines`)

#### POST `/vaccines`
Cria um registro de vacinação. **Requer permissão ADMIN, GESTOR ou FUNCIONARIO.**

**Request Body:**
```json
{
  "petId": 1,
  "name": "V8",
  "applicationDate": "2024-01-15",
  "nextDoseDate": "2024-02-15",
  "veterinarian": "Dr. Silva",
  "batch": "LOT123"
}
```

#### GET `/vaccines`
Lista todos os registros de vacinação.

#### GET `/vaccines/{id}`
Busca um registro de vacinação por ID.

#### GET `/vaccines/pet/{petId}`
Lista todas as vacinas de um pet específico.

#### PUT `/vaccines/{id}`
Atualiza um registro de vacinação. **Requer permissão ADMIN, GESTOR ou FUNCIONARIO.**

#### DELETE `/vaccines/{id}`
Remove um registro de vacinação. **Requer permissão ADMIN, GESTOR ou FUNCIONARIO.**

---

### 💊 Prescrições (`/prescriptions`)

#### POST `/prescriptions`
Cria uma prescrição médica. **Requer permissão ADMIN, GESTOR ou FUNCIONARIO.**

**Request Body:**
```json
{
  "petId": 1,
  "medication": "Antibiótico",
  "dosage": "1 comprimido a cada 8 horas",
  "startDate": "2024-01-15",
  "endDate": "2024-01-22",
  "veterinarian": "Dr. Silva",
  "notes": "Tomar com alimento"
}
```

#### GET `/prescriptions`
Lista todas as prescrições.

#### GET `/prescriptions/{id}`
Busca uma prescrição por ID.

#### GET `/prescriptions/pet/{petId}`
Lista todas as prescrições de um pet específico.

#### PUT `/prescriptions/{id}`
Atualiza uma prescrição. **Requer permissão ADMIN, GESTOR ou FUNCIONARIO.**

#### DELETE `/prescriptions/{id}`
Remove uma prescrição. **Requer permissão ADMIN, GESTOR ou FUNCIONARIO.**

---

### 📊 Analytics (`/analytics`)

#### GET `/analytics/dashboard`
Retorna dados analíticos para o dashboard.

**Response 200:**
```json
{
  "totalTutors": 25,
  "totalPets": 15,
  "totalServices": 10,
  "totalSchedules": 35,
  "schedulesToday": 5,
  "vaccinesLate": 2,
  "vaccinesNext7Days": 3,
  "petsBySpecies": {
    "Cachorro": 10,
    "Gato": 5
  },
  "schedulesByService": {
    "Consulta": 20,
    "Vacinação": 15
  }
}
```

---

### ⚙️ Sistema (`/`)

#### GET `/health`
Verifica se a API está funcionando.

**Response 200:**
```
OK - ZoorAções API funcionando!
```

#### GET `/version`
Retorna a versão da API.

**Response 200:**
```
1.0.0
```

---

## Códigos de Status HTTP

- `200 OK` - Requisição bem-sucedida
- `201 Created` - Recurso criado com sucesso
- `400 Bad Request` - Dados inválidos
- `401 Unauthorized` - Não autenticado
- `403 Forbidden` - Acesso negado (sem permissão)
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro interno do servidor

---

## Permissões (Roles)

- **ADMIN**: Acesso total a todos os endpoints
- **GESTOR**: Pode gerenciar tutores, pets, agendamentos, vacinas e prescrições
- **FUNCIONARIO**: Pode gerenciar vacinas e prescrições

---

## Exemplos de Uso

### Exemplo 1: Login e obter token
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "senha123"
  }'
```

### Exemplo 2: Listar tutores (com autenticação)
```bash
curl -X GET http://localhost:8080/tutors \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### Exemplo 3: Criar um pet
```bash
curl -X POST http://localhost:8080/pets \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rex",
    "species": "Cachorro",
    "breed": "Labrador",
    "weight": 25.5,
    "birthDate": "2020-01-15",
    "tutorId": 1
  }'
```

---

## Swagger UI

Se o Swagger estiver funcionando, você pode acessar a documentação interativa em:
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/swagger-ui/index.html`

A documentação OpenAPI está disponível em:
- `http://localhost:8080/v3/api-docs`

