import apiClient from '../client'

export const vaccinesApi = {
  // GET - Listar todas as vacinas
  getAll: async (params = {}) => {
    return apiClient.get('/vaccines', { params })
  },
  
  // GET - Buscar vacina por ID
  getById: async (id) => {
    return apiClient.get(`/vaccines/${id}`)
  },
  
  // GET - Buscar vacinas por pet
  getByPet: async (petId) => {
    return apiClient.get(`/vaccines/pet/${petId}`)
  },
  
  // POST - Criar nova vacina
  create: async (data) => {
    return apiClient.post('/vaccines', data)
  },
  
  // PUT - Atualizar vacina
  update: async (id, data) => {
    return apiClient.put(`/vaccines/${id}`, data)
  },
  
  // DELETE - Excluir vacina
  delete: async (id) => {
    return apiClient.delete(`/vaccines/${id}`)
  }
}

