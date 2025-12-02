import { ref } from 'vue'
import { analyticsApi } from '../api/endpoints/analytics'

export function useAnalytics() {
  const dashboardData = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const fetchDashboard = async () => {
    loading.value = true
    error.value = null
    try {
      const response = await analyticsApi.getDashboard()
      dashboardData.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar dados do dashboard'
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    dashboardData,
    loading,
    error,
    fetchDashboard
  }
}



