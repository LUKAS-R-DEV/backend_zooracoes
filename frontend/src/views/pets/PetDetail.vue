<template>
  <AppLayout>
    <div class="page-header">
      <div>
        <h1 class="page-title">{{ pet?.name || 'Pet' }}</h1>
        <p class="page-subtitle">Detalhes do pet</p>
      </div>
      <div class="page-actions">
        <Button variant="secondary" icon="pencil" @click="$router.push(`/pets/${$route.params.id}/edit`)">
          Editar
        </Button>
        <Button icon="arrow-left" @click="$router.push('/pets')">
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

    <div v-else-if="pet" class="grid grid-cols-2" style="gap: var(--spacing-lg)">
      <Card>
        <div class="card-header">
          <h3 class="card-title">Informações</h3>
        </div>
        <div style="display: grid; gap: var(--spacing-md)">
          <div>
            <strong>Nome:</strong> {{ pet.name }}
          </div>
          <div>
            <strong>Espécie:</strong> {{ pet.species }}
          </div>
          <div>
            <strong>Raça:</strong> {{ pet.breed || '-' }}
          </div>
          <div>
            <strong>Peso:</strong> {{ pet.weight ? `${pet.weight} kg` : '-' }}
          </div>
          <div>
            <strong>Data de Nascimento:</strong> {{ formatDate(pet.birthDate) }}
          </div>
          <div>
            <strong>Tutor:</strong> {{ pet.tutorName || '-' }}
          </div>
        </div>
      </Card>

      <Card>
        <div class="card-header" style="display: flex; justify-content: space-between; align-items: center">
          <h3 class="card-title">Vacinas</h3>
          <Button @click="handleAddVaccine" size="sm" icon="plus">
            Adicionar
          </Button>
        </div>
        <div v-if="vaccinesList.length === 0" class="empty-state" style="padding: var(--spacing-lg)">
          <Icon name="syringe" :size="32" class="empty-state-icon" />
          <div class="empty-state-text">Nenhuma vacina registrada</div>
        </div>
        <div v-else>
          <div v-for="vaccine in vaccinesList" :key="vaccine.id" style="padding: var(--spacing-md); border-bottom: 1px solid var(--color-gray-200); display: flex; align-items: start; gap: 12px">
            <Icon name="syringe" :size="20" style="color: var(--color-primary); flex-shrink: 0; margin-top: 2px" />
            <div style="flex: 1">
              <div style="font-weight: 500">{{ vaccine.name }}</div>
              <div style="font-size: 0.875rem; color: var(--color-gray-600)">
                Aplicada em: {{ formatDate(vaccine.applicationDate) }}
              </div>
              <div v-if="vaccine.nextDoseDate" style="font-size: 0.875rem; color: var(--color-gray-600)">
                Próxima dose: {{ formatDate(vaccine.nextDoseDate) }}
              </div>
              <div v-if="vaccine.veterinarian" style="font-size: 0.875rem; color: var(--color-gray-600)">
                Veterinário: {{ vaccine.veterinarian }}
              </div>
            </div>
            <div style="display: flex; gap: 8px; align-items: center">
              <Button 
                variant="secondary" 
                size="sm"
                icon="pencil"
                @click="handleEditVaccine(vaccine)"
              >
                Editar
              </Button>
              <Button 
                variant="danger" 
                size="sm"
                icon="trash-2"
                @click="handleDeleteVaccine(vaccine.id)"
              >
                Excluir
              </Button>
            </div>
          </div>
        </div>
      </Card>

      <Card>
        <div class="card-header" style="display: flex; justify-content: space-between; align-items: center">
          <h3 class="card-title">Prescrições</h3>
          <Button @click="handleAddPrescription" size="sm" icon="plus">
            Adicionar
          </Button>
        </div>
        <div v-if="prescriptionsList.length === 0" class="empty-state" style="padding: var(--spacing-lg)">
          <Icon name="pill" :size="32" class="empty-state-icon" />
          <div class="empty-state-text">Nenhuma prescrição registrada</div>
        </div>
        <div v-else>
          <div v-for="prescription in prescriptionsList" :key="prescription.id" style="padding: var(--spacing-md); border-bottom: 1px solid var(--color-gray-200); display: flex; align-items: start; gap: 12px">
            <Icon name="pill" :size="20" style="color: var(--color-primary); flex-shrink: 0; margin-top: 2px" />
            <div style="flex: 1">
              <div style="font-weight: 500">{{ prescription.medication }}</div>
              <div style="font-size: 0.875rem; color: var(--color-gray-600)">
                Dosagem: {{ prescription.dosage }}
              </div>
              <div style="font-size: 0.875rem; color: var(--color-gray-600)">
                Período: {{ formatDate(prescription.startDate) }} - {{ formatDate(prescription.endDate) }}
              </div>
              <div v-if="prescription.veterinarian" style="font-size: 0.875rem; color: var(--color-gray-600)">
                Veterinário: {{ prescription.veterinarian }}
              </div>
            </div>
            <div style="display: flex; gap: 8px; align-items: center">
              <Button 
                variant="secondary" 
                size="sm"
                icon="pencil"
                @click="handleEditPrescription(prescription)"
              >
                Editar
              </Button>
              <Button 
                variant="danger" 
                size="sm"
                icon="trash-2"
                @click="handleDeletePrescription(prescription.id)"
              >
                Excluir
              </Button>
            </div>
          </div>
        </div>
      </Card>
    </div>

    <VaccineForm
      v-if="showVaccineForm"
      :pet-id="route.params.id"
      :vaccine-id="editingVaccineId"
      @close="handleCloseVaccineForm"
      @saved="handleVaccineSaved"
    />

    <PrescriptionForm
      v-if="showPrescriptionForm"
      :pet-id="route.params.id"
      :prescription-id="editingPrescriptionId"
      @close="handleClosePrescriptionForm"
      @saved="handlePrescriptionSaved"
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
import VaccineForm from '@/components/forms/VaccineForm.vue'
import PrescriptionForm from '@/components/forms/PrescriptionForm.vue'
import { usePets } from '@/composables/usePets'
import { useVaccines } from '@/composables/useVaccines'
import { usePrescriptions } from '@/composables/usePrescriptions'
import { useToast } from '@/composables/useToast'
import { useConfirmModal } from '@/composables/useConfirmModal'
import { formatters } from '@/utils/formatters'

const toast = useToast()
const confirmModal = useConfirmModal()

const route = useRoute()
const { pet, loading, error, fetchPet } = usePets()
const { vaccines, fetchVaccinesByPet, deleteVaccine } = useVaccines()
const { prescriptions, fetchPrescriptionsByPet, deletePrescription } = usePrescriptions()

const vaccinesList = ref([])
const prescriptionsList = ref([])

const showVaccineForm = ref(false)
const showPrescriptionForm = ref(false)
const editingVaccineId = ref(null)
const editingPrescriptionId = ref(null)

const handleAddVaccine = () => {
  editingVaccineId.value = null
  showVaccineForm.value = true
}

const handleEditVaccine = (vaccine) => {
  editingVaccineId.value = vaccine.id
  showVaccineForm.value = true
}

const handleDeleteVaccine = async (vaccineId) => {
  const confirmed = await confirmModal.showConfirm({
    title: 'Excluir Vacina',
    message: 'Tem certeza que deseja excluir esta vacina? Esta ação não pode ser desfeita.',
    confirmText: 'Excluir',
    cancelText: 'Cancelar',
    variant: 'danger'
  })

  if (!confirmed) {
    return
  }

  try {
    await deleteVaccine(vaccineId)
    const vaccinesData = await fetchVaccinesByPet(route.params.id)
    vaccinesList.value = vaccinesData || []
    toast.success('Vacina excluída com sucesso!')
  } catch (err) {
    console.error('Erro ao excluir vacina:', err)
    toast.error('Erro ao excluir vacina. Tente novamente.')
  }
}

const handleCloseVaccineForm = () => {
  showVaccineForm.value = false
  editingVaccineId.value = null
}

const handleVaccineSaved = async () => {
  const vaccinesData = await fetchVaccinesByPet(route.params.id)
  vaccinesList.value = vaccinesData || []
  handleCloseVaccineForm()
}

const handleAddPrescription = () => {
  editingPrescriptionId.value = null
  showPrescriptionForm.value = true
}

const handleEditPrescription = (prescription) => {
  editingPrescriptionId.value = prescription.id
  showPrescriptionForm.value = true
}

const handleDeletePrescription = async (prescriptionId) => {
  const confirmed = await confirmModal.showConfirm({
    title: 'Excluir Prescrição',
    message: 'Tem certeza que deseja excluir esta prescrição? Esta ação não pode ser desfeita.',
    confirmText: 'Excluir',
    cancelText: 'Cancelar',
    variant: 'danger'
  })

  if (!confirmed) {
    return
  }

  try {
    await deletePrescription(prescriptionId)
    const prescriptionsData = await fetchPrescriptionsByPet(route.params.id)
    prescriptionsList.value = prescriptionsData || []
    toast.success('Prescrição excluída com sucesso!')
  } catch (err) {
    console.error('Erro ao excluir prescrição:', err)
    toast.error('Erro ao excluir prescrição. Tente novamente.')
  }
}

const handleClosePrescriptionForm = () => {
  showPrescriptionForm.value = false
  editingPrescriptionId.value = null
}

const handlePrescriptionSaved = async () => {
  const prescriptionsData = await fetchPrescriptionsByPet(route.params.id)
  prescriptionsList.value = prescriptionsData || []
  handleClosePrescriptionForm()
}

const formatDate = (dateString) => {
  return formatters.date(dateString)
}

onMounted(async () => {
  const petId = route.params.id
  await fetchPet(petId)
  const vaccinesData = await fetchVaccinesByPet(petId)
  vaccinesList.value = vaccinesData || []
  const prescriptionsData = await fetchPrescriptionsByPet(petId)
  prescriptionsList.value = prescriptionsData || []
})
</script>

<style scoped>
</style>

