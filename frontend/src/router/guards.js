import { useAuthStore } from '@/stores/auth'

export const authGuard = (to, from, next) => {
  const { isAuthenticated } = useAuthStore()
  
  if (to.meta.requiresAuth && !isAuthenticated.value) {
    next('/login')
  } else if (to.path === '/login' && isAuthenticated.value) {
    next('/dashboard')
  } else {
    next()
  }
}

export const roleGuard = (allowedRoles) => {
  return (to, from, next) => {
    const { hasRole } = useAuthStore()
    
    if (!hasRole(allowedRoles)) {
      next('/dashboard')
    } else {
      next()
    }
  }
}



