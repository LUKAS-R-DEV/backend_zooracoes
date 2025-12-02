import { ref } from 'vue'
import { prescriptionsApi } from '../api/endpoints/prescriptions'

export function usePrescriptions() {
  const prescriptions = ref([])
  const prescription = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const fetchPrescriptions = async () => {
    loading.value = true
    error.value = null
    try {
      const response = await prescriptionsApi.getAll()
      prescriptions.value = response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar prescrições'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchPrescription = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await prescriptionsApi.getById(id)
      prescription.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar prescrição'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchPrescriptionsByPet = async (petId) => {
    loading.value = true
    error.value = null
    try {
      const response = await prescriptionsApi.getByPet(petId)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar prescrições do pet'
      throw err
    } finally {
      loading.value = false
    }
  }

  const createPrescription = async (data) => {
    loading.value = true
    error.value = null
    try {
      const response = await prescriptionsApi.create(data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao criar prescrição'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updatePrescription = async (id, data) => {
    loading.value = true
    error.value = null
    try {
      const response = await prescriptionsApi.update(id, data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar prescrição'
      throw err
    } finally {
      loading.value = false
    }
  }

  const deletePrescription = async (id) => {
    loading.value = true
    error.value = null
    try {
      await prescriptionsApi.delete(id)
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao deletar prescrição'
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    prescriptions,
    prescription,
    loading,
    error,
    fetchPrescriptions,
    fetchPrescription,
    fetchPrescriptionsByPet,
    createPrescription,
    updatePrescription,
    deletePrescription
  }
}



