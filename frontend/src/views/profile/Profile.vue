<template>
  <AppLayout>
    <div class="page-header">
      <div>
        <h1 class="page-title">Meu Perfil</h1>
        <p class="page-subtitle">Visualize e gerencie suas informações</p>
      </div>
      <div class="page-actions">
        <Button icon="arrow-left" @click="$router.push('/dashboard')">
          Voltar
        </Button>
      </div>
    </div>

    <div v-if="loading" class="loading">
      <Loading />
    </div>

    <div v-else-if="error" class="error-message" style="display: flex; align-items: center; gap: 8px">
      <Icon name="alert-circle" :size="20" />
      {{ error }}
    </div>

    <div v-else-if="!user || !user.name" class="empty-state" style="padding: var(--spacing-2xl); text-align: center;">
      <Icon name="user-x" :size="48" class="empty-state-icon" />
      <div class="empty-state-text">Usuário não encontrado</div>
      <Button @click="fetchUser" style="margin-top: var(--spacing-md)">
        Tentar novamente
      </Button>
    </div>

    <div v-else class="profile-container">
      <div class="grid grid-cols-2" style="gap: var(--spacing-lg)">
        <Card>
          <div class="card-header">
            <h3 class="card-title">Informações Pessoais</h3>
          </div>
          <div class="profile-info">
            <div class="profile-avatar">
              <span>{{ userInitials }}</span>
            </div>
            <div class="profile-details">
              <div class="profile-detail-item">
                <div class="detail-label">
                  <Icon name="user" :size="18" />
                  Nome
                </div>
                <div class="detail-value">{{ user.name }}</div>
              </div>
              
              <div class="profile-detail-item">
                <div class="detail-label">
                  <Icon name="mail" :size="18" />
                  Email
                </div>
                <div class="detail-value">{{ user.email }}</div>
              </div>
              
              <div class="profile-detail-item">
                <div class="detail-label">
                  <Icon name="shield" :size="18" />
                  Perfil
                </div>
                <div class="detail-value">
                  <span class="role-badge" :class="`role-${user.role.toLowerCase()}`">
                    {{ userRoleLabel }}
                  </span>
                </div>
              </div>
              
              <div class="profile-detail-item">
                <div class="detail-label">
                  <Icon name="circle-check" :size="18" />
                  Status
                </div>
                <div class="detail-value">
                  <span class="status-badge" :class="user.active ? 'status-active' : 'status-inactive'">
                    {{ user.active ? 'Ativo' : 'Inativo' }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </Card>

        <Card>
          <div class="card-header">
            <h3 class="card-title">Estatísticas</h3>
          </div>
          <div class="profile-stats">
            <div class="stat-item">
              <Icon name="calendar" :size="24" class="stat-icon" />
              <div class="stat-content">
                <div class="stat-label">Último Acesso</div>
                <div class="stat-value">{{ formatDate(user?.lastLogin) || 'N/A' }}</div>
              </div>
            </div>
            
            <div class="stat-item">
              <Icon name="clock" :size="24" class="stat-icon" />
              <div class="stat-content">
                <div class="stat-label">Sessão Ativa</div>
                <div class="stat-value">{{ user?.activeSession ? 'Sim' : 'Não' }}</div>
              </div>
            </div>
          </div>
        </Card>
      </div>
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import { useAuth } from '@/composables/useAuth'
import { USER_ROLE_LABELS } from '@/utils/constants'
import { formatters } from '@/utils/formatters'

const { user, fetchUser } = useAuth()
const loading = ref(false)
const error = ref(null)

const userInitials = computed(() => {
  if (!user.value?.name) return 'U'
  const names = user.value.name.split(' ')
  if (names.length >= 2) {
    return (names[0][0] + names[names.length - 1][0]).toUpperCase()
  }
  return user.value.name.substring(0, 2).toUpperCase()
})

const userRoleLabel = computed(() => {
  if (!user.value?.role) return ''
  return USER_ROLE_LABELS[user.value.role] || user.value.role
})

const formatDate = (dateString) => {
  if (!dateString) return 'N/A'
  return formatters.date(dateString)
}

onMounted(async () => {
  loading.value = true
  error.value = null
  try {
    // Sempre buscar para garantir dados atualizados
    await fetchUser()
    // Se ainda não tiver usuário após buscar, mostrar erro
    if (!user.value) {
      error.value = 'Não foi possível carregar os dados do usuário'
    }
  } catch (err) {
    error.value = err.message || 'Erro ao carregar dados do perfil'
    console.error('Erro ao buscar perfil:', err)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.profile-container {
  max-width: 1200px;
  margin: 0 auto;
}

.profile-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-xl);
  padding: var(--spacing-lg);
}

.profile-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 2.5rem;
  box-shadow: 0 8px 16px rgba(34, 197, 94, 0.3);
  border: 4px solid white;
}

.profile-details {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

.profile-detail-item {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-xs);
  padding: var(--spacing-md);
  background-color: var(--color-gray-50);
  border-radius: 10px;
  border: 1px solid var(--color-gray-200);
}

.detail-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.8125rem;
  font-weight: 600;
  color: var(--color-gray-600);
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.detail-value {
  font-size: 1rem;
  font-weight: 500;
  color: var(--color-gray-900);
  margin-top: 4px;
}

.role-badge {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  border-radius: 8px;
  font-size: 0.875rem;
  font-weight: 600;
}

.role-admin {
  background-color: #dbeafe;
  color: #1e40af;
}

.role-gestor {
  background-color: #dcfce7;
  color: #166534;
}

.role-funcionario {
  background-color: #fef3c7;
  color: #92400e;
}

.status-badge {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  border-radius: 8px;
  font-size: 0.875rem;
  font-weight: 600;
}

.status-active {
  background-color: #dcfce7;
  color: #166534;
}

.status-inactive {
  background-color: #fee2e2;
  color: #991b1b;
}

.profile-stats {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
  padding: var(--spacing-lg);
}

.stat-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
  padding: var(--spacing-lg);
  background-color: var(--color-gray-50);
  border-radius: 12px;
  border: 1px solid var(--color-gray-200);
  transition: all 0.2s ease;
}

.stat-item:hover {
  background-color: var(--color-white);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transform: translateY(-2px);
}

.stat-icon {
  color: var(--color-primary);
  flex-shrink: 0;
}

.stat-content {
  flex: 1;
}

.stat-label {
  font-size: 0.8125rem;
  font-weight: 600;
  color: var(--color-gray-600);
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin-bottom: 4px;
}

.stat-value {
  font-size: 1.125rem;
  font-weight: 600;
  color: var(--color-gray-900);
}

@media (max-width: 768px) {
  .grid.grid-cols-2 {
    grid-template-columns: 1fr;
  }
  
  .profile-avatar {
    width: 100px;
    height: 100px;
    font-size: 2rem;
  }
}
</style>

