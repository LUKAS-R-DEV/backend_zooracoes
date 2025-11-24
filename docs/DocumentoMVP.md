# Documento MVP — Plataforma de Pet Shop

**Autor:** Audri Rian

## 🧭 Navegação

- 📖 [README Principal](../README.md) - Visão geral do projeto
- 🚀 [Guia de Início](IniciarProjeto.md) - Configuração do ambiente
- 💻 [Frontend README](../frontend/README.md) - Documentação do frontend

---

## Histórico de Versões

| Data | Versão | Descrição | Autor |
|------|--------|-----------|-------|
| 04/11/2025 | 1.0 | MVP inicial | Lucas Rafael |
| 04/11/2025 | 1.1 | Expansão clínica veterinária e automação inteligente | Audri Rian |

---

## RESUMO EXECUTIVO / VISÃO SINTÉTICA

### Problema / Dor que o MVP resolve:
Pet shops e clínicas veterinárias enfrentam dificuldades em manter o controle de clientes, pets, prontuários, vacinas e agendamentos. Isso gera falhas de comunicação, atrasos em tratamentos e perda de fidelização.

### Solução Proposta:
Uma plataforma unificada de gestão petshop e clínica veterinária, que centraliza o controle de tutores, pets, agendamentos, vacinas, prescrições e comunicação comercial.

Inclui automação de lembretes, campanhas e um assistente clínico inteligente para apoio em diagnósticos, medicamentos e dosagens.

### Público-Alvo:
- Pequenos e médios petshops
- Clínicas veterinárias completas
- Profissionais autônomos (veterinários, tosadores, banhistas)

### Principais Funcionalidades Incluídas:
- Gestão de tutores, pets e histórico clínico
- Agendamentos de serviços e consultas
- Lembretes automáticos (vacinas, retornos e medicamentos)
- Prescrições digitais e calculadora de doses
- Comunicação e marketing integrados
- Dashboard de feedback e fidelização

### Critérios de Sucesso:
- Reduzir 30% das faltas de agendamento
- Aumentar 25% a retenção de clientes em 3 meses
- Reduzir 40% o tempo médio de atendimento
- Obter nota média ≥ 4,5 nas avaliações de serviço

---

## 1. VISÃO E OBJETIVO

**O que:**
Um sistema web inteligente de gestão petshop e clínica veterinária, com automação de processos administrativos e suporte clínico integrado.

**Por que:**
Eliminar controles manuais, reduzir erros em vacinas e prescrições, melhorar o atendimento e fidelizar clientes.

**Benefício Principal:**
Gestão integrada e inteligente — combinando operação comercial (banho/tosa/agendamento) com prontuário clínico e automação de comunicação.

---

## 2. ESCOPO DO MVP

### Incluídas (Versão MVP):
- Cadastro de tutores e pets
- Controle de serviços e agendamentos
- Registro de vacinas com lembretes automáticos
- Prescrições digitais básicas (texto livre)
- Comunicação por e-mail automatizada
- Dashboard de feedbacks e retornos clínicos

### Fora do Escopo (Versões Futuras):
- App mobile para tutores
- Sistema de pontos e recompensas
- Ranking de serviços mais populares
- Integração com marketplace de produtos
- Integração completa com bulário digital (fase 2)

---

## 3. STAKEHOLDERS

### Internos:
- Administrador do sistema
- Equipe de desenvolvimento
- Suporte e atendimento técnico

### Externos:
- Tutores (clientes finais)
- Veterinários e auxiliares clínicos
- Parceiros (APIs de WhatsApp, e-mail, bulário)

---

## 4. PÚBLICO-ALVO

Empresas e profissionais do setor pet que desejam otimizar a gestão, automatizar lembretes e melhorar o relacionamento com clientes.

---

## 5. PERSONAS & HISTÓRIAS DE USUÁRIO

### Persona 1 – Ana, dona de Petshop

**Contexto:** 34 anos, gerencia um pet shop com 4 funcionários.

**Dores:** Falta de controle de vacinas, agendamentos e históricos.

**Expectativas:** Ver agendamentos e status clínicos em um painel unificado.

**Histórias de Usuário:**
- Como Ana, quero visualizar no dashboard todos os pets com vacinas pendentes, para contatar tutores automaticamente.
- Como Ana, quero agendar consultas e serviços no mesmo painel, evitando duplicidade de cadastros.

### Persona 2 – Dr. Carlos, Veterinário

**Contexto:** 42 anos, atende em clínica com 2 assistentes.

**Dores:** Perda de prescrições, dificuldade em controlar doses e lembretes de reforço.

**Expectativas:** Sistema que sugira medicamentos e calcule doses automaticamente.

**Histórias de Usuário:**
- Como Dr. Carlos, quero registrar prescrições digitais e acessar histórico clínico do pet.
- Como Dr. Carlos, quero consultar o bulário e usar a calculadora de dose integrada.

### Persona 3 – João, Tutor de Pet

**Contexto:** 28 anos, tutor de dois cães.

**Dores:** Esquece vacinas e horários de banho.

**Expectativas:** Receber lembretes automáticos e agendar online.

**Histórias de Usuário:**
- Como João, quero receber notificações por WhatsApp sobre vacinas e consultas.
- Como João, quero avaliar o atendimento e receber campanhas de desconto.

---

## 6. FUNCIONALIDADES ESSENCIAIS

- Cadastro de tutores, pets e históricos clínicos
- Agendamento de serviços e consultas veterinárias
- Registro e lembrete automático de vacinas e retornos
- Prescrições digitais e bulário básico
- Calculadora de dose integrada (peso x medicamento)
- Dashboard de comunicação e feedback

---

## 7. REQUISITOS FUNCIONAIS (RF)

| Código | Descrição | Atores | Prioridade | Critérios de Aceitação |
|--------|-----------|--------|------------|------------------------|
| RF001 | Cadastro de Tutor | Funcionário, Admin | Essencial | Validação de campos obrigatórios e exibição no painel |
| RF002 | Cadastro de Pet + Histórico Clínico | Funcionário, Veterinário | Alta | Pet vinculado ao tutor e registro de histórico médico |
| RF003 | Agendamento de Serviço/Consulta | Tutor, Funcionário | Essencial | Notificação automática e reagendamento permitido |
| RF004 | Lembrete de Vacina/Consulta | Sistema | Alta | Envio automático via e-mail/WhatsApp |
| RF005 | Prescrição Digital | Veterinário | Média | Geração de receita simples com histórico |
| RF006 | Calculadora de Dose | Veterinário | Média | Cálculo baseado em peso e tipo de medicamento |
| RF007 | Feedback do Cliente | Tutor, Sistema | Média | Coleta e registro automático após atendimento |

---

## 8. REQUISITOS NÃO FUNCIONAIS (NF)

- **[NF001] Performance:** Respostas ≤ 2s por requisição
- **[NF002] Segurança:** Aderência à LGPD e criptografia de dados
- **[NF003] Disponibilidade:** Uptime ≥ 99%
- **[NF004] Escalabilidade:** Suporte a crescimento de clínicas múltiplas
- **[NF005] Usabilidade:** Interface responsiva, intuitiva e mobile-friendly

---

## 9. HIPÓTESES & VALIDAÇÕES

| Hipótese | Validação |
|----------|-----------|
| Petshops valorizam lembretes automáticos | Métricas de engajamento após 30 dias |
| Veterinários adotam prescrições digitais | Pesquisas de satisfação e taxa de uso |
| Tutores preferem comunicação via WhatsApp | Taxa de abertura e cliques em notificações |

---

## 10. FLUXOS DE USUÁRIO (ALTO NÍVEL)

### Fluxo 1 – Agendamento de Consulta:
Tutor → Escolhe serviço → Seleciona data → Confirma → Recebe notificação

### Fluxo 2 – Lembrete de Vacina:
Sistema → Calcula próxima dose → Envia lembrete → Atualiza status

### Fluxo 3 – Prescrição Digital:
Veterinário → Registra consulta → Gera prescrição → Tutor recebe link e lembrete da próxima dose

---

## 11. WIREFRAMES / PROTÓTIPOS (ESPAÇO)

Login / Dashboard / Agendamento / Feedback

---

## 12. ARQUITETURA (VISÃO GERAL)

**Abordagem:** Clean Architecture + DDD

**Camadas:**
- **Domain:** Entidades Tutor, Pet, Vacina, Consulta, Prescrição
- **Application:** Serviços de agendamento, lembrete, prescrição
- **Infrastructure:** PostgreSQL, Redis, fila de mensagens, Mailgun/Z-API
- **Interface:** Painel Web (Vue.js 3 + TailwindCSS)

---

## 13. ENDPOINTS (SUMÁRIO)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/tutors` | Cadastrar tutor |
| POST | `/api/pets` | Cadastrar pet |
| POST | `/api/schedule` | Criar agendamento |
| GET | `/api/vaccines/reminders` | Listar vacinas com lembretes |
| POST | `/api/feedback` | Registrar avaliação |

---

## 14. SEGURANÇA & CONFORMIDADE

- Autenticação JWT + RBAC (controle por perfil)
- Criptografia bcrypt + TLS
- Backup automático diário
- Consentimento explícito para uso de dados

---

## 15. OBSERVABILIDADE & AUDITORIA

- Logs de API com timestamps
- Monitoramento de e-mails e falhas de entrega
- Dashboard de métricas (serviços, feedbacks e lembretes enviados)

---

## 16. ROADMAP DE RELEASES

| Release | Funcionalidades |
|---------|-----------------|
| R1 (MVP Core) | Tutores, Pets, Agendamentos, Vacinas, Lembretes |
| R2 | Prescrições digitais, calculadora de dose |
| R3 | Comunicação automatizada, campanhas e feedback |
| R4 (Futuro) | App mobile, pontuação, marketplace |

---

## 17. PLANO DE TESTES

- **Tipos:** Unitário, integração e end-to-end
- **Critérios:** Cobertura mínima de 80% nos módulos principais
- **Rollback:** Reversão automática via deploy anterior em caso de falha

---

## 18. KPIs PRINCIPAIS

- Taxa de confirmações de agendamento (%)
- Taxa de abertura de notificações (%)
- Retenção de tutores (%)
- Satisfação média (1–5 estrelas)
- Uso do módulo de prescrição (%)

---

## 19. RISCOS e MITIGAÇÕES

| Risco | Mitigação |
|-------|-----------|
| Falhas de envio de e-mail/WhatsApp | Provedor de fallback |
| Perda de dados | Backup automático diário |
| Resistência de adoção clínica | Onboarding guiado e suporte remoto |

---

## 20. DEFINIÇÃO DE PRONTO (DOD)

- Código revisado e testado
- API documentada
- Deploy em staging com sucesso
- Testes de lembretes e e-mails validados

---

## 21. TECNOLOGIAS E INFRAESTRUTURA

- **Backend:** Laravel 11
- **Frontend:** Vue.js 3 + TailwindCSS
- **Banco:** PostgreSQL
- **Mensageria:** Redis / Laravel Queue
- **E-mail:** Mailgun / AWS SES
- **WhatsApp:** Twilio API / Z-API
- **Infraestrutura:** Docker + AWS EC2 / RDS / S3

---

## 22. ASSUNÇÕES e RESTRIÇÕES

### Assunções:
- Tutores têm acesso à internet e e-mail/WhatsApp válidos
- Pet Shops mantêm cadastro digital atualizado

### Restrições:
- Prazo de entrega: 60 dias
- Orçamento limitado a R$ 10.000 para MVP
- Suporte inicial apenas em navegadores modernos

---

## 23. CASOS DE USO ILUSTRATIVOS

### Caso 1 – Tutor João:
Agendou o banho da cachorra Mel via app. Recebe lembrete automático no WhatsApp, avalia com 5 estrelas e ganha desconto na próxima consulta.

### Caso 2 – Dr. Carlos:
Registra consulta da gata Luna, gera prescrição digital com dose calculada automaticamente e envia lembrete de reforço para o tutor.

### Caso 3 – Petshop da Ana:
Visualiza no dashboard os pets com vacinas atrasadas e lança campanha "Semana da Vacina", atingindo 60% de agendamentos em 3 dias.

---

## 24. ROADMAP VISUAL (MoSCoW)

| Categoria | Funcionalidades |
|-----------|-----------------|
| Must Have | Tutores, Pets, Agendamentos, Vacinas, Lembretes |
| Should Have | Prescrições, Calculadora de Dose, Feedback |
| Could Have | Campanhas, Marketing Avançado |
| Won't Have (fase inicial) | Sistema de pontos, App mobile |

---

## 25. GLOSSÁRIO

| Termo | Definição |
|-------|-----------|
| Tutor | Dono do pet |
| Agendamento | Serviço ou consulta marcada |
| Prescrição Digital | Receita eletrônica emitida pelo veterinário |
| Bulário | Base de dados de medicamentos veterinários |
| Calculadora de Dose | Ferramenta que sugere quantidade de medicamento por peso |
| Feedback | Avaliação pós-serviço |

---

## 26. VISUALIZAÇÕES E DIAGRAMAS

- Diagrama de Caso de Uso (Tutor/Funcionário/Sistema)
- Diagrama de Componentes (Módulos: Tutores, Pets, Vacinas, Comunicação)
- Diagrama de Fluxo de Dados (Cadastro → Notificação → Feedback)

---

## 27. PLANO DE FEEDBACK

- Coleta automática após cada atendimento
- Análise quinzenal das notas médias
- Revisão do backlog de melhorias com base nos feedbacks

---

## 🧭 Navegação

### Documentos Relacionados
- 📖 [README Principal](../README.md) - Voltar para visão geral
- 🚀 [Guia de Início](IniciarProjeto.md) - Configuração do ambiente
- 💻 [Frontend README](../frontend/README.md) - Documentação do frontend

### Seções Importantes deste Documento
- 📋 [Requisitos Funcionais](#7-requisitos-funcionais-rf)
- 🏗️ [Arquitetura](#12-arquitetura-visão-geral)
- 🔐 [Segurança](#14-segurança--conformidade)
- 🚀 [Roadmap](#16-roadmap-de-releases)
- 💻 [Tecnologias](#21-tecnologias-e-infraestrutura)
