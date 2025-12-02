import apiClient from '../client'

export const schedulesApi = {
  // GET - Listar todos os agendamentos
  getAll: async (params = {}) => {
    return apiClient.get('/schedules', { params })
  },
  
  // GET - Buscar agendamento por ID
  getById: async (id) => {
    return apiClient.get(`/schedules/${id}`)
  },
  
  // POST - Criar novo agendamento
  create: async (data) => {
    return apiClient.post('/schedules', data)
  },
  
  // PUT - Atualizar agendamento
  update: async (id, data) => {
    return apiClient.put(`/schedules/${id}`, data)
  },
  
  // PATCH - Atualizar apenas o status
  updateStatus: async (id, status) => {
    return apiClient.patch(`/schedules/${id}/status`, { status })
  },
  
  // DELETE - Excluir agendamento
  delete: async (id) => {
    return apiClient.delete(`/schedules/${id}`)
  }
}

