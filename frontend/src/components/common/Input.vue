<template>
  <div class="input-group">
    <label v-if="label" class="input-label" :for="id">
      <span class="label-text">
        {{ label }}
        <span v-if="required" class="required-asterisk">*</span>
      </span>
    </label>
    <div class="input-wrapper">
      <Icon v-if="icon" :name="icon" :size="18" class="input-icon" />
      <input
        :id="id"
        :type="type"
        :placeholder="placeholder"
        :value="maskedValue"
        :class="['input', { 'input-error': error, 'input-with-icon': icon }]"
        :required="required"
        @input="handleInput"
        @blur="$emit('blur')"
        @focus="$emit('focus')"
      />
      <Icon v-if="error" name="alert-circle" :size="18" class="input-error-icon" />
      <Icon v-if="!error && modelValue && success" name="check-circle" :size="18" class="input-success-icon" />
    </div>
    <span v-if="error" class="error-message">
      <Icon name="alert-circle" :size="14" />
      {{ error }}
    </span>
    <span v-if="hint && !error" class="input-hint">{{ hint }}</span>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import Icon from './Icon.vue'
import { applyMask } from '@/utils/mask'

const props = defineProps({
  id: String,
  label: String,
  type: {
    type: String,
    default: 'text'
  },
  placeholder: String,
  modelValue: [String, Number],
  required: Boolean,
  error: String,
  hint: String,
  icon: String,
  success: Boolean,
  mask: [String, Array, Object]
})

const emit = defineEmits(['update:modelValue', 'blur', 'focus'])

const maskedValue = computed(() => {
  if (!props.mask || !props.modelValue) {
    return props.modelValue || ''
  }
  return applyMask(String(props.modelValue), props.mask)
})

const handleInput = (event) => {
  let value = event.target.value
  if (props.mask) {
    // Remove caracteres não numéricos para máscaras
    if (props.mask === 'phone' || props.mask.includes('#')) {
      value = value.replace(/\D/g, '')
    }
  }
  emit('update:modelValue', value)
}
</script>

<style scoped>
.input-group {
  margin-bottom: var(--spacing-lg);
  position: relative;
}

.input-label {
  display: block;
  margin-bottom: var(--spacing-xs);
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--color-gray-700);
  transition: color 0.2s ease;
}

.label-text {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.required-asterisk {
  color: var(--color-error);
  font-weight: 600;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 14px;
  color: var(--color-gray-400);
  z-index: 1;
  transition: color 0.2s ease;
  pointer-events: none;
}

.input {
  width: 100%;
  padding: 12px 16px;
  padding-left: var(--input-padding-left, 16px);
  border: 2px solid var(--color-gray-200);
  border-radius: 10px;
  font-size: 0.9375rem;
  background-color: var(--color-white);
  color: var(--color-gray-900);
  transition: all 0.2s ease;
  outline: none;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}

.input-with-icon {
  padding-left: 44px;
}

.input::placeholder {
  color: var(--color-gray-400);
}

.input:hover:not(:disabled) {
  border-color: var(--color-gray-300);
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
}

.input:focus {
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(34, 197, 94, 0.1), 0 2px 4px 0 rgba(0, 0, 0, 0.05);
  outline: none;
}

.input:focus + .input-icon,
.input:focus ~ .input-icon {
  color: var(--color-primary);
}

.input:disabled {
  background-color: var(--color-gray-50);
  cursor: not-allowed;
  opacity: 0.6;
}

.input-error {
  border-color: var(--color-error);
  background-color: #fef2f2;
}

.input-error:focus {
  border-color: var(--color-error);
  box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.1);
}

.input-error-icon {
  position: absolute;
  right: 14px;
  color: var(--color-error);
  z-index: 1;
  pointer-events: none;
}

.input-success-icon {
  position: absolute;
  right: 14px;
  color: var(--color-primary);
  z-index: 1;
  pointer-events: none;
}

.error-message {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-top: 6px;
  font-size: 0.8125rem;
  color: var(--color-error);
  font-weight: 500;
  animation: slideDown 0.2s ease;
}

.input-hint {
  display: block;
  margin-top: 6px;
  font-size: 0.8125rem;
  color: var(--color-gray-500);
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-4px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>

