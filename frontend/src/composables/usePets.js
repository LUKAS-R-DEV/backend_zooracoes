import { ref } from 'vue'
import { petsApi } from '../api/endpoints/pets'

export function usePets() {
  const pets = ref([])
  const pet = ref(null)
  const loading = ref(false)
  const error = ref(null)
  const pagination = ref({
    page: 0,
    size: 10,
    total: 0,
    totalPages: 0
  })

  const fetchPets = async (params = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await petsApi.getAll(params)
      if (response.data.content) {
        pets.value = response.data.content
        pagination.value = {
          page: response.data.page,
          size: response.data.size,
          total: response.data.totalElements,
          totalPages: response.data.totalPages
        }
      } else {
        pets.value = response.data
      }
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar pets'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchPet = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await petsApi.getById(id)
      pet.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar pet'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchPetsByTutor = async (tutorId) => {
    loading.value = true
    error.value = null
    try {
      const response = await petsApi.getByTutor(tutorId)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar pets do tutor'
      throw err
    } finally {
      loading.value = false
    }
  }

  const createPet = async (data) => {
    loading.value = true
    error.value = null
    try {
      const response = await petsApi.create(data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao criar pet'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updatePet = async (id, data) => {
    loading.value = true
    error.value = null
    try {
      const response = await petsApi.update(id, data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar pet'
      throw err
    } finally {
      loading.value = false
    }
  }

  const deletePet = async (id) => {
    loading.value = true
    error.value = null
    try {
      await petsApi.delete(id)
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao deletar pet'
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    pets,
    pet,
    loading,
    error,
    pagination,
    fetchPets,
    fetchPet,
    fetchPetsByTutor,
    createPet,
    updatePet,
    deletePet
  }
}


