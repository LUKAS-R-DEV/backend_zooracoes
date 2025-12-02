<template>
  <AppLayout>
      <div class="page-header">
      <div>
        <h1 class="page-title">Tutores</h1>
        <p class="page-subtitle">Gerencie todos os tutores cadastrados</p>
      </div>
        <Button icon="plus" @click="$router.push('/tutors/new')">
          Novo Tutor
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
        <div v-if="tutors.length === 0" class="empty-state">
          <Icon name="users" :size="48" class="empty-state-icon" />
          <div class="empty-state-text">Nenhum tutor cadastrado</div>
          <div class="empty-state-subtext">Comece adicionando um novo tutor</div>
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
                    <Icon name="phone" :size="16" />
                    <span>Telefone</span>
                  </div>
                </th>
                <th>
                  <div class="table-header-content">
                    <Icon name="map-pin" :size="16" />
                    <span>Endereço</span>
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
              <tr v-for="tutor in tutors" :key="tutor.id">
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ tutor.name }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ tutor.email }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ tutor.phone || '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-cell-content">
                    <span class="cell-value">{{ tutor.address || '-' }}</span>
                  </div>
                </td>
                <td>
                  <div class="table-actions">
                    <Button variant="secondary" size="sm" icon="pencil" @click="$router.push(`/tutors/${tutor.id}/edit`)">
                      Editar
                    </Button>
                    <Button variant="danger" size="sm" icon="trash-2" @click="handleDelete(tutor.id)">
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
import { useTutors } from '@/composables/useTutors'
import { useToast } from '@/composables/useToast'

const toast = useToast()
const { tutors, loading, error, fetchTutors, deleteTutor } = useTutors()

const handleDelete = async (id) => {
  if (confirm('Tem certeza que deseja excluir este tutor?')) {
    try {
      await deleteTutor(id)
      await fetchTutors() // Recarrega a lista após exclusão
      toast.success('Tutor excluído com sucesso!')
    } catch (err) {
      console.error('Erro ao excluir tutor:', err)
      toast.error('Erro ao excluir tutor. Tente novamente.')
    }
  }
}

onMounted(() => {
  fetchTutors()
})
</script>

<style scoped>
</style>

