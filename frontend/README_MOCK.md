# Modo Mock - Testes sem Backend

O frontend está configurado para funcionar **sem backend** usando dados mockados.

## Como funciona

1. **Modo Mock Ativado**: O arquivo `src/utils/mockHelper.js` tem `MOCK_MODE = true`
2. **Dados Mockados**: Todos os endpoints retornam dados de exemplo
3. **Login**: Aceita qualquer email/senha para testes

## Para testar o frontend

1. Instale as dependências:
```bash
cd frontend
npm install
```

2. Inicie o servidor de desenvolvimento:
```bash
npm run dev
```

3. Acesse: `http://localhost:5173`

4. Faça login com qualquer email/senha (ex: `admin@test.com` / `123456`)

## Para conectar com o backend real

1. Abra `src/utils/mockHelper.js`
2. Altere `MOCK_MODE = false`
3. Certifique-se de que o backend está rodando em `http://localhost:8080`

## Dados Mockados Disponíveis

- **Tutores**: 2 tutores de exemplo
- **Pets**: 2 pets de exemplo
- **Serviços**: 3 serviços de exemplo
- **Agendamentos**: 2 agendamentos de exemplo
- **Vacinas**: 1 vacina de exemplo
- **Prescrições**: 1 prescrição de exemplo
- **Dashboard**: Dados analíticos mockados

Todos os dados são salvos apenas em memória (não persistem após refresh).



