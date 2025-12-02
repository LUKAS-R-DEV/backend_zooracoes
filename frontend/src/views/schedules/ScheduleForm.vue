<template>
  <AppLayout>
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Editar Agendamento' : 'Novo Agendamento' }}</h1>
    </div>

    <Card>
      <form @submit.prevent="handleSubmit" class="form">
        <div class="form-grid">
          <Input
            id="dateTime"
            label="Data e Hora"
            type="datetime-local"
            v-model="form.dateTime"
            :error="errors.dateTime"
            icon="calendar-clock"
            required
          />

          <div class="input-group">
            <label class="input-label" for="petId">
              <span class="label-text">
                Pet
                <span class="required-asterisk">*</span>
              </span>
            </label>
            <div class="input-wrapper">
              <Icon name="dog" :size="18" class="input-icon" />
              <select id="petId" class="input input-with-icon" v-model="form.petId" required>
                <option value="">Selecione um pet...</option>
                <option v-for="pet in pets" :key="pet.id" :value="String(pet.id)">
                  {{ pet.name }} - {{ pet.tutorName }}
                </option>
              </select>
            </div>
            <span v-if="errors.petId" class="error-message">
              <Icon name="alert-circle" :size="14" />
              {{ errors.petId }}
            </span>
          </div>

          <div class="input-group">
            <label class="input-label" for="serviceId">
              <span class="label-text">
                Serviço
                <span class="required-asterisk">*</span>
              </span>
            </label>
            <div class="input-wrapper">
              <Icon name="stethoscope" :size="18" class="input-icon" />
              <select id="serviceId" class="input input-with-icon" v-model="form.serviceId" required>
                <option value="">Selecione um serviço...</option>
                <option v-for="service in services" :key="service.id" :value="String(service.id)">
                  {{ service.name }} - {{ formatCurrency(service.price) }}
                </option>
              </select>
            </div>
            <span v-if="errors.serviceId" class="error-message">
              <Icon name="alert-circle" :size="14" />
              {{ errors.serviceId }}
            </span>
          </div>

          <div v-if="isEdit" class="input-group">
            <label class="input-label" for="status">
              <span class="label-text">
                Status
                <span class="required-asterisk">*</span>
              </span>
            </label>
            <div class="input-wrapper">
              <Icon name="circle" :size="18" class="input-icon" />
              <select id="status" class="input input-with-icon" v-model="form.status" required>
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

          <div class="input-group input-group-full">
            <label class="input-label" for="notes">
              <span class="label-text">Observações</span>
            </label>
            <div class="input-wrapper">
              <Icon name="file-text" :size="18" class="input-icon" />
              <textarea
                id="notes"
                class="input input-with-icon"
                v-model="form.notes"
                rows="3"
                placeholder="Adicione observações sobre o agendamento..."
              ></textarea>
            </div>
          </div>
        </div>

        <div class="form-actions">
          <Button variant="secondary" icon="x" @click="$router.back()">
            Cancelar
          </Button>
          <Button type="submit" :loading="loading" icon="save">
            {{ loading ? 'Salvando...' : 'Salvar' }}
          </Button>
        </div>
      </form>
    </Card>
  </AppLayout>
</template>

<script setup>
import { reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Input from '@/components/common/Input.vue'
import Icon from '@/components/common/Icon.vue'
import { useSchedules } from '@/composables/useSchedules'
import { usePets } from '@/composables/usePets'
import { useServices } from '@/composables/useServices'
import { useToast } from '@/composables/useToast'
import { useFormValidation } from '@/composables/useFormValidation'
import { formatters } from '@/utils/formatters'

const route = useRoute()
const router = useRouter()
const toast = useToast()
const { schedule, loading, fetchSchedule, createSchedule, updateSchedule } = useSchedules()
const { pets, fetchPets } = usePets()
const { services, fetchServices } = useServices()

const isEdit = computed(() => !!route.params.id)

const form = reactive({
  dateTime: '',
  petId: '',
  serviceId: '',
  status: 'PENDING',
  notes: ''
})

const getValidationSchema = () => {
  const schema = {
    dateTime: ['required', 'date', 'dateNotPast'],
    petId: ['required', 'select'],
    serviceId: ['required', 'select']
  }
  // Status só é obrigatório na edição
  if (isEdit.value) {
    schema.status = ['required']
  }
  return schema
}

const { errors, validate, clearErrors } = useFormValidation(getValidationSchema())

const formatCurrency = (value) => {
  return formatters.currency(value)
}

const handleSubmit = async () => {
  clearErrors()

  if (!validate(form, getValidationSchema())) {
    return
  }

  try {
    const data = {
      petId: parseInt(form.petId),
      serviceId: parseInt(form.serviceId),
      dateTime: new Date(form.dateTime).toISOString(),
      notes: form.notes
    }

    // Só envia status na edição (na criação o backend define como PENDING)
    if (isEdit.value) {
      data.status = form.status
      await updateSchedule(route.params.id, data)
      toast.success('Agendamento atualizado com sucesso!')
    } else {
      await createSchedule(data)
      toast.success('Agendamento criado com sucesso!')
    }
    router.push('/schedules')
  } catch (err) {
    console.error('Erro ao salvar agendamento:', err)
    toast.error('Erro ao salvar agendamento. Tente novamente.')
  }
}

onMounted(async () => {
  await fetchPets()
  await fetchServices()
  
  if (isEdit.value) {
    await fetchSchedule(route.params.id)
    if (schedule.value) {
      form.dateTime = schedule.value.dateTime ? new Date(schedule.value.dateTime).toISOString().slice(0, 16) : ''
      form.petId = schedule.value.petId ? String(schedule.value.petId) : ''
      form.serviceId = schedule.value.serviceId ? String(schedule.value.serviceId) : ''
      form.status = schedule.value.status || 'PENDING'
      form.notes = schedule.value.notes || ''
    }
  }
})
</script>

<style scoped>
.form {
  max-width: 600px;
}

.form {
  max-width: 800px;
  margin: 0 auto;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: var(--spacing-lg);
}

.input-group-full {
  grid-column: 1 / -1;
}

.form-actions {
  display: flex;
  gap: var(--spacing-md);
  justify-content: flex-end;
  margin-top: var(--spacing-2xl);
  padding-top: var(--spacing-xl);
  border-top: 2px solid var(--color-gray-100);
}

@media (max-width: 768px) {
  .form-grid {
    grid-template-columns: 1fr;
  }
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

