import apiClient from '../client'

export const prescriptionsApi = {
  // GET - Listar todas as prescrições
  getAll: async (params = {}) => {
    return apiClient.get('/prescriptions', { params })
  },
  
  // GET - Buscar prescrição por ID
  getById: async (id) => {
    return apiClient.get(`/prescriptions/${id}`)
  },
  
  // GET - Buscar prescrições por pet
  getByPet: async (petId) => {
    return apiClient.get(`/prescriptions/pet/${petId}`)
  },
  
  // POST - Criar nova prescrição
  create: async (data) => {
    return apiClient.post('/prescriptions', data)
  },
  
  // PUT - Atualizar prescrição
  update: async (id, data) => {
    return apiClient.put(`/prescriptions/${id}`, data)
  },
  
  // DELETE - Excluir prescrição
  delete: async (id) => {
    return apiClient.delete(`/prescriptions/${id}`)
  }
}

