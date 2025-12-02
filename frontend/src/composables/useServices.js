import { ref } from 'vue'
import { servicesApi } from '../api/endpoints/services'

export function useServices() {
  const services = ref([])
  const service = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const fetchServices = async () => {
    loading.value = true
    error.value = null
    try {
      const response = await servicesApi.getAll()
      services.value = response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar serviços'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchService = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await servicesApi.getById(id)
      service.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar serviço'
      throw err
    } finally {
      loading.value = false
    }
  }

  const createService = async (data) => {
    loading.value = true
    error.value = null
    try {
      const response = await servicesApi.create(data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao criar serviço'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateService = async (id, data) => {
    loading.value = true
    error.value = null
    try {
      const response = await servicesApi.update(id, data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar serviço'
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteService = async (id) => {
    loading.value = true
    error.value = null
    try {
      await servicesApi.delete(id)
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao deletar serviço'
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    services,
    service,
    loading,
    error,
    fetchServices,
    fetchService,
    createService,
    updateService,
    deleteService
  }
}



