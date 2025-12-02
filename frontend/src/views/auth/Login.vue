<template>
  <div class="login-container">
    <div class="login-background">
      <div class="login-shapes">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
        <div class="shape shape-3"></div>
      </div>
    </div>
    
    <div class="login-card">
      <div class="login-left">
        <div class="login-brand">
          <div class="brand-logo">
            <Icon name="heart-pulse" :size="48" color="white" />
          </div>
          <h1 class="brand-title">ZooRacoes</h1>
          <p class="brand-subtitle">Gestão completa para clínicas veterinárias</p>
        </div>
        <div class="login-features">
          <div class="feature-item">
            <Icon name="check-circle" :size="20" color="white" />
            <span>Gestão de pacientes</span>
          </div>
          <div class="feature-item">
            <Icon name="check-circle" :size="20" color="white" />
            <span>Agendamentos inteligentes</span>
          </div>
          <div class="feature-item">
            <Icon name="check-circle" :size="20" color="white" />
            <span>Controle de vacinas</span>
          </div>
        </div>
        <div class="login-image">
          <Icon name="dog" :size="140" color="white" class="image-icon" />
        </div>
      </div>
      
      <div class="login-right">
        <div class="login-form-container">
          <div class="login-header">
            <h2 class="login-title">Bem-vindo de volta</h2>
            <p class="login-subtitle">Entre com suas credenciais para continuar</p>
          </div>
          
          <form @submit.prevent="handleLogin" class="login-form">
            <Input
              id="email"
              label="Email"
              type="email"
              v-model="form.email"
              :error="errors.email"
              icon="mail"
              placeholder="seu@email.com"
              required
            />

            <div class="input-group">
              <label class="input-label" for="password">
                Senha
              </label>
              <div class="password-input-wrapper">
                <Icon name="lock" :size="18" class="password-icon" />
                <input
                  id="password"
                  :type="showPassword ? 'text' : 'password'"
                  class="input password-input"
                  :class="{ 'input-error': errors.password }"
                  v-model="form.password"
                  placeholder="Digite sua senha"
                  required
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

            <div class="login-options">
              <label class="remember-me">
                <input type="checkbox" v-model="rememberMe" />
                <span>Lembrar-me</span>
              </label>
            </div>

            <Button type="submit" :loading="loading" icon="log-in" size="lg" style="width: 100%">
              {{ loading ? 'Entrando...' : 'Entrar' }}
            </Button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useAuth } from '@/composables/useAuth'
import { useRouter } from 'vue-router'
import { useToast } from '@/composables/useToast'
import Button from '@/components/common/Button.vue'
import Input from '@/components/common/Input.vue'
import Icon from '@/components/common/Icon.vue'

const router = useRouter()
const { login } = useAuth()
const toast = useToast()

const form = reactive({
  email: '',
  password: ''
})

const errors = reactive({
  email: '',
  password: ''
})

const showPassword = ref(false)
const loading = ref(false)
const rememberMe = ref(false)

const handleLogin = async () => {
  errors.email = ''
  errors.password = ''

  if (!form.email) {
    errors.email = 'Email é obrigatório'
    return
  }

  if (!form.email.includes('@')) {
    errors.email = 'Email inválido'
    return
  }

  if (!form.password) {
    errors.password = 'Senha é obrigatória'
    return
  }

  if (form.password.length < 6) {
    errors.password = 'Senha deve ter no mínimo 6 caracteres'
    return
  }

  loading.value = true
  const result = await login(form.email, form.password)
  loading.value = false

  if (!result.success) {
    toast.error(result.error || 'Erro ao fazer login. Verifique suas credenciais.')
  } else {
    toast.success('Login realizado com sucesso!')
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f0fdf4 0%, #dcfce7 50%, #f0fdf4 100%);
  padding: var(--spacing-lg);
  position: relative;
  overflow: hidden;
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 0;
}

.login-shapes {
  position: relative;
  width: 100%;
  height: 100%;
}

.shape {
  position: absolute;
  border-radius: 50%;
  background: rgba(34, 197, 94, 0.1);
  animation: float 20s infinite ease-in-out;
}

.shape-1 {
  width: 300px;
  height: 300px;
  top: -100px;
  left: -100px;
  animation-delay: 0s;
}

.shape-2 {
  width: 200px;
  height: 200px;
  bottom: -50px;
  right: -50px;
  animation-delay: 5s;
}

.shape-3 {
  width: 150px;
  height: 150px;
  top: 50%;
  right: 10%;
  animation-delay: 10s;
}

@keyframes float {
  0%, 100% {
    transform: translate(0, 0) rotate(0deg);
  }
  33% {
    transform: translate(30px, -30px) rotate(120deg);
  }
  66% {
    transform: translate(-20px, 20px) rotate(240deg);
  }
}

.login-card {
  background-color: var(--color-white);
  border-radius: 24px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
  display: flex;
  max-width: 1100px;
  width: 100%;
  overflow: hidden;
  position: relative;
  z-index: 1;
  animation: slideUp 0.6s ease;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  padding: var(--spacing-2xl);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  color: var(--color-white);
  position: relative;
  overflow: hidden;
}

.login-left::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.1) 0%, transparent 70%);
  animation: pulse 8s infinite ease-in-out;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.5;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.8;
  }
}

.login-brand {
  position: relative;
  z-index: 1;
}

.brand-logo {
  width: 80px;
  height: 80px;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: var(--spacing-lg);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.brand-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: var(--spacing-sm);
  letter-spacing: -0.02em;
}

.brand-subtitle {
  font-size: 1.125rem;
  opacity: 0.95;
  line-height: 1.6;
}

.login-features {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
  margin: var(--spacing-xl) 0;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  font-size: 0.9375rem;
  opacity: 0.95;
}

.login-image {
  position: relative;
  z-index: 1;
  margin-top: var(--spacing-xl);
  text-align: center;
}

.image-icon {
  opacity: 0.9;
  filter: drop-shadow(0 8px 16px rgba(0, 0, 0, 0.3));
  animation: bounce 3s infinite ease-in-out;
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.login-right {
  flex: 1;
  padding: var(--spacing-2xl);
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--color-white);
}

.login-form-container {
  width: 100%;
  max-width: 420px;
}

.login-header {
  text-align: center;
  margin-bottom: var(--spacing-2xl);
}

.login-title {
  font-size: 2rem;
  font-weight: 700;
  color: var(--color-gray-900);
  margin-bottom: var(--spacing-sm);
  letter-spacing: -0.02em;
}

.login-subtitle {
  color: var(--color-gray-600);
  font-size: 0.9375rem;
  line-height: 1.5;
}

.login-form {
  width: 100%;
}

.input-group {
  margin-bottom: var(--spacing-lg);
}

.input-label {
  display: block;
  margin-bottom: var(--spacing-xs);
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--color-gray-700);
}

.password-input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.password-icon {
  position: absolute;
  left: 16px;
  color: var(--color-gray-400);
  z-index: 1;
  pointer-events: none;
}

.password-input {
  width: 100%;
  padding: 14px 16px;
  padding-left: 48px;
  padding-right: 48px;
  border: 2px solid var(--color-gray-200);
  border-radius: 12px;
  font-size: 0.9375rem;
  transition: all 0.2s ease;
  background-color: var(--color-white);
  color: var(--color-gray-900);
}

.password-input:focus {
  border-color: var(--color-primary);
  outline: 2px solid var(--color-primary-lighter);
  outline-offset: 0;
  box-shadow: 0 0 0 3px rgba(34, 197, 94, 0.1);
}

.password-input.input-error {
  border-color: var(--color-error);
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

.login-options {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: var(--spacing-xl);
}

.remember-me {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.875rem;
  color: var(--color-gray-700);
  cursor: pointer;
}

.remember-me input[type="checkbox"] {
  width: 18px;
  height: 18px;
  cursor: pointer;
  accent-color: var(--color-primary);
}

@media (max-width: 968px) {
  .login-card {
    flex-direction: column;
    max-width: 500px;
  }
  
  .login-left {
    min-height: 300px;
    padding: var(--spacing-xl);
  }
  
  .brand-title {
    font-size: 2rem;
  }
  
  .login-features {
    margin: var(--spacing-lg) 0;
  }
  
  .image-icon {
    width: 100px;
    height: 100px;
  }
}

@media (max-width: 640px) {
  .login-container {
    padding: var(--spacing-md);
  }
  
  .login-card {
    border-radius: 16px;
  }
  
  .login-left,
  .login-right {
    padding: var(--spacing-lg);
  }
  
  .brand-title {
    font-size: 1.75rem;
  }
  
  .login-title {
    font-size: 1.5rem;
  }
}
</style>

