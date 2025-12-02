import { ref, computed } from 'vue'
import { authApi } from '../api/endpoints/auth'

const user = ref(null)
const token = ref(localStorage.getItem('token') || null)
let initialized = false

export function useAuthStore() {
  const isAuthenticated = computed(() => !!token.value)
  const userRole = computed(() => user.value?.role || null)

  const login = async (email, password) => {
    try {
      const response = await authApi.login(email, password)
      token.value = response.data.token
      localStorage.setItem('token', token.value)
      await fetchUser()
      
      return { success: true }
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Erro ao fazer login' 
      }
    }
  }

  const fetchUser = async () => {
    try {
      const response = await authApi.getMe()
      user.value = response.data
    } catch (error) {
      console.error('Erro ao buscar usuário:', error)
      logout()
    }
  }

  const logout = () => {
    token.value = null
    user.value = null
    localStorage.removeItem('token')
    initialized = false
  }

  const hasRole = (roles) => {
    if (!user.value) return false
    if (Array.isArray(roles)) {
      return roles.includes(user.value.role)
    }
    return user.value.role === roles
  }

  // Inicializar usuário se já tiver token (apenas uma vez)
  if (token.value && !initialized) {
    initialized = true
    fetchUser().catch(() => {
      // Se falhar, não bloqueia o uso do store
      initialized = false
    })
  }

  return {
    user,
    token,
    isAuthenticated,
    userRole,
    login,
    logout,
    fetchUser,
    hasRole
  }
}

