<template>
  <AppLayout>
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Editar Pet' : 'Novo Pet' }}</h1>
    </div>

    <Card>
      <form @submit.prevent="handleSubmit" class="form">
        <div class="form-grid">
          <Input
            id="name"
            label="Nome do Pet"
            v-model="form.name"
            :error="errors.name"
            icon="dog"
            placeholder="Digite o nome do pet"
            required
          />

          <div class="input-group">
            <label class="input-label" for="species">
              <span class="label-text">
                Espécie
                <span class="required-asterisk">*</span>
              </span>
            </label>
            <div class="input-wrapper">
              <Icon name="paw-print" :size="18" class="input-icon" />
              <select id="species" class="input input-with-icon" v-model="form.species" required>
                <option value="">Selecione a espécie...</option>
                <option v-for="specie in species" :key="specie" :value="specie">
                  {{ specie }}
                </option>
              </select>
            </div>
            <span v-if="errors.species" class="error-message">
              <Icon name="alert-circle" :size="14" />
              {{ errors.species }}
            </span>
          </div>

          <Input
            id="breed"
            label="Raça"
            v-model="form.breed"
            :error="errors.breed"
            icon="tag"
            placeholder="Ex: Labrador, Persa, etc."
          />

          <Input
            id="weight"
            label="Peso (kg)"
            type="number"
            step="0.1"
            v-model="form.weight"
            :error="errors.weight"
            icon="scale"
            placeholder="0.0"
          />

          <Input
            id="birthDate"
            label="Data de Nascimento"
            type="date"
            v-model="form.birthDate"
            :error="errors.birthDate"
            icon="calendar"
          />

          <div class="input-group">
            <label class="input-label" for="tutorId">
              <span class="label-text">
                Tutor
                <span class="required-asterisk">*</span>
              </span>
            </label>
            <div class="input-wrapper">
              <Icon name="user" :size="18" class="input-icon" />
              <select id="tutorId" class="input input-with-icon" v-model="form.tutorId" required>
                <option value="">Selecione um tutor...</option>
                <option v-for="tutor in tutors" :key="tutor.id" :value="String(tutor.id)">
                  {{ tutor.name }}
                </option>
              </select>
            </div>
            <span v-if="errors.tutorId" class="error-message">
              <Icon name="alert-circle" :size="14" />
              {{ errors.tutorId }}
            </span>
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
import { usePets } from '@/composables/usePets'
import { useTutors } from '@/composables/useTutors'
import { useToast } from '@/composables/useToast'
import { useFormValidation } from '@/composables/useFormValidation'
import { PET_SPECIES } from '@/utils/constants'

const route = useRoute()
const router = useRouter()
const toast = useToast()
const { pet, loading, fetchPet, createPet, updatePet } = usePets()
const { tutors, fetchTutors } = useTutors()

const isEdit = computed(() => !!route.params.id)
const species = PET_SPECIES

const form = reactive({
  name: '',
  species: '',
  breed: '',
  weight: '',
  birthDate: '',
  tutorId: ''
})

const validationSchema = {
  name: ['required', 'minLength:2'],
  species: ['required', 'select'],
  tutorId: ['required', 'select'],
  weight: ['positiveNumber'],
  birthDate: ['date', 'dateNotFuture']
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
      weight: form.weight ? parseFloat(form.weight) : null,
      tutorId: parseInt(form.tutorId)
    }

    if (isEdit.value) {
      await updatePet(route.params.id, data)
      toast.success('Pet atualizado com sucesso!')
    } else {
      await createPet(data)
      toast.success('Pet criado com sucesso!')
    }
    router.push('/pets')
  } catch (err) {
    console.error('Erro ao salvar pet:', err)
    toast.error('Erro ao salvar pet. Tente novamente.')
  }
}

onMounted(async () => {
  await fetchTutors()
  
  if (isEdit.value) {
    await fetchPet(route.params.id)
    if (pet.value) {
      form.name = pet.value.name || ''
      form.species = pet.value.species || ''
      form.breed = pet.value.breed || ''
      form.weight = pet.value.weight || ''
      form.birthDate = pet.value.birthDate ? pet.value.birthDate.split('T')[0] : ''
      // Converter tutorId para string para o select funcionar corretamente
      form.tutorId = pet.value.tutorId ? String(pet.value.tutorId) : ''
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

