import { ref } from 'vue'
import { tutorsApi } from '../api/endpoints/tutors'
import { useToast } from './useToast'

export function useTutors() {
  const toast = useToast()
  const tutors = ref([])
  const tutor = ref(null)
  const loading = ref(false)
  const error = ref(null)
  const pagination = ref({
    page: 0,
    size: 10,
    total: 0,
    totalPages: 0
  })

  const fetchTutors = async (params = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await tutorsApi.getAll(params)
      if (response.data.content) {
        tutors.value = response.data.content
        pagination.value = {
          page: response.data.page,
          size: response.data.size,
          total: response.data.totalElements,
          totalPages: response.data.totalPages
        }
      } else {
        tutors.value = response.data
      }
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar tutores'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchTutor = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await tutorsApi.getById(id)
      tutor.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar tutor'
      throw err
    } finally {
      loading.value = false
    }
  }

  const createTutor = async (data) => {
    loading.value = true
    error.value = null
    try {
      const response = await tutorsApi.create(data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao criar tutor'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateTutor = async (id, data) => {
    loading.value = true
    error.value = null
    try {
      const response = await tutorsApi.update(id, data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar tutor'
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteTutor = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await tutorsApi.delete(id)
      toast.success('Tutor excluído com sucesso!')
      return response
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.response?.data || 'Erro ao excluir tutor'
      error.value = errorMessage
      toast.error(errorMessage)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    tutors,
    tutor,
    loading,
    error,
    pagination,
    fetchTutors,
    fetchTutor,
    createTutor,
    updateTutor,
    deleteTutor
  }
}

