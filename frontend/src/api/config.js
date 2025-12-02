// Configuração centralizada da API
export const API_CONFIG = {
  // URL base da API
  baseURL: import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? '/api' : 'http://localhost:8080'),
  
  // Timeout padrão para requisições
  timeout: 30000,
  
  // Modo de mock (para desenvolvimento sem backend)
  mockMode: false, // Desabilitado - usando dados reais do banco
  
  // Endpoints base por módulo
  endpoints: {
    auth: '/auth',
    tutors: '/tutors',
    pets: '/pets',
    schedules: '/schedules',
    services: '/services',
    vaccines: '/vaccines',
    prescriptions: '/prescriptions',
    users: '/users',
    analytics: '/analytics'
  }
}

