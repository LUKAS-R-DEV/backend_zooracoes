<template>
  <Transition name="toast">
    <div 
      v-if="visible" 
      :class="['toast', `toast-${type}`]"
      @click="handleClose"
    >
      <div class="toast-icon-wrapper">
        <Icon :name="iconName" :size="20" class="toast-icon" />
      </div>
      <div class="toast-content">
        <div class="toast-message">{{ message }}</div>
      </div>
      <button class="toast-close" @click.stop="handleClose">
        <Icon name="x" :size="16" />
      </button>
      <div v-if="duration > 0" class="toast-progress" :style="{ animationDuration: `${duration}ms` }"></div>
    </div>
  </Transition>
</template>

<script setup>
import { computed } from 'vue'
import Icon from './Icon.vue'

const props = defineProps({
  id: {
    type: Number,
    required: true
  },
  message: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'info',
    validator: (value) => ['success', 'error', 'warning', 'info'].includes(value)
  },
  duration: {
    type: Number,
    default: 5000
  },
  visible: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['close'])

const iconName = computed(() => {
  switch (props.type) {
    case 'success':
      return 'check-circle'
    case 'error':
      return 'alert-circle'
    case 'warning':
      return 'alert-triangle'
    default:
      return 'info'
  }
})

const handleClose = () => {
  emit('close', props.id)
}
</script>

<style scoped>
.toast {
  position: relative;
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 300px;
  max-width: 500px;
  padding: 16px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
  border-left: 4px solid;
  cursor: pointer;
  overflow: hidden;
  transition: all 0.3s ease;
}

.toast:hover {
  transform: translateX(-4px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.2);
}

.toast-success {
  border-left-color: var(--color-primary);
  background: linear-gradient(135deg, #ffffff 0%, #f0fdf4 100%);
}

.toast-error {
  border-left-color: var(--color-error);
  background: linear-gradient(135deg, #ffffff 0%, #fef2f2 100%);
}

.toast-warning {
  border-left-color: #f59e0b;
  background: linear-gradient(135deg, #ffffff 0%, #fffbeb 100%);
}

.toast-info {
  border-left-color: #3b82f6;
  background: linear-gradient(135deg, #ffffff 0%, #eff6ff 100%);
}

.toast-icon-wrapper {
  flex-shrink: 0;
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.8);
}

.toast-success .toast-icon-wrapper {
  background: rgba(34, 197, 94, 0.1);
}

.toast-error .toast-icon-wrapper {
  background: rgba(239, 68, 68, 0.1);
}

.toast-warning .toast-icon-wrapper {
  background: rgba(245, 158, 11, 0.1);
}

.toast-info .toast-icon-wrapper {
  background: rgba(59, 130, 246, 0.1);
}

.toast-icon {
  color: var(--color-gray-700);
}

.toast-success .toast-icon {
  color: var(--color-primary);
}

.toast-error .toast-icon {
  color: var(--color-error);
}

.toast-warning .toast-icon {
  color: #f59e0b;
}

.toast-info .toast-icon {
  color: #3b82f6;
}

.toast-content {
  flex: 1;
  min-width: 0;
}

.toast-message {
  font-size: 0.9375rem;
  font-weight: 500;
  color: var(--color-gray-900);
  line-height: 1.5;
}

.toast-close {
  flex-shrink: 0;
  width: 24px;
  height: 24px;
  border: none;
  background: transparent;
  color: var(--color-gray-400);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.toast-close:hover {
  background: var(--color-gray-100);
  color: var(--color-gray-700);
}

.toast-progress {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 3px;
  background: currentColor;
  animation: progress linear;
  border-radius: 0 0 12px 12px;
}

.toast-success .toast-progress {
  background: var(--color-primary);
}

.toast-error .toast-progress {
  background: var(--color-error);
}

.toast-warning .toast-progress {
  background: #f59e0b;
}

.toast-info .toast-progress {
  background: #3b82f6;
}

@keyframes progress {
  from {
    width: 100%;
  }
  to {
    width: 0%;
  }
}

.toast-enter-active {
  transition: all 0.3s ease;
}

.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100%) scale(0.9);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(100%) scale(0.9);
}
</style>



