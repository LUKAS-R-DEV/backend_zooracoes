<template>
  <Transition name="modal">
    <div v-if="visible" class="modal-overlay" @click.self="handleCancel">
      <div class="modal-container">
        <div class="modal-header">
          <div class="modal-icon-wrapper" :class="`modal-icon-${variant}`">
            <Icon :name="iconName" :size="24" />
          </div>
          <h2 class="modal-title">{{ title }}</h2>
        </div>
        
        <div class="modal-body">
          <p class="modal-message">{{ message }}</p>
        </div>

        <div class="modal-footer">
          <Button variant="secondary" @click="handleCancel">
            {{ cancelText }}
          </Button>
          <Button :variant="variant === 'danger' ? 'danger' : 'primary'" @click="handleConfirm">
            {{ confirmText }}
          </Button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { computed } from 'vue'
import Button from './Button.vue'
import Icon from './Icon.vue'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: 'Confirmar ação'
  },
  message: {
    type: String,
    default: 'Tem certeza que deseja continuar?'
  },
  confirmText: {
    type: String,
    default: 'Confirmar'
  },
  cancelText: {
    type: String,
    default: 'Cancelar'
  },
  variant: {
    type: String,
    default: 'danger',
    validator: (value) => ['danger', 'warning', 'info'].includes(value)
  },
  onConfirm: {
    type: Function,
    default: null
  },
  onCancel: {
    type: Function,
    default: null
  }
})

const emit = defineEmits(['confirm', 'cancel'])

const iconName = computed(() => {
  switch (props.variant) {
    case 'danger':
      return 'alert-triangle'
    case 'warning':
      return 'alert-circle'
    default:
      return 'info'
  }
})

const handleConfirm = () => {
  if (props.onConfirm) {
    props.onConfirm()
  }
  emit('confirm')
}

const handleCancel = () => {
  if (props.onCancel) {
    props.onCancel()
  }
  emit('cancel')
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000;
  padding: 20px;
}

.modal-container {
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  max-width: 500px;
  width: 100%;
  max-height: 90vh;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  animation: modalSlideIn 0.3s ease;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(-20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.modal-header {
  padding: 24px 24px 16px;
  display: flex;
  align-items: center;
  gap: 16px;
  border-bottom: 1px solid var(--color-gray-200);
}

.modal-icon-wrapper {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.modal-icon-danger {
  background: rgba(239, 68, 68, 0.1);
  color: var(--color-error);
}

.modal-icon-warning {
  background: rgba(245, 158, 11, 0.1);
  color: #f59e0b;
}

.modal-icon-info {
  background: rgba(59, 130, 246, 0.1);
  color: #3b82f6;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--color-gray-900);
  margin: 0;
}

.modal-body {
  padding: 24px;
  flex: 1;
  overflow-y: auto;
}

.modal-message {
  font-size: 0.9375rem;
  color: var(--color-gray-700);
  line-height: 1.6;
  margin: 0;
}

.modal-footer {
  padding: 16px 24px 24px;
  display: flex;
  gap: 12px;
  justify-content: flex-end;
  border-top: 1px solid var(--color-gray-200);
}

.modal-enter-active {
  transition: all 0.3s ease;
}

.modal-leave-active {
  transition: all 0.3s ease;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  transform: translateY(-20px) scale(0.95);
}

@media (max-width: 640px) {
  .modal-container {
    max-width: 100%;
    margin: 10px;
  }

  .modal-header {
    padding: 20px 20px 12px;
  }

  .modal-body {
    padding: 20px;
  }

  .modal-footer {
    padding: 12px 20px 20px;
    flex-direction: column-reverse;
  }

  .modal-footer .btn {
    width: 100%;
  }
}
</style>

