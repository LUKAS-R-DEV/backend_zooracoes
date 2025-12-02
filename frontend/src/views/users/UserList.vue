<template>
  <AppLayout>
    <div class="page-header">
      <div>
        <h1 class="page-title">Usuários</h1>
        <p class="page-subtitle">Gerencie todos os usuários do sistema</p>
      </div>
      <Button v-if="canCreate" icon="plus" @click="$router.push('/users/new')">
        Novo Usuário
      </Button>
    </div>

    <Card>
      <div v-if="loading" class="loading">
        <Loading />
      </div>

      <div v-else-if="error" class="error-message" style="display: flex; align-items: center; gap: 8px">
        <Icon name="alert-circle" :size="20" />
        {{ error }}
      </div>

      <div v-else>
        <div v-if="users.length === 0" class="empty-state">
          <Icon name="users" :size="48" class="empty-state-icon" />
          <div class="empty-state-text">Nenhum usuário cadastrado</div>
          <div class="empty-state-subtext">Comece adicionando um novo usuário</div>
        </div>

        <div v-else class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>
                  <div class="table-header-content">
                    <Icon name="user" :size="16" />
                    <span>Nome</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="mail" :size="16" />
                    <span>Email</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="shield" :size="16" />
                    <span>Perfil</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="circle-check" :size="16" />
                    <span>Status</span>
                  </div>
                </th>
                <th v-if="canEdit">
                  <div class="table-header-content">
                    <Icon name="more-horizontal" :size="16" />
                    <span>Ações</span>
                  </div>
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="user in users" :key="user.id">
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ user.name }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ user.email }}</span>
                  </div>
                </td>
                <td>
                  <span :class="getRoleBadgeClass(user.role)" class="role-badge">
                    {{ getUserRoleLabel(user.role) }}
                  </span>
                </td>
                <td>
                  <span :class="user.active ? 'badge badge-success' : 'badge badge-error'">
                    {{ user.active ? 'Ativo' : 'Inativo' }}
                  </span>
                </td>
                <td v-if="canEdit">
                  <div class="table-actions">
                    <Button variant="secondary" size="sm" icon="pencil" @click="$router.push(`/users/${user.id}/edit`)">
                      Editar
                    </Button>
                    <Button variant="danger" size="sm" icon="trash-2" @click="handleDelete(user.id)">
                      Excluir
                    </Button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </Card>
  </AppLayout>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useAuth } from '@/composables/useAuth'
import { useToast } from '@/composables/useToast'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import { useUsers } from '@/composables/useUsers'
import { useConfirmModal } from '@/composables/useConfirmModal'
import { USER_ROLE_LABELS } from '@/utils/constants'

const { user: currentUser } = useAuth()
const toast = useToast()
const confirmModal = useConfirmModal()
const { users, loading, error, fetchUsers, deleteUser } = useUsers()

const canCreate = computed(() => {
  return currentUser.value?.role === 'ADMIN'
})

const canEdit = computed(() => {
  return currentUser.value?.role === 'ADMIN'
})

const getUserRoleLabel = (role) => {
  return USER_ROLE_LABELS[role] || role
}

const getRoleBadgeClass = (role) => {
  switch (role) {
    case 'ADMIN':
      return 'badge badge-info'
    case 'GESTOR':
      return 'badge badge-primary'
    case 'FUNCIONARIO':
      return 'badge badge-warning'
    default:
      return 'badge badge-secondary'
  }
}

const handleDelete = async (id) => {
  const confirmed = await confirmModal.showConfirm({
    title: 'Excluir Usuário',
    message: 'Tem certeza que deseja excluir este usuário? Esta ação não pode ser desfeita.',
    confirmText: 'Excluir',
    cancelText: 'Cancelar',
    variant: 'danger'
  })

  if (confirmed) {
    try {
      await deleteUser(id)
      await fetchUsers()
      toast.success('Usuário excluído com sucesso!')
    } catch (err) {
      console.error('Erro ao excluir usuário:', err)
      toast.error('Erro ao excluir usuário. Tente novamente.')
    }
  }
}

onMounted(() => {
  fetchUsers()
})
</script>

<style scoped>
.role-badge {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  border-radius: 8px;
  font-size: 0.8125rem;
  font-weight: 600;
}
</style>





