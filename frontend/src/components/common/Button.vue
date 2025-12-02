<template>
  <button 
    :class="['btn', `btn-${variant}`, `btn-${size}`, { 'btn-disabled': disabled, 'btn-loading': loading }]"
    :disabled="disabled || loading"
    :type="type"
    @click="$emit('click', $event)"
  >
    <Icon v-if="icon && !loading" :name="icon" :size="iconSize" class="btn-icon" />
    <Icon v-if="loading" name="loader-2" :size="iconSize" class="btn-icon spinning" />
    <span v-if="$slots.default" class="btn-text">
      <slot />
    </span>
  </button>
</template>

<script setup>
import Icon from './Icon.vue'

defineProps({
  variant: {
    type: String,
    default: 'primary',
    validator: (value) => ['primary', 'secondary', 'danger', 'ghost'].includes(value)
  },
  disabled: {
    type: Boolean,
    default: false
  },
  loading: {
    type: Boolean,
    default: false
  },
  type: {
    type: String,
    default: 'button'
  },
  icon: {
    type: String,
    default: null
  },
  iconSize: {
    type: [Number, String],
    default: 18
  },
  size: {
    type: String,
    default: 'md',
    validator: (value) => ['sm', 'md', 'lg'].includes(value)
  }
})

defineEmits(['click'])
</script>

<style scoped>
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
  outline: none;
  position: relative;
  overflow: hidden;
  white-space: nowrap;
  user-select: none;
  font-family: inherit;
}

.btn::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  transform: translate(-50%, -50%);
  transition: width 0.3s ease, height 0.3s ease;
}

.btn:hover:not(:disabled)::before {
  width: 300px;
  height: 300px;
}

/* Sizes */
.btn-sm {
  padding: 8px 16px;
  font-size: 0.8125rem;
  border-radius: 8px;
}

.btn-md {
  padding: 12px 24px;
  font-size: 0.9375rem;
  border-radius: 10px;
}

.btn-lg {
  padding: 16px 32px;
  font-size: 1rem;
  border-radius: 12px;
}

/* Primary */
.btn-primary {
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  color: var(--color-white);
  box-shadow: 0 4px 12px rgba(34, 197, 94, 0.3);
}

.btn-primary:hover:not(:disabled) {
  background: linear-gradient(135deg, var(--color-primary-dark) 0%, var(--color-primary) 100%);
  box-shadow: 0 6px 16px rgba(34, 197, 94, 0.4);
  transform: translateY(-2px);
}

.btn-primary:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(34, 197, 94, 0.3);
}

/* Secondary */
.btn-secondary {
  background-color: var(--color-white);
  color: var(--color-gray-700);
  border: 2px solid var(--color-gray-200);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.btn-secondary:hover:not(:disabled) {
  background-color: var(--color-gray-50);
  border-color: var(--color-gray-300);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.btn-secondary:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

/* Danger */
.btn-danger {
  background: linear-gradient(135deg, var(--color-error) 0%, #dc2626 100%);
  color: var(--color-white);
  box-shadow: 0 4px 12px rgba(239, 68, 68, 0.3);
}

.btn-danger:hover:not(:disabled) {
  background: linear-gradient(135deg, #dc2626 0%, var(--color-error) 100%);
  box-shadow: 0 6px 16px rgba(239, 68, 68, 0.4);
  transform: translateY(-2px);
}

.btn-danger:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(239, 68, 68, 0.3);
}

/* Ghost */
.btn-ghost {
  background-color: transparent;
  color: var(--color-gray-700);
}

.btn-ghost:hover:not(:disabled) {
  background-color: var(--color-gray-100);
  color: var(--color-gray-900);
}

.btn-icon {
  flex-shrink: 0;
  transition: transform 0.2s ease;
}

.btn:hover:not(:disabled) .btn-icon {
  transform: scale(1.1);
}

.btn-text {
  display: inline-block;
}

.btn:disabled,
.btn-disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none !important;
  box-shadow: none !important;
}

.btn-loading {
  cursor: wait;
}

.spinning {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>

