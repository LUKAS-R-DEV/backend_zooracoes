import { ref } from 'vue'
import { vaccinesApi } from '../api/endpoints/vaccines'

export function useVaccines() {
  const vaccines = ref([])
  const vaccine = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const fetchVaccines = async () => {
    loading.value = true
    error.value = null
    try {
      const response = await vaccinesApi.getAll()
      vaccines.value = response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar vacinas'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchVaccine = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await vaccinesApi.getById(id)
      vaccine.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar vacina'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchVaccinesByPet = async (petId) => {
    loading.value = true
    error.value = null
    try {
      const response = await vaccinesApi.getByPet(petId)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar vacinas do pet'
      throw err
    } finally {
      loading.value = false
    }
  }

  const createVaccine = async (data) => {
    loading.value = true
    error.value = null
    try {
      const response = await vaccinesApi.create(data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao criar vacina'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateVaccine = async (id, data) => {
    loading.value = true
    error.value = null
    try {
      const response = await vaccinesApi.update(id, data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar vacina'
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteVaccine = async (id) => {
    loading.value = true
    error.value = null
    try {
      await vaccinesApi.delete(id)
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao deletar vacina'
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    vaccines,
    vaccine,
    loading,
    error,
    fetchVaccines,
    fetchVaccine,
    fetchVaccinesByPet,
    createVaccine,
    updateVaccine,
    deleteVaccine
  }
}



