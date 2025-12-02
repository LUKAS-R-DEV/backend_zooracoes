<template>
  <AppLayout>
      <div class="page-header">
      <div>
        <h1 class="page-title">Agendamentos</h1>
        <p class="page-subtitle">Gerencie todos os agendamentos</p>
      </div>
        <Button icon="plus" @click="$router.push('/schedules/new')">
          Novo Agendamento
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
        <div v-if="schedules.length === 0" class="empty-state">
          <Icon name="calendar-x" :size="48" class="empty-state-icon" />
          <div class="empty-state-text">Nenhum agendamento cadastrado</div>
          <div class="empty-state-subtext">Comece adicionando um novo agendamento</div>
        </div>

        <div v-else class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>
                  <div class="table-header-content">
                    <Icon name="calendar-clock" :size="16" />
                    <span>Data/Hora</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="dog" :size="16" />
                    <span>Pet</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="user" :size="16" />
                    <span>Tutor</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="stethoscope" :size="16" />
                    <span>Serviço</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="circle" :size="16" />
                    <span>Status</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="more-horizontal" :size="16" />
                    <span>Ações</span>
                  </div>
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="schedule in schedules" :key="schedule.id">
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ formatDateTime(schedule.dateTime) }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ schedule.petName || '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ schedule.tutorName || '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ schedule.serviceName || '-' }}</span>
                  </div>
                </td>
                <td>
                  <span :class="getStatusBadgeClass(schedule.status)" style="display: inline-flex; align-items: center; gap: 4px">
                    <Icon :name="getStatusIcon(schedule.status)" :size="14" />
                    {{ getStatusLabel(schedule.status) }}
                  </span>
                </td>
                <td>
                  <div class="table-actions">
                    <Button variant="primary" size="sm" icon="eye" @click="$router.push(`/schedules/${schedule.id}`)">
                      Detalhes
                    </Button>
                    <Button variant="secondary" size="sm" icon="refresh-cw" @click="openStatusModal(schedule)">
                      Status
                    </Button>
                    <Button variant="secondary" size="sm" icon="pencil" @click="$router.push(`/schedules/${schedule.id}/edit`)">
                      Editar
                    </Button>
                    <Button variant="danger" size="sm" icon="trash-2" @click="handleDelete(schedule.id)">
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

    <StatusChangeModal
      v-if="selectedSchedule"
      :schedule-id="selectedSchedule.id"
      :current-status="selectedSchedule.status"
      :loading="updatingStatus"
      @close="selectedSchedule = null"
      @saved="handleStatusChange"
    />
  </AppLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import StatusChangeModal from '@/components/schedules/StatusChangeModal.vue'
import { useSchedules } from '@/composables/useSchedules'
import { useToast } from '@/composables/useToast'
import { useConfirmModal } from '@/composables/useConfirmModal'
import { formatters } from '@/utils/formatters'
import { SCHEDULE_STATUS_LABELS } from '@/utils/constants'

const toast = useToast()
const confirmModal = useConfirmModal()
const { schedules, loading, error, fetchSchedules, deleteSchedule, updateScheduleStatus } = useSchedules()

const selectedSchedule = ref(null)
const updatingStatus = ref(false)

const formatDateTime = (dateString) => {
  return formatters.dateTime(dateString)
}

const getStatusLabel = (status) => {
  return SCHEDULE_STATUS_LABELS[status] || status
}

const getStatusIcon = (status) => {
  if (status === 'DONE') {
    return 'check-circle'
  } else if (status === 'CANCELLED') {
    return 'x-circle'
  } else {
    return 'clock'
  }
}

const getStatusBadgeClass = (status) => {
  const classes = {
    'badge': true
  }
  if (status === 'DONE') {
    classes['badge-success'] = true
  } else if (status === 'CANCELLED') {
    classes['badge-error'] = true
  } else {
    classes['badge-info'] = true
  }
  return classes
}

const openStatusModal = (schedule) => {
  selectedSchedule.value = schedule
}

const handleStatusChange = async (newStatus) => {
  if (!selectedSchedule.value) return
  
  updatingStatus.value = true
  try {
    await updateScheduleStatus(selectedSchedule.value.id, newStatus)
    await fetchSchedules()
    toast.success('Status atualizado com sucesso!')
    selectedSchedule.value = null
  } catch (err) {
    console.error('Erro ao atualizar status:', err)
    toast.error('Erro ao atualizar status. Tente novamente.')
  } finally {
    updatingStatus.value = false
  }
}

const handleDelete = async (id) => {
  const confirmed = await confirmModal.showConfirm({
    title: 'Excluir Agendamento',
    message: 'Tem certeza que deseja excluir este agendamento? Esta ação não pode ser desfeita.',
    confirmText: 'Excluir',
    cancelText: 'Cancelar',
    variant: 'danger'
  })

  if (confirmed) {
    try {
      await deleteSchedule(id)
      await fetchSchedules()
      toast.success('Agendamento excluído com sucesso!')
    } catch (err) {
      console.error('Erro ao excluir agendamento:', err)
      toast.error('Erro ao excluir agendamento. Tente novamente.')
    }
  }
}

onMounted(() => {
  fetchSchedules()
})
</script>

<style scoped>
.table-actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  align-items: center;
}

@media (max-width: 1200px) {
  .table-actions {
    flex-direction: column;
  }
  
  .table-actions > * {
    width: 100%;
  }
}
</style>

