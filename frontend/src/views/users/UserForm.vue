<template>
  <AppLayout>
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Editar Usuário' : 'Novo Usuário' }}</h1>
    </div>

    <Card>
      <form @submit.prevent="handleSubmit" class="form-grid">
        <Input
          id="name"
          label="Nome"
          v-model="form.name"
          :error="errors.name"
          icon="user"
          required
        />

        <Input
          id="email"
          label="Email"
          type="email"
          v-model="form.email"
          :error="errors.email"
          icon="mail"
          required
        />

        <div class="input-group">
          <label class="input-label" for="password">
            Senha
            <span v-if="!isEdit" style="color: var(--color-error)">*</span>
            <span v-else class="input-hint">(Deixe em branco para manter a atual)</span>
          </label>
          <div class="input-wrapper">
            <Icon name="lock" :size="18" class="input-icon" />
            <input
              id="password"
              :type="showPassword ? 'text' : 'password'"
              class="input input-with-icon"
              :class="{ 'input-error': errors.password }"
              v-model="form.password"
              :placeholder="isEdit ? 'Nova senha (opcional)' : 'Digite a senha'"
              :required="!isEdit"
            />
            <button
              type="button"
              class="password-toggle"
              @click="showPassword = !showPassword"
              tabindex="-1"
            >
              <Icon :name="showPassword ? 'eye-off' : 'eye'" :size="18" />
            </button>
          </div>
          <span v-if="errors.password" class="error-message">
            <Icon name="alert-circle" :size="14" />
            {{ errors.password }}
          </span>
        </div>

        <div class="input-group">
          <label class="input-label" for="role">
            Perfil
            <span style="color: var(--color-error)">*</span>
          </label>
          <div class="input-wrapper">
            <Icon name="shield" :size="18" class="input-icon" />
            <select
              id="role"
              class="input input-with-icon"
              :class="{ 'input-error': errors.role }"
              v-model="form.role"
              required
            >
              <option value="">Selecione um perfil</option>
              <option value="ADMIN">Administrador</option>
              <option value="GESTOR">Gestor</option>
              <option value="FUNCIONARIO">Funcionário</option>
            </select>
          </div>
          <span v-if="errors.role" class="error-message">
            <Icon name="alert-circle" :size="14" />
            {{ errors.role }}
          </span>
        </div>

        <div class="input-group">
          <label class="input-label">
            <input type="checkbox" v-model="form.active" />
            <span style="margin-left: 8px">Usuário ativo</span>
          </label>
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
import { reactive, computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useToast } from '@/composables/useToast'
import { useFormValidation } from '@/composables/useFormValidation'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Input from '@/components/common/Input.vue'
import Icon from '@/components/common/Icon.vue'
import { useUsers } from '@/composables/useUsers'

const route = useRoute()
const router = useRouter()
const toast = useToast()
const { user, loading, fetchUser, createUser, updateUser } = useUsers()

const isEdit = computed(() => !!route.params.id)
const showPassword = ref(false)

const form = reactive({
  name: '',
  email: '',
  password: '',
  role: '',
  active: true
})

// Schema de validação
const getValidationSchema = () => ({
  name: ['required', 'minLength:2'],
  email: ['required', 'email'],
  password: isEdit.value 
    ? ['password'] // Opcional no edit, mas se preenchido deve ser válido
    : ['required', 'password'],
  role: ['required', 'select']
})

const { errors, validate, clearErrors } = useFormValidation(getValidationSchema())

const handleSubmit = async () => {
  clearErrors()

  // Atualizar schema se necessário
  const schema = { ...getValidationSchema() }
  
  // Validação customizada para senha no modo edição
  if (isEdit.value && !form.password) {
    // Senha opcional no edit, remover do schema
    delete schema.password
  }

  if (!validate(form, schema)) {
    return
  }

  try {
    const data = { ...form }
    if (isEdit.value && !data.password) {
      delete data.password
    }

    if (isEdit.value) {
      await updateUser(route.params.id, data)
      toast.success('Usuário atualizado com sucesso!')
    } else {
      await createUser(data)
      toast.success('Usuário criado com sucesso!')
    }
    router.push('/users')
  } catch (err) {
    console.error('Erro ao salvar usuário:', err)
    toast.error('Erro ao salvar usuário. Tente novamente.')
  }
}

onMounted(async () => {
  if (isEdit.value) {
    await fetchUser(route.params.id)
    if (user.value) {
      form.name = user.value.name || ''
      form.email = user.value.email || ''
      form.role = user.value.role || ''
      form.active = user.value.active !== undefined ? user.value.active : true
    }
  }
})
</script>

<style scoped>
.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: var(--spacing-lg);
  max-width: 900px;
}

.form-actions {
  grid-column: 1 / -1;
  display: flex;
  gap: var(--spacing-md);
  justify-content: flex-end;
  margin-top: var(--spacing-xl);
}

.password-toggle {
  position: absolute;
  right: 16px;
  background: none;
  border: none;
  cursor: pointer;
  color: var(--color-gray-400);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 4px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.password-toggle:hover {
  color: var(--color-gray-600);
  background: var(--color-gray-100);
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


