<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal">
      <div class="modal-header">
        <h3 class="modal-title">Alterar Status</h3>
        <button class="modal-close" @click="$emit('close')">
          <Icon name="x" :size="20" />
        </button>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <label class="input-label" for="status">
            <span class="label-text">
              Novo Status
              <span class="required-asterisk">*</span>
            </span>
          </label>
          <div class="input-wrapper">
            <Icon name="circle" :size="18" class="input-icon" />
            <select 
              id="status" 
              class="input input-with-icon" 
              v-model="selectedStatus"
              :class="{ 'input-error': errors.status }"
            >
              <option value="PENDING">Pendente</option>
              <option value="DONE">Concluído</option>
              <option value="CANCELLED">Cancelado</option>
            </select>
          </div>
          <span v-if="errors.status" class="error-message">
            <Icon name="alert-circle" :size="14" />
            {{ errors.status }}
          </span>
        </div>
      </div>
      <div class="modal-footer">
        <Button variant="secondary" icon="x" @click="$emit('close')">
          Cancelar
        </Button>
        <Button :loading="loading" icon="save" @click="handleSave">
          Salvar
        </Button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'

const props = defineProps({
  scheduleId: {
    type: [String, Number],
    required: true
  },
  currentStatus: {
    type: String,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['close', 'saved'])

const selectedStatus = ref(props.currentStatus)
const errors = ref({})

const handleSave = () => {
  errors.value = {}
  
  if (!selectedStatus.value) {
    errors.value.status = 'Status é obrigatório'
    return
  }

  if (selectedStatus.value === props.currentStatus) {
    emit('close')
    return
  }

  emit('saved', selectedStatus.value)
}

onMounted(() => {
  selectedStatus.value = props.currentStatus
})
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
}

.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px;
  border-bottom: 1px solid var(--color-gray-200);
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--color-gray-900);
  margin: 0;
}

.modal-close {
  background: none;
  border: none;
  cursor: pointer;
  color: var(--color-gray-400);
  padding: 4px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
}

.modal-close:hover {
  background: var(--color-gray-100);
  color: var(--color-gray-600);
}

.modal-body {
  padding: 24px;
  flex: 1;
  overflow-y: auto;
}

.modal-footer {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
  padding: 20px 24px;
  border-top: 1px solid var(--color-gray-200);
}

.form-group {
  margin-bottom: 0;
}

.input-label {
  display: block;
  margin-bottom: 8px;
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--color-gray-700);
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
  pointer-events: none;
}

.input {
  width: 100%;
  padding: 12px 14px 12px 44px;
  border: 2px solid var(--color-gray-200);
  border-radius: 8px;
  font-size: 0.9375rem;
  transition: all 0.2s ease;
}

.input:focus {
  outline: none;
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.input-error {
  border-color: var(--color-error);
}

.error-message {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-top: 6px;
  font-size: 0.8125rem;
  color: var(--color-error);
  font-weight: 500;
}
</style>


