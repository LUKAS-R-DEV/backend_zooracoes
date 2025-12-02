import apiClient from '../client'

export const analyticsApi = {
  // GET - Buscar dados do dashboard
  getDashboard: async () => {
    return apiClient.get('/analytics/dashboard')
  }
}

