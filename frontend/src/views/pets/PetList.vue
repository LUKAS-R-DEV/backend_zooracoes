<template>
  <AppLayout>
      <div class="page-header">
      <div>
        <h1 class="page-title">Pets</h1>
        <p class="page-subtitle">Gerencie todos os pets cadastrados</p>
      </div>
        <Button icon="plus" @click="$router.push('/pets/new')">
          Novo Pet
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
        <div v-if="pets.length === 0" class="empty-state">
          <Icon name="dog" :size="48" class="empty-state-icon" />
          <div class="empty-state-text">Nenhum pet cadastrado</div>
          <div class="empty-state-subtext">Comece adicionando um novo pet</div>
        </div>

        <div v-else class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>
                  <div class="table-header-content">
                    <Icon name="dog" :size="16" />
                    <span>Nome</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="paw-print" :size="16" />
                    <span>Espécie</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="tag" :size="16" />
                    <span>Raça</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="scale" :size="16" />
                    <span>Peso</span>
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
                    <Icon name="more-horizontal" :size="16" />
                    <span>Ações</span>
                  </div>
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="pet in pets" :key="pet.id">
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ pet.name }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ pet.species }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ pet.breed || '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ pet.weight ? `${pet.weight} kg` : '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ pet.tutorName || '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-actions">
                    <Button variant="secondary" size="sm" icon="eye" @click="$router.push(`/pets/${pet.id}`)">
                      Ver
                    </Button>
                    <Button variant="secondary" size="sm" icon="pencil" @click="$router.push(`/pets/${pet.id}/edit`)">
                      Editar
                    </Button>
                    <Button variant="danger" size="sm" icon="trash-2" @click="handleDelete(pet.id)">
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
import { onMounted } from 'vue'
import AppLayout from '@/components/layout/AppLayout.vue'
import Card from '@/components/common/Card.vue'
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
import Loading from '@/components/common/Loading.vue'
import { usePets } from '@/composables/usePets'
import { useToast } from '@/composables/useToast'

const toast = useToast()
const { pets, loading, error, fetchPets, deletePet } = usePets()

const handleDelete = async (id) => {
  if (confirm('Tem certeza que deseja excluir este pet?')) {
    try {
      await deletePet(id)
      await fetchPets()
      toast.success('Pet excluído com sucesso!')
    } catch (err) {
      console.error('Erro ao excluir pet:', err)
      toast.error('Erro ao excluir pet. Tente novamente.')
    }
  }
}

onMounted(() => {
  fetchPets()
})
</script>

<style scoped>
</style>

