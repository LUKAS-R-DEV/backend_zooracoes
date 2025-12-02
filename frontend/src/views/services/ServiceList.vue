<template>
  <AppLayout>
      <div class="page-header">
      <div>
        <h1 class="page-title">Serviços</h1>
        <p class="page-subtitle">Gerencie todos os serviços disponíveis</p>
      </div>
      <Button v-if="canCreate" icon="plus" @click="$router.push('/services/new')">
        Novo Serviço
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
        <div v-if="services.length === 0" class="empty-state">
          <Icon name="stethoscope" :size="48" class="empty-state-icon" />
          <div class="empty-state-text">Nenhum serviço cadastrado</div>
          <div class="empty-state-subtext">Comece adicionando um novo serviço</div>
        </div>

        <div v-else class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>
                  <div class="table-header-content">
                    <Icon name="stethoscope" :size="16" />
                    <span>Nome</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="file-text" :size="16" />
                    <span>Descrição</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="dollar-sign" :size="16" />
                    <span>Preço</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="clock" :size="16" />
                    <span>Duração (min)</span>
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
              <tr v-for="service in services" :key="service.id">
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ service.name }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ service.description || '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ formatCurrency(service.price) }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ service.duration || '-' }}</span>
                  </div>
                </td>
                <td v-if="canEdit">
                  <div class="table-actions">
                    <Button variant="secondary" size="sm" icon="pencil" @click="$router.push(`/services/${service.id}/edit`)">
                      Editar
                    </Button>
                    <Button variant="danger" size="sm" icon="trash-2" @click="handleDelete(service.id)">
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
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import { useServices } from '@/composables/useServices'
import { useToast } from '@/composables/useToast'
import { useConfirmModal } from '@/composables/useConfirmModal'
import { useAuthStore } from '@/stores/auth'
import { formatters } from '@/utils/formatters'

const toast = useToast()
const confirmModal = useConfirmModal()
const { services, loading, error, fetchServices, deleteService } = useServices()
const { hasRole } = useAuthStore()

const canCreate = computed(() => hasRole('ADMIN'))
const canEdit = computed(() => hasRole('ADMIN'))

const formatCurrency = (value) => {
  return formatters.currency(value)
}

const handleDelete = async (id) => {
  const confirmed = await confirmModal.showConfirm({
    title: 'Excluir Serviço',
    message: 'Tem certeza que deseja excluir este serviço? Esta ação não pode ser desfeita.',
    confirmText: 'Excluir',
    cancelText: 'Cancelar',
    variant: 'danger'
  })

  if (confirmed) {
    try {
      await deleteService(id)
      await fetchServices()
      toast.success('Serviço excluído com sucesso!')
    } catch (err) {
      console.error('Erro ao excluir serviço:', err)
      toast.error('Erro ao excluir serviço. Tente novamente.')
    }
  }
}

onMounted(() => {
  fetchServices()
})
</script>

<style scoped>
</style>

