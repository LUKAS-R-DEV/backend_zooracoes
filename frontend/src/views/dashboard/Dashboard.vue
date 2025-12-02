<template>
  <AppLayout>
    <div class="page-header">
      <h1 class="page-title">Bom dia, {{ user?.name || 'Usuário' }}!</h1>
    </div>

    <div v-if="loading" class="loading">
      <Loading />
    </div>

    <div v-else-if="error" class="error-message" style="display: flex; align-items: center; gap: 8px">
      <Icon name="alert-circle" :size="20" />
      {{ error }}
    </div>

    <div v-else>
      <div class="dashboard-stats-grid">
        <div class="stats-card stats-card-primary">
          <div class="stats-card-icon-wrapper">
            <Icon name="users" :size="28" class="stats-card-icon" />
          </div>
          <div class="stats-card-content">
            <div class="stats-value">{{ dashboardData?.totalTutors || 0 }}</div>
            <div class="stats-label">Tutores</div>
          </div>
          <div class="stats-card-decoration"></div>
        </div>
        <div class="stats-card stats-card-success">
          <div class="stats-card-icon-wrapper">
            <Icon name="dog" :size="28" class="stats-card-icon" />
          </div>
          <div class="stats-card-content">
            <div class="stats-value">{{ dashboardData?.totalPets || 0 }}</div>
            <div class="stats-label">Pets</div>
          </div>
          <div class="stats-card-decoration"></div>
        </div>
        <div class="stats-card stats-card-info">
          <div class="stats-card-icon-wrapper">
            <Icon name="calendar" :size="28" class="stats-card-icon" />
          </div>
          <div class="stats-card-content">
            <div class="stats-value">{{ dashboardData?.schedulesToday || 0 }}</div>
            <div class="stats-label">Agendamentos Hoje</div>
          </div>
          <div class="stats-card-decoration"></div>
        </div>
        <div class="stats-card stats-card-warning">
          <div class="stats-card-icon-wrapper">
            <Icon name="alert-triangle" :size="28" class="stats-card-icon" />
          </div>
          <div class="stats-card-content">
            <div class="stats-value">{{ dashboardData?.vaccinesLate || 0 }}</div>
            <div class="stats-label">Vacinas Atrasadas</div>
          </div>
          <div class="stats-card-decoration"></div>
        </div>
        <div class="stats-card stats-card-info">
          <div class="stats-card-icon-wrapper">
            <Icon name="syringe" :size="28" class="stats-card-icon" />
          </div>
          <div class="stats-card-content">
            <div class="stats-value">{{ dashboardData?.vaccinesNext7Days || 0 }}</div>
            <div class="stats-label">Vacinas Próximas</div>
          </div>
          <div class="stats-card-decoration"></div>
        </div>
      </div>

      <div class="grid grid-cols-2" style="gap: var(--spacing-lg)">
        <Card>
          <div class="card-header">
            <h3 class="card-title">Últimos Agendamentos</h3>
          </div>
          <div v-if="lastSchedules.length === 0" class="empty-state">
            <Icon name="calendar-x" :size="48" class="empty-state-icon" />
            <div class="empty-state-text">Nenhum agendamento recente</div>
          </div>
          <div v-else>
            <div 
              v-for="schedule in lastSchedules" 
              :key="schedule.id"
              class="schedule-item"
              @click="goToSchedule(schedule.id)"
            >
              <Icon :name="getStatusIcon(schedule.status)" :size="24" :class="['schedule-icon', getStatusIconClass(schedule.status)]" />
              <div class="schedule-content">
                <div class="schedule-pet-name">{{ schedule.petName || '-' }}</div>
                <div class="schedule-details">
                  {{ schedule.tutorName || '-' }} - {{ schedule.serviceName || '-' }}
                </div>
              </div>
              <div class="schedule-time">
                <div class="schedule-time-value">{{ formatDateTime(schedule.dateTime) }}</div>
                <div class="schedule-time-until" :class="getStatusClass(schedule.status)">
                  {{ getStatusLabel(schedule.status) }}
                </div>
              </div>
            </div>
          </div>
        </Card>

        <Card>
          <div class="card-header">
            <h3 class="card-title">Agendamentos Atrasados</h3>
          </div>
          <div v-if="overdueSchedules.length === 0" class="empty-state">
            <Icon name="check-circle" :size="48" class="empty-state-icon" />
            <div class="empty-state-text">Nenhum agendamento atrasado</div>
          </div>
          <div v-else>
            <div 
              v-for="schedule in overdueSchedules" 
              :key="schedule.id"
              class="schedule-item overdue-item"
              @click="goToSchedule(schedule.id)"
            >
              <Icon name="alert-triangle" :size="24" class="schedule-icon overdue-icon" />
              <div class="schedule-content">
                <div class="schedule-pet-name">{{ schedule.petName || '-' }}</div>
                <div class="schedule-details">
                  {{ schedule.tutorName || '-' }} - {{ schedule.serviceName || '-' }}
                </div>
              </div>
              <div class="schedule-time">
                <div class="schedule-time-value">{{ formatDateTime(schedule.dateTime) }}</div>
                <div class="schedule-time-until overdue-text">{{ getTimeAgo(schedule.dateTime) }}</div>
              </div>
            </div>
          </div>
        </Card>

        <Card class="full-width-card">
          <div class="card-header">
            <h3 class="card-title">Agendamentos de Hoje</h3>
          </div>
          <div v-if="todaySchedules.length === 0" class="empty-state">
            <Icon name="calendar" :size="48" class="empty-state-icon" />
            <div class="empty-state-text">Nenhum agendamento hoje</div>
          </div>
          <div v-else>
            <div 
              v-for="schedule in todaySchedules" 
              :key="schedule.id"
              class="schedule-item"
              @click="goToSchedule(schedule.id)"
            >
              <Icon :name="getStatusIcon(schedule.status)" :size="24" :class="['schedule-icon', getStatusIconClass(schedule.status)]" />
              <div class="schedule-content">
                <div class="schedule-pet-name">{{ schedule.petName || '-' }}</div>
                <div class="schedule-details">
                  {{ schedule.tutorName || '-' }} - {{ schedule.serviceName || '-' }}
                </div>
              </div>
              <div class="schedule-time">
                <div class="schedule-time-value">{{ formatTime(schedule.dateTime) }}</div>
                <div class="schedule-time-until" :class="getStatusClass(schedule.status)">
                  {{ getStatusLabel(schedule.status) }}
                </div>
              </div>
            </div>
          </div>
        </Card>
      </div>

      <div class="grid grid-cols-2" style="gap: var(--spacing-lg); margin-top: var(--spacing-lg)">
        <Card>
          <div class="card-header">
            <h3 class="card-title">Vacinas Atrasadas</h3>
          </div>
          <div v-if="overdueVaccines.length === 0" class="empty-state">
            <Icon name="check-circle" :size="48" class="empty-state-icon" />
            <div class="empty-state-text">Nenhuma vacina atrasada</div>
          </div>
          <div v-else>
            <div 
              v-for="vaccine in overdueVaccines" 
              :key="vaccine.id"
              class="schedule-item overdue-item"
              @click="goToPet(vaccine.petId)"
            >
              <Icon name="alert-triangle" :size="24" class="schedule-icon overdue-icon" />
              <div class="schedule-content">
                <div class="schedule-pet-name">{{ vaccine.petName || '-' }}</div>
                <div class="schedule-details">
                  {{ vaccine.vaccineName || '-' }}
                </div>
              </div>
              <div class="schedule-time">
                <div class="schedule-time-value">{{ formatDate(vaccine.nextDoseDate) }}</div>
                <div class="schedule-time-until overdue-text">{{ getDaysAgo(vaccine.nextDoseDate) }}</div>
              </div>
            </div>
          </div>
        </Card>

        <Card>
          <div class="card-header">
            <h3 class="card-title">Vacinas Próximas</h3>
          </div>
          <div v-if="upcomingVaccines.length === 0" class="empty-state">
            <Icon name="syringe" :size="48" class="empty-state-icon" />
            <div class="empty-state-text">Nenhuma vacina próxima</div>
          </div>
          <div v-else>
            <div 
              v-for="vaccine in upcomingVaccines" 
              :key="vaccine.id"
              class="schedule-item"
              @click="goToPet(vaccine.petId)"
            >
              <Icon name="syringe" :size="24" class="schedule-icon status-icon-pending" />
              <div class="schedule-content">
                <div class="schedule-pet-name">{{ vaccine.petName || '-' }}</div>
                <div class="schedule-details">
                  {{ vaccine.vaccineName || '-' }}
                </div>
              </div>
              <div class="schedule-time">
                <div class="schedule-time-value">{{ formatDate(vaccine.nextDoseDate) }}</div>
                <div class="schedule-time-until status-pending">{{ getDaysUntil(vaccine.nextDoseDate) }}</div>
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
import { useRouter } from 'vue-router'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import { useAnalytics } from '@/composables/useAnalytics'
import { useSchedules } from '@/composables/useSchedules'
import { useVaccines } from '@/composables/useVaccines'
import { useAuthStore } from '@/stores/auth'
import { formatters } from '@/utils/formatters'
import { SCHEDULE_STATUS_LABELS } from '@/utils/constants'

const router = useRouter()
const { dashboardData, loading: analyticsLoading, error: analyticsError, fetchDashboard } = useAnalytics()
const { schedules, loading: schedulesLoading, fetchSchedules } = useSchedules()
const { vaccines, loading: vaccinesLoading, fetchVaccines } = useVaccines()
const { user } = useAuthStore()

const loading = computed(() => analyticsLoading.value || schedulesLoading.value || vaccinesLoading.value)
const error = computed(() => analyticsError.value)

// Últimos agendamentos (mais recentes criados, ordenados por ID decrescente, limitado a 5)
const lastSchedules = computed(() => {
  if (!schedules.value || schedules.value.length === 0) return []
  
  return schedules.value
    .sort((a, b) => (b.id || 0) - (a.id || 0)) // Ordenar por ID decrescente (mais recente primeiro)
    .slice(0, 5)
})

// Agendamentos atrasados (data no passado e status PENDING)
const overdueSchedules = computed(() => {
  if (!schedules.value || schedules.value.length === 0) return []
  
  const now = new Date()
  
  return schedules.value
    .filter(schedule => {
      if (schedule.status !== 'PENDING') return false
      const scheduleDate = new Date(schedule.dateTime)
      return scheduleDate < now
    })
    .sort((a, b) => new Date(a.dateTime) - new Date(b.dateTime)) // Mais antigo primeiro
    .slice(0, 5)
})

// Agendamentos de hoje (todos os status)
const todaySchedules = computed(() => {
  if (!schedules.value || schedules.value.length === 0) return []
  
  const now = new Date()
  const todayStart = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 0, 0, 0)
  const todayEnd = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59)
  
  return schedules.value
    .filter(schedule => {
      const scheduleDate = new Date(schedule.dateTime)
      return scheduleDate >= todayStart && scheduleDate <= todayEnd
    })
    .sort((a, b) => new Date(a.dateTime) - new Date(b.dateTime)) // Ordenar por horário
})

// Vacinas atrasadas (data da próxima dose no passado)
const overdueVaccines = computed(() => {
  if (!vaccines.value || vaccines.value.length === 0) return []
  
  const now = new Date()
  now.setHours(0, 0, 0, 0)
  
  return vaccines.value
    .filter(vaccine => {
      if (!vaccine.nextDoseDate) return false
      const nextDose = new Date(vaccine.nextDoseDate)
      nextDose.setHours(0, 0, 0, 0)
      return nextDose < now
    })
    .sort((a, b) => new Date(a.nextDoseDate) - new Date(b.nextDoseDate)) // Mais antiga primeiro
    .slice(0, 5)
})

// Vacinas próximas (próximos 7 dias)
const upcomingVaccines = computed(() => {
  if (!vaccines.value || vaccines.value.length === 0) return []
  
  const now = new Date()
  now.setHours(0, 0, 0, 0)
  const sevenDaysLater = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000)
  
  return vaccines.value
    .filter(vaccine => {
      if (!vaccine.nextDoseDate) return false
      const nextDose = new Date(vaccine.nextDoseDate)
      nextDose.setHours(0, 0, 0, 0)
      return nextDose >= now && nextDose <= sevenDaysLater
    })
    .sort((a, b) => new Date(a.nextDoseDate) - new Date(b.nextDoseDate)) // Mais próxima primeiro
    .slice(0, 5)
})

const formatTime = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' })
}

const formatDateTime = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('pt-BR', { 
    day: '2-digit', 
    month: 'short',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const getStatusLabel = (status) => {
  return SCHEDULE_STATUS_LABELS[status] || status
}

const getStatusIcon = (status) => {
  switch (status) {
    case 'DONE':
      return 'check-circle'
    case 'CANCELLED':
      return 'x-circle'
    case 'PENDING':
      return 'clock'
    default:
      return 'calendar'
  }
}

const getStatusIconClass = (status) => {
  switch (status) {
    case 'DONE':
      return 'status-icon-done'
    case 'CANCELLED':
      return 'status-icon-cancelled'
    case 'PENDING':
      return 'status-icon-pending'
    default:
      return ''
  }
}

const getStatusClass = (status) => {
  switch (status) {
    case 'DONE':
      return 'status-done'
    case 'CANCELLED':
      return 'status-cancelled'
    case 'PENDING':
      return 'status-pending'
    default:
      return ''
  }
}

const getTimeAgo = (dateString) => {
  if (!dateString) return ''
  const now = new Date()
  const date = new Date(dateString)
  const diff = now - date
  const hours = Math.floor(diff / (1000 * 60 * 60))
  const days = Math.floor(hours / 24)
  
  if (days > 0) return `Há ${days} ${days === 1 ? 'dia' : 'dias'}`
  if (hours > 0) return `Há ${hours} ${hours === 1 ? 'hora' : 'horas'}`
  const minutes = Math.floor(diff / (1000 * 60))
  if (minutes > 0) return `Há ${minutes} ${minutes === 1 ? 'minuto' : 'minutos'}`
  return 'Agora'
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('pt-BR', { day: '2-digit', month: 'short' })
}

const getDaysAgo = (dateString) => {
  if (!dateString) return ''
  const now = new Date()
  now.setHours(0, 0, 0, 0)
  const date = new Date(dateString)
  date.setHours(0, 0, 0, 0)
  const diff = now - date
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))
  
  if (days === 0) return 'Hoje'
  if (days === 1) return 'Há 1 dia'
  return `Há ${days} dias`
}

const getDaysUntil = (dateString) => {
  if (!dateString) return ''
  const now = new Date()
  now.setHours(0, 0, 0, 0)
  const date = new Date(dateString)
  date.setHours(0, 0, 0, 0)
  const diff = date - now
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))
  
  if (days < 0) return 'Atrasado'
  if (days === 0) return 'Hoje'
  if (days === 1) return 'Amanhã'
  return `Em ${days} dias`
}

const goToSchedule = (scheduleId) => {
  router.push(`/schedules/${scheduleId}`)
}

const goToPet = (petId) => {
  router.push(`/pets/${petId}`)
}

onMounted(async () => {
  try {
    await Promise.all([
      fetchDashboard(),
      fetchSchedules({ size: 100, sort: 'dateTime,desc' }), // Buscar mais agendamentos para filtrar (mais recentes primeiro)
      fetchVaccines() // Buscar vacinas para os cards
    ])
  } catch (err) {
    console.error('Erro ao carregar dados do dashboard:', err)
  }
})
</script>

<style scoped>
.dashboard-stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: var(--spacing-lg);
  margin-bottom: var(--spacing-xl);
}

.stats-card {
  position: relative;
  background: linear-gradient(135deg, #ffffff 0%, #f9fafb 100%);
  border-radius: 20px;
  padding: 24px;
  border: 1px solid var(--color-gray-200);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  transition: all 0.3s ease;
  overflow: hidden;
}

.stats-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  transform: scaleX(0);
  transform-origin: left;
  transition: transform 0.3s ease;
}

.stats-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.stats-card:hover::before {
  transform: scaleX(1);
}

.stats-card-primary::before {
  background: linear-gradient(90deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
}

.stats-card-success::before {
  background: linear-gradient(90deg, #10b981 0%, #059669 100%);
}

.stats-card-info::before {
  background: linear-gradient(90deg, #3b82f6 0%, #2563eb 100%);
}

.stats-card-warning::before {
  background: linear-gradient(90deg, #f59e0b 0%, #d97706 100%);
}

.stats-card-icon-wrapper {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 16px;
  position: relative;
  transition: all 0.3s ease;
}

.stats-card-primary .stats-card-icon-wrapper {
  background: linear-gradient(135deg, rgba(34, 197, 94, 0.1) 0%, rgba(34, 197, 94, 0.15) 100%);
}

.stats-card-success .stats-card-icon-wrapper {
  background: linear-gradient(135deg, rgba(16, 185, 129, 0.1) 0%, rgba(16, 185, 129, 0.15) 100%);
}

.stats-card-info .stats-card-icon-wrapper {
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.1) 0%, rgba(59, 130, 246, 0.15) 100%);
}

.stats-card-warning .stats-card-icon-wrapper {
  background: linear-gradient(135deg, rgba(245, 158, 11, 0.1) 0%, rgba(245, 158, 11, 0.15) 100%);
}

.stats-card:hover .stats-card-icon-wrapper {
  transform: scale(1.1) rotate(5deg);
}

.stats-card-icon {
  color: var(--color-primary);
  transition: all 0.3s ease;
}

.stats-card-success .stats-card-icon {
  color: #10b981;
}

.stats-card-info .stats-card-icon {
  color: #3b82f6;
}

.stats-card-warning .stats-card-icon {
  color: #f59e0b;
}

.stats-card-content {
  position: relative;
  z-index: 1;
}

.stats-value {
  font-size: 2.25rem;
  font-weight: 700;
  color: var(--color-gray-900);
  line-height: 1;
  margin-bottom: 8px;
  letter-spacing: -0.02em;
}

.stats-label {
  font-size: 0.875rem;
  font-weight: 500;
  color: var(--color-gray-600);
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.stats-card-decoration {
  position: absolute;
  bottom: -20px;
  right: -20px;
  width: 120px;
  height: 120px;
  border-radius: 50%;
  opacity: 0.05;
  background: var(--color-primary);
  pointer-events: none;
}

.stats-card-success .stats-card-decoration {
  background: #10b981;
}

.stats-card-info .stats-card-decoration {
  background: #3b82f6;
}

.stats-card-warning .stats-card-decoration {
  background: #f59e0b;
}

@media (max-width: 1024px) {
  .dashboard-stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 640px) {
  .dashboard-stats-grid {
    grid-template-columns: 1fr;
  }
  
  .stats-card {
    padding: 20px;
  }
  
  .stats-value {
    font-size: 1.875rem;
  }
}

.card-header {
  padding: var(--spacing-lg);
  border-bottom: 1px solid var(--color-gray-200);
}

.card-title {
  font-size: 1.125rem;
  font-weight: 600;
  color: var(--color-gray-900);
  margin: 0;
}

.empty-state {
  padding: var(--spacing-2xl);
  text-align: center;
  color: var(--color-gray-500);
}

.empty-state-icon {
  color: var(--color-gray-300);
  margin-bottom: var(--spacing-md);
}

.empty-state-text {
  font-size: 0.9375rem;
  color: var(--color-gray-500);
}

.schedule-item,
.reminder-item {
  padding: var(--spacing-md);
  border-bottom: 1px solid var(--color-gray-200);
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.schedule-item:hover,
.reminder-item:hover {
  background-color: var(--color-gray-50);
}

.schedule-item:last-child,
.reminder-item:last-child {
  border-bottom: none;
}

.schedule-icon,
.reminder-icon {
  color: var(--color-primary);
  flex-shrink: 0;
}

.schedule-content,
.reminder-content {
  flex: 1;
  min-width: 0;
}

.schedule-pet-name,
.reminder-title {
  font-weight: 500;
  color: var(--color-gray-900);
  margin-bottom: 4px;
}

.schedule-details,
.reminder-description {
  font-size: 0.875rem;
  color: var(--color-gray-600);
}

.schedule-time,
.reminder-date {
  text-align: right;
  flex-shrink: 0;
}

.schedule-time-value,
.reminder-date-value {
  font-weight: 500;
  color: var(--color-gray-900);
  margin-bottom: 4px;
}

.schedule-time-until,
.reminder-date-until {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.full-width-card {
  grid-column: 1 / -1;
}

.overdue-item {
  border-left: 3px solid var(--color-warning);
}

.overdue-icon {
  color: var(--color-warning);
}

.overdue-text {
  color: var(--color-warning);
  font-weight: 500;
}

.status-icon-done {
  color: #10b981;
}

.status-icon-cancelled {
  color: var(--color-gray-500);
}

.status-icon-pending {
  color: #3b82f6;
}

.status-done {
  color: #10b981;
  font-weight: 500;
}

.status-cancelled {
  color: var(--color-gray-500);
  font-weight: 500;
}

.status-pending {
  color: #3b82f6;
  font-weight: 500;
}

@media (max-width: 768px) {
  .grid-cols-2 {
    grid-template-columns: 1fr;
  }
  
  .full-width-card {
    grid-column: 1;
  }
}
</style>

