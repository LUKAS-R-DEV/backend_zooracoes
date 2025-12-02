<template>
  <AppLayout>
    <div class="page-header">
      <div>
        <Button variant="secondary" icon="arrow-left" @click="handleBack">
          Voltar
        </Button>
        <h1 class="page-title">Detalhes da Prescrição</h1>
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

    <div v-else-if="prescription" class="detail-container">
      <Card>
        <div class="detail-section">
          <h2 class="section-title">Informações da Prescrição</h2>
          <div class="detail-grid">
            <div class="detail-item">
              <div class="detail-label">
                <Icon name="pill" :size="18" />
                <span>Medicamento</span>
              </div>
              <div class="detail-value">{{ prescription.medicationName || '-' }}</div>
            </div>

            <div class="detail-item">
              <div class="detail-label">
                <Icon name="syringe" :size="18" />
                <span>Dosagem</span>
              </div>
              <div class="detail-value">{{ prescription.dosage || '-' }}</div>
            </div>

            <div class="detail-item detail-item-full">
              <div class="detail-label">
                <Icon name="file-text" :size="18" />
                <span>Instruções</span>
              </div>
              <div class="detail-value">{{ prescription.instructions || '-' }}</div>
            </div>

            <div class="detail-item" v-if="prescription.startDate && prescription.endDate">
              <div class="detail-label">
                <Icon name="calendar" :size="18" />
                <span>Período</span>
              </div>
              <div class="detail-value">{{ formatDate(prescription.startDate) }} - {{ formatDate(prescription.endDate) }}</div>
            </div>

            <div class="detail-item" v-if="prescription.veterinarian">
              <div class="detail-label">
                <Icon name="user" :size="18" />
                <span>Veterinário</span>
              </div>
              <div class="detail-value">{{ prescription.veterinarian }}</div>
            </div>

            <div class="detail-item">
              <div class="detail-label">
                <Icon name="calendar-clock" :size="18" />
                <span>Data da Prescrição</span>
              </div>
              <div class="detail-value">{{ formatDateTime(prescription.prescribedAt) }}</div>
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
              <div class="detail-value">
                <Button 
                  variant="ghost" 
                  size="sm" 
                  icon="external-link"
                  @click="$router.push(`/pets/${prescription.petId}`)"
                >
                  {{ petName || 'Carregando...' }}
                </Button>
              </div>
            </div>
          </div>
        </div>
      </Card>

      <div class="actions-bar">
        <Button variant="secondary" icon="pencil" @click="handleEdit">
          Editar
        </Button>
      </div>
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import { usePrescriptions } from '@/composables/usePrescriptions'
import { usePets } from '@/composables/usePets'
import { formatters } from '@/utils/formatters'

const route = useRoute()
const router = useRouter()
const { prescription, loading, error, fetchPrescription } = usePrescriptions()
const { pet, fetchPet } = usePets()

const petName = ref('')

const formatDate = (dateString) => {
  if (!dateString) return '-'
  return formatters.date(dateString)
}

const formatDateTime = (dateString) => {
  if (!dateString) return '-'
  return formatters.dateTime(dateString)
}

const handleBack = () => {
  // Voltar para a página anterior ou para a lista de pets
  if (route.query.from === 'pet') {
    router.push(`/pets/${prescription.value?.petId}`)
  } else {
    router.push('/pets')
  }
}

const handleEdit = () => {
  router.push(`/pets/${prescription.value?.petId}?editPrescription=${prescription.value?.id}`)
}

onMounted(async () => {
  await fetchPrescription(route.params.id)
  if (prescription.value?.petId) {
    try {
      const petData = await fetchPet(prescription.value.petId)
      petName.value = petData?.name || '-'
    } catch (err) {
      console.error('Erro ao buscar pet:', err)
    }
  }
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

.detail-item-full {
  grid-column: 1 / -1;
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
  word-break: break-word;
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

