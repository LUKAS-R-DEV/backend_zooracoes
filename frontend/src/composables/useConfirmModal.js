import { ref } from 'vue'

// Estado global compartilhado
const modalState = ref({
  visible: false,
  title: '',
  message: '',
  confirmText: 'Confirmar',
  cancelText: 'Cancelar',
  variant: 'danger', // 'danger', 'warning', 'info'
  onConfirm: null,
  onCancel: null
})

export function useConfirmModal() {
  const showConfirm = (options) => {
    return new Promise((resolve) => {
      modalState.value = {
        visible: true,
        title: options.title || 'Confirmar ação',
        message: options.message || 'Tem certeza que deseja continuar?',
        confirmText: options.confirmText || 'Confirmar',
        cancelText: options.cancelText || 'Cancelar',
        variant: options.variant || 'danger',
        onConfirm: () => {
          modalState.value.visible = false
          resolve(true)
        },
        onCancel: () => {
          modalState.value.visible = false
          resolve(false)
        }
      }
    })
  }

  const hideConfirm = () => {
    modalState.value.visible = false
  }

  return {
    modalState,
    showConfirm,
    hideConfirm
  }
}

