import apiClient from '../client'

export const petsApi = {
  // GET - Listar todos os pets
  getAll: async (params = {}) => {
    return apiClient.get('/pets', { params })
  },
  
  // GET - Buscar pet por ID
  getById: async (id) => {
    return apiClient.get(`/pets/${id}`)
  },
  
  // GET - Buscar pets por tutor
  getByTutor: async (tutorId) => {
    return apiClient.get(`/pets/tutor/${tutorId}`)
  },
  
  // POST - Criar novo pet
  create: async (data) => {
    return apiClient.post('/pets', data)
  },
  
  // PUT - Atualizar pet
  update: async (id, data) => {
    return apiClient.put(`/pets/${id}`, data)
  },
  
  // DELETE - Excluir pet
  delete: async (id) => {
    return apiClient.delete(`/pets/${id}`)
  }
}

