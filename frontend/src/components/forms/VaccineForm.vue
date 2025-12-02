<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal">
      <div class="modal-header">
        <h3 class="modal-title">{{ isEdit ? 'Editar Vacina' : 'Nova Vacina' }}</h3>
        <button class="modal-close" @click="$emit('close')">
          <Icon name="x" :size="20" />
        </button>
      </div>
      <div class="modal-body">
        <form @submit.prevent="handleSubmit" class="modal-form">
          <div class="form-grid">
            <Input
              id="name"
              label="Nome da Vacina"
              v-model="form.name"
              :error="errors.name"
              icon="syringe"
              placeholder="Ex: V8, V10, Antirrábica"
              required
            />

            <Input
              id="applicationDate"
              label="Data de Aplicação"
              type="date"
              v-model="form.applicationDate"
              :error="errors.applicationDate"
              icon="calendar"
              required
            />

            <Input
              id="nextDoseDate"
              label="Data da Próxima Dose"
              type="date"
              v-model="form.nextDoseDate"
              :error="errors.nextDoseDate"
              icon="calendar-days"
            />

            <Input
              id="veterinarian"
              label="Veterinário"
              v-model="form.veterinarian"
              :error="errors.veterinarian"
              icon="user"
              placeholder="Nome do veterinário"
            />

            <Input
              id="batch"
              label="Lote"
              v-model="form.batch"
              :error="errors.batch"
              icon="hash"
              placeholder="Número do lote"
            />
          </div>

          <div class="modal-footer">
            <Button variant="secondary" icon="x" @click="$emit('close')">
              Cancelar
            </Button>
            <Button type="submit" :loading="loading" icon="save">
              {{ loading ? 'Salvando...' : 'Salvar' }}
            </Button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, computed, watch } from 'vue'
import Input from '@/components/common/Input.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import { useVaccines } from '@/composables/useVaccines'
import { useToast } from '@/composables/useToast'
import { useFormValidation } from '@/composables/useFormValidation'

const toast = useToast()

const props = defineProps({
  petId: {
    type: [String, Number],
    required: true
  },
  vaccineId: {
    type: [String, Number],
    default: null
  }
})

const emit = defineEmits(['close', 'saved'])

const { vaccine, loading, fetchVaccine, createVaccine, updateVaccine } = useVaccines()

const isEdit = computed(() => !!props.vaccineId)

const form = reactive({
  name: '',
  applicationDate: '',
  nextDoseDate: '',
  veterinarian: '',
  batch: ''
})

const validationSchema = {
  name: ['required', 'minLength:2'],
  applicationDate: ['required', 'date', 'dateNotFuture'],
  nextDoseDate: ['date', (value) => {
    if (!value) return true
    if (!form.applicationDate) return true
    const nextDate = new Date(value)
    const appDate = new Date(form.applicationDate)
    if (nextDate <= appDate) {
      return 'Data da próxima dose deve ser posterior à data de aplicação'
    }
    return true
  }]
}

const { errors, validate, clearErrors } = useFormValidation(validationSchema)

const handleSubmit = async () => {
  clearErrors()

  if (!validate(form)) {
    return
  }

  try {
    const data = {
      ...form,
      petId: parseInt(props.petId)
    }

    if (isEdit.value) {
      await updateVaccine(props.vaccineId, data)
    } else {
      await createVaccine(data)
    }
    emit('saved')
    emit('close')
    toast.success(isEdit.value ? 'Vacina atualizada com sucesso!' : 'Vacina registrada com sucesso!')
  } catch (err) {
    console.error('Erro ao salvar vacina:', err)
    toast.error('Erro ao salvar vacina. Tente novamente.')
  }
}

watch(() => props.vaccineId, async (newId) => {
  if (newId) {
    await fetchVaccine(newId)
    if (vaccine.value) {
      form.name = vaccine.value.name || ''
      form.applicationDate = vaccine.value.applicationDate ? vaccine.value.applicationDate.split('T')[0] : ''
      form.nextDoseDate = vaccine.value.nextDoseDate ? vaccine.value.nextDoseDate.split('T')[0] : ''
      form.veterinarian = vaccine.value.veterinarian || ''
      form.batch = vaccine.value.batch || ''
    }
  } else {
    // Limpar formulário quando não há ID (modo criar)
    form.name = ''
    form.applicationDate = ''
    form.nextDoseDate = ''
    form.veterinarian = ''
    form.batch = ''
  }
}, { immediate: true })
</script>

<style scoped>
.modal-form {
  width: 100%;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: var(--spacing-lg);
}

.input-group-full {
  grid-column: 1 / -1;
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

@media (max-width: 640px) {
  .form-grid {
    grid-template-columns: 1fr;
  }
}
</style>

