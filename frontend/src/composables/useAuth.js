import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'

export function useAuth() {
  const authStore = useAuthStore()
  const router = useRouter()

  const login = async (email, password) => {
    const result = await authStore.login(email, password)
    if (result.success) {
      router.push('/dashboard')
    }
    return result
  }

  const logout = () => {
    authStore.logout()
    router.push('/login')
  }

  return {
    ...authStore,
    login,
    logout
  }
}


