import { ref } from 'vue'

const toasts = ref([])
let toastId = 0

export function useToast() {
  const showToast = (message, type = 'info', duration = 5000) => {
    const id = ++toastId
    const toast = {
      id,
      message,
      type, // 'success', 'error', 'warning', 'info'
      duration,
      visible: true
    }

    toasts.value.push(toast)

    // Auto remover após a duração
    if (duration > 0) {
      setTimeout(() => {
        removeToast(id)
      }, duration)
    }

    return id
  }

  const removeToast = (id) => {
    const index = toasts.value.findIndex(t => t.id === id)
    if (index !== -1) {
      toasts.value[index].visible = false
      // Remover do array após animação
      setTimeout(() => {
        toasts.value.splice(index, 1)
      }, 300)
    }
  }

  const success = (message, duration) => {
    return showToast(message, 'success', duration)
  }

  const error = (message, duration) => {
    return showToast(message, 'error', duration)
  }

  const warning = (message, duration) => {
    return showToast(message, 'warning', duration)
  }

  const info = (message, duration) => {
    return showToast(message, 'info', duration)
  }

  return {
    toasts,
    success,
    error,
    warning,
    info,
    removeToast
  }
}



