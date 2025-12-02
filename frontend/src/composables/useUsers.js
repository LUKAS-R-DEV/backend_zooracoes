import { ref } from 'vue'
import { usersApi } from '../api/endpoints/users'

const users = ref([])
const user = ref(null)
const loading = ref(false)
const error = ref(null)

export function useUsers() {
  const fetchUsers = async (params = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await usersApi.getAll(params)
      if (response.data.content) {
        users.value = response.data.content
      } else {
        users.value = response.data
      }
      return users.value
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar usuários'
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchUser = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await usersApi.getById(id)
      user.value = response.data
      return user.value
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao buscar usuário'
      throw err
    } finally {
      loading.value = false
    }
  }

  const createUser = async (data) => {
    loading.value = true
    error.value = null
    try {
      const response = await usersApi.create(data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao criar usuário'
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateUser = async (id, data) => {
    loading.value = true
    error.value = null
    try {
      const response = await usersApi.update(id, data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao atualizar usuário'
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteUser = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await usersApi.delete(id)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || 'Erro ao excluir usuário'
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    users,
    user,
    loading,
    error,
    fetchUsers,
    fetchUser,
    createUser,
    updateUser,
    deleteUser
  }
}



