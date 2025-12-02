import apiClient from '../client'

export const tutorsApi = {
  // GET - Listar todos os tutores
  getAll: async (params = {}) => {
    return apiClient.get('/tutors', { params })
  },
  
  // GET - Buscar tutor por ID
  getById: async (id) => {
    return apiClient.get(`/tutors/${id}`)
  },
  
  // POST - Criar novo tutor
  create: async (data) => {
    return apiClient.post('/tutors', data)
  },
  
  // PUT - Atualizar tutor
  update: async (id, data) => {
    return apiClient.put(`/tutors/${id}`, data)
  },
  
  // DELETE - Excluir tutor
  delete: async (id) => {
    return apiClient.delete(`/tutors/${id}`)
  }
}

