import apiClient from '../client'

export const servicesApi = {
  // GET - Listar todos os serviços
  getAll: async (params = {}) => {
    return apiClient.get('/services', { params })
  },
  
  // GET - Buscar serviço por ID
  getById: async (id) => {
    return apiClient.get(`/services/${id}`)
  },
  
  // POST - Criar novo serviço
  create: async (data) => {
    return apiClient.post('/services', data)
  },
  
  // PUT - Atualizar serviço
  update: async (id, data) => {
    return apiClient.put(`/services/${id}`, data)
  },
  
  // DELETE - Excluir serviço
  delete: async (id) => {
    return apiClient.delete(`/services/${id}`)
  }
}

