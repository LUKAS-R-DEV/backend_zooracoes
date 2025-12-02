import apiClient from '../client'

export const authApi = {
  // POST - Login de usuário
  login: async (email, password) => {
    return apiClient.post('/auth/login', { email, password })
  },
  
  // POST - Registrar novo usuário
  register: async (data) => {
    return apiClient.post('/auth/register', data)
  },
  
  // GET - Buscar dados do usuário autenticado
  getMe: async () => {
    return apiClient.get('/auth/me')
  }
}

