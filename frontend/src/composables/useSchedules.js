import { ref } from 'vue'
import { schedulesApi } from '../api/endpoints/schedules'

export function useSchedules() {
  const schedules = ref([])
  const schedule = ref(null)
  const loading = ref(false)
  const error = ref(null)
  const pagination = ref({
    page: 0,
    size: 10,
    total: 0,
    totalPages: 0
  })

  const fetchSchedules = async (params = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await schedulesApi.getAll(params)
      if (response.data.content) {
        schedules.value = response.data.content
        pagination.value = {
          page: response.data.page,
          size: response.data.size,
          total: response.data.totalElements,
          totalPages: response.data.totalPages
        }
      } else {
        schedules.value = response.data
      }
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar agendamentos'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchSchedule = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await schedulesApi.getById(id)
      schedule.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar agendamento'
      throw err
    } finally {
      loading.value = false
    }
  }

  const createSchedule = async (data) => {
    loading.value = true
    error.value = null
    try {
      const response = await schedulesApi.create(data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao criar agendamento'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateSchedule = async (id, data) => {
    loading.value = true
    error.value = null
    try {
      const response = await schedulesApi.update(id, data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar agendamento'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateScheduleStatus = async (id, status) => {
    loading.value = true
    error.value = null
    try {
      const response = await schedulesApi.updateStatus(id, status)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar status do agendamento'
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteSchedule = async (id) => {
    loading.value = true
    error.value = null
    try {
      await schedulesApi.delete(id)
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao deletar agendamento'
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    schedules,
    schedule,
    loading,
    error,
    pagination,
    fetchSchedules,
    fetchSchedule,
    createSchedule,
    updateSchedule,
    updateScheduleStatus,
    deleteSchedule
  }
}


