import apiClient from '../client'

export const usersApi = {
  // GET - Listar todos os usuários
  getAll: async (params = {}) => {
    return apiClient.get('/users', { params })
  },
  
  // GET - Buscar usuário por ID
  getById: async (id) => {
    return apiClient.get(`/users/${id}`)
  },
  
  // POST - Criar novo usuário (usa /auth/register conforme backend)
  create: async (data) => {
    return apiClient.post('/auth/register', data)
  },
  
  // PUT - Atualizar usuário
  update: async (id, data) => {
    return apiClient.put(`/users/${id}`, data)
  },
  
  // DELETE - Excluir usuário
  delete: async (id) => {
    return apiClient.delete(`/users/${id}`)
  }
}

