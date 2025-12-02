<template>
  <AppLayout>
    <div class="page-header">
      <div>
        <Button variant="secondary" icon="arrow-left" @click="$router.push('/schedules')">
          Voltar
        </Button>
        <h1 class="page-title">Detalhes do Agendamento</h1>
      </div>
    </div>

    <Card v-if="loading">
      <Loading />
    </Card>

    <Card v-else-if="error" class="error-card">
      <div class="error-message" style="display: flex; align-items: center; gap: 8px">
        <Icon name="alert-circle" :size="20" />
        {{ error }}
      </div>
    </Card>

    <div v-else-if="schedule" class="detail-container">
      <Card>
        <div class="detail-section">
          <h2 class="section-title">Informações do Agendamento</h2>
          <div class="detail-grid">
            <div class="detail-item">
              <div class="detail-label">
                <Icon name="calendar-clock" :size="18" />
                <span>Data e Hora</span>
              </div>
              <div class="detail-value">{{ formatDateTime(schedule.dateTime) }}</div>
            </div>

            <div class="detail-item">
              <div class="detail-label">
                <Icon name="circle" :size="18" />
                <span>Status</span>
              </div>
              <div class="detail-value">
                <span :class="getStatusBadgeClass(schedule.status)" style="display: inline-flex; align-items: center; gap: 4px">
                  <Icon :name="getStatusIcon(schedule.status)" :size="14" />
                  {{ getStatusLabel(schedule.status) }}
                </span>
              </div>
            </div>
          </div>
        </div>
      </Card>

      <Card>
        <div class="detail-section">
          <h2 class="section-title">Pet</h2>
          <div class="detail-grid">
            <div class="detail-item">
              <div class="detail-label">
                <Icon name="dog" :size="18" />
                <span>Nome do Pet</span>
              </div>
              <div class="detail-value">{{ schedule.petName || '-' }}</div>
            </div>
          </div>
        </div>
      </Card>

      <Card>
        <div class="detail-section">
          <h2 class="section-title">Tutor</h2>
          <div class="detail-grid">
            <div class="detail-item">
              <div class="detail-label">
                <Icon name="user" :size="18" />
                <span>Nome do Tutor</span>
              </div>
              <div class="detail-value">{{ schedule.tutorName || '-' }}</div>
            </div>
          </div>
        </div>
      </Card>

      <Card>
        <div class="detail-section">
          <h2 class="section-title">Serviço</h2>
          <div class="detail-grid">
            <div class="detail-item">
              <div class="detail-label">
                <Icon name="stethoscope" :size="18" />
                <span>Nome do Serviço</span>
              </div>
              <div class="detail-value">{{ schedule.serviceName || '-' }}</div>
            </div>
          </div>
        </div>
      </Card>

      <Card v-if="schedule.notes">
        <div class="detail-section">
          <h2 class="section-title">Observações</h2>
          <div class="detail-item">
            <div class="detail-value">{{ schedule.notes }}</div>
          </div>
        </div>
      </Card>

      <div class="actions-bar">
        <Button variant="secondary" icon="pencil" @click="$router.push(`/schedules/${schedule.id}/edit`)">
          Editar
        </Button>
        <Button variant="primary" icon="refresh-cw" @click="showStatusModal = true">
          Alterar Status
        </Button>
      </div>
    </div>

    <StatusChangeModal
      v-if="showStatusModal && schedule"
      :schedule-id="schedule.id"
      :current-status="schedule.status"
      :loading="updatingStatus"
      @close="showStatusModal = false"
      @saved="handleStatusChange"
    />
  </AppLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import StatusChangeModal from '@/components/schedules/StatusChangeModal.vue'
import { useSchedules } from '@/composables/useSchedules'
import { useToast } from '@/composables/useToast'
import { formatters } from '@/utils/formatters'
import { SCHEDULE_STATUS_LABELS } from '@/utils/constants'

const route = useRoute()
const toast = useToast()
const { schedule, loading, error, fetchSchedule, updateScheduleStatus } = useSchedules()

const showStatusModal = ref(false)
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

const handleStatusChange = async (newStatus) => {
  updatingStatus.value = true
  try {
    await updateScheduleStatus(route.params.id, newStatus)
    await fetchSchedule(route.params.id)
    toast.success('Status atualizado com sucesso!')
    showStatusModal.value = false
  } catch (err) {
    console.error('Erro ao atualizar status:', err)
    toast.error('Erro ao atualizar status. Tente novamente.')
  } finally {
    updatingStatus.value = false
  }
}

onMounted(async () => {
  await fetchSchedule(route.params.id)
})
</script>

<style scoped>
.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: var(--spacing-xl);
  gap: var(--spacing-md);
}

.page-header > div {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
}

.page-title {
  font-size: 1.875rem;
  font-weight: 700;
  color: var(--color-gray-900);
  margin: 0;
}

.detail-container {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

.detail-section {
  width: 100%;
}

.section-title {
  font-size: 1.125rem;
  font-weight: 600;
  color: var(--color-gray-900);
  margin: 0 0 var(--spacing-lg) 0;
  padding-bottom: var(--spacing-md);
  border-bottom: 2px solid var(--color-gray-100);
}

.detail-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: var(--spacing-lg);
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-xs);
}

.detail-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--color-gray-600);
}

.detail-value {
  font-size: 1rem;
  color: var(--color-gray-900);
  font-weight: 500;
}

.actions-bar {
  display: flex;
  gap: var(--spacing-md);
  justify-content: flex-end;
  margin-top: var(--spacing-lg);
}

.error-card {
  padding: var(--spacing-xl);
}

@media (max-width: 768px) {
  .detail-grid {
    grid-template-columns: 1fr;
  }

  .actions-bar {
    flex-direction: column;
  }

  .actions-bar > * {
    width: 100%;
  }
}
</style>


