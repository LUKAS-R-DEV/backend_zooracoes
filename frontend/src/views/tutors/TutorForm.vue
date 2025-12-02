<template>
  <AppLayout>
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Editar Tutor' : 'Novo Tutor' }}</h1>
    </div>

    <Card>
      <form @submit.prevent="handleSubmit" class="form">
        <div class="form-grid">
          <Input
            id="name"
            label="Nome Completo"
            v-model="form.name"
            :error="errors.name"
            icon="user"
            placeholder="Digite o nome completo"
            required
          />

          <Input
            id="email"
            label="Email"
            type="email"
            v-model="form.email"
            :error="errors.email"
            icon="mail"
            placeholder="exemplo@email.com"
            required
          />

          <Input
            id="phone"
            label="Telefone"
            v-model="form.phone"
            :error="errors.phone"
            icon="phone"
            placeholder="(00) 00000-0000"
            mask="phone"
          />

          <div class="input-group input-group-full">
            <label class="input-label" for="address">
              <span class="label-text">
                Endereço
              </span>
            </label>
            <div class="input-wrapper">
              <Icon name="map-pin" :size="18" class="input-icon" />
              <textarea
                id="address"
                class="input input-with-icon"
                v-model="form.address"
                rows="3"
                placeholder="Digite o endereço completo"
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
import { useTutors } from '@/composables/useTutors'
import { useToast } from '@/composables/useToast'
import { useFormValidation } from '@/composables/useFormValidation'

const toast = useToast()

const route = useRoute()
const router = useRouter()
const { tutor, loading, fetchTutor, createTutor, updateTutor } = useTutors()

const isEdit = computed(() => !!route.params.id)

const form = reactive({
  name: '',
  email: '',
  phone: '',
  address: ''
})

const validationSchema = {
  name: ['required', 'minLength:2'],
  email: ['required', 'email'],
  phone: ['phone'] // Opcional mas se preenchido deve ser válido
}

const { errors, validate, clearErrors } = useFormValidation(validationSchema)

const handleSubmit = async () => {
  clearErrors()

  if (!validate(form)) {
    return
  }

  try {
    if (isEdit.value) {
      await updateTutor(route.params.id, form)
    } else {
      await createTutor(form)
    }
    router.push('/tutors')
    toast.success(isEdit.value ? 'Tutor atualizado com sucesso!' : 'Tutor criado com sucesso!')
  } catch (err) {
    console.error('Erro ao salvar tutor:', err)
    toast.error('Erro ao salvar tutor. Tente novamente.')
  }
}

onMounted(async () => {
  if (isEdit.value) {
    await fetchTutor(route.params.id)
    if (tutor.value) {
      form.name = tutor.value.name || ''
      form.email = tutor.value.email || ''
      form.phone = tutor.value.phone || ''
      form.address = tutor.value.address || ''
    }
  }
})
</script>

<style scoped>
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

@media (max-width: 768px) {
  .form-grid {
    grid-template-columns: 1fr;
  }
}
</style>

