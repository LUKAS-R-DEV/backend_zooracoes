<template>
  <AppLayout>
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Editar Serviço' : 'Novo Serviço' }}</h1>
    </div>

    <Card>
      <form @submit.prevent="handleSubmit" class="form">
        <div class="form-grid">
          <Input
            id="name"
            label="Nome do Serviço"
            v-model="form.name"
            :error="errors.name"
            icon="stethoscope"
            placeholder="Ex: Consulta Veterinária"
            required
          />

          <div class="input-group input-group-full">
            <label class="input-label" for="description">
              <span class="label-text">Descrição</span>
            </label>
            <div class="input-wrapper">
              <Icon name="file-text" :size="18" class="input-icon" />
              <textarea
                id="description"
                class="input input-with-icon"
                v-model="form.description"
                rows="3"
                placeholder="Descreva o serviço oferecido..."
              ></textarea>
            </div>
          </div>

          <Input
            id="price"
            label="Preço (R$)"
            type="number"
            step="0.01"
            v-model="form.price"
            :error="errors.price"
            icon="dollar-sign"
            placeholder="0.00"
            required
          />

          <Input
            id="duration"
            label="Duração (minutos)"
            type="number"
            v-model="form.duration"
            :error="errors.duration"
            icon="clock"
            placeholder="30"
          />
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
import { useServices } from '@/composables/useServices'
import { useToast } from '@/composables/useToast'
import { useFormValidation } from '@/composables/useFormValidation'

const route = useRoute()
const router = useRouter()
const toast = useToast()
const { service, loading, fetchService, createService, updateService } = useServices()

const isEdit = computed(() => !!route.params.id)

const form = reactive({
  name: '',
  description: '',
  price: '',
  duration: ''
})

const validationSchema = {
  name: ['required', 'minLength:2'],
  price: ['required', 'positiveNumber', 'minValue:0'],
  duration: ['integer', 'positiveNumber', 'minValue:1']
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
      price: parseFloat(form.price),
      duration: form.duration ? parseInt(form.duration) : null
    }

    if (isEdit.value) {
      await updateService(route.params.id, data)
      toast.success('Serviço atualizado com sucesso!')
    } else {
      await createService(data)
      toast.success('Serviço criado com sucesso!')
    }
    router.push('/services')
  } catch (err) {
    console.error('Erro ao salvar serviço:', err)
    toast.error('Erro ao salvar serviço. Tente novamente.')
  }
}

onMounted(async () => {
  if (isEdit.value) {
    await fetchService(route.params.id)
    if (service.value) {
      form.name = service.value.name || ''
      form.description = service.value.description || ''
      form.price = service.value.price || ''
      form.duration = service.value.duration || ''
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

