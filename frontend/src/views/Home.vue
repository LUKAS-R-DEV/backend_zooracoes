<template>
  <div class="animate-fade-in">
    <div class="text-center py-12 bg-gradient-to-r from-primary-500 to-purple-600 text-white rounded-xl mb-8">
      <h1 class="text-4xl font-bold mb-4">Bem-vindo ao ZooRacoes! 🦁</h1>
      <p class="text-xl opacity-90">Sistema de gestão para rações de zoológico</p>
    </div>

    <div class="card mb-8">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800">Status da Conexão</h2>
      <div class="flex items-center gap-4 text-lg font-medium">
        <span 
          class="w-3 h-3 rounded-full animate-pulse"
          :class="isConnected ? 'bg-green-500' : 'bg-red-500'"
        ></span>
        <span>{{ statusMessage }}</span>
      </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div class="card hover:-translate-y-1 hover:shadow-xl transition-all duration-300">
        <h3 class="text-xl font-semibold mb-2 text-primary-500">📦 Gestão de Rações</h3>
        <p class="text-gray-600">Controle completo de estoque e distribuição</p>
      </div>
      <div class="card hover:-translate-y-1 hover:shadow-xl transition-all duration-300">
        <h3 class="text-xl font-semibold mb-2 text-primary-500">🦒 Controle de Animais</h3>
        <p class="text-gray-600">Gerencie informações dos animais do zoológico</p>
      </div>
      <div class="card hover:-translate-y-1 hover:shadow-xl transition-all duration-300">
        <h3 class="text-xl font-semibold mb-2 text-primary-500">📊 Relatórios</h3>
        <p class="text-gray-600">Gere relatórios detalhados e análises</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useApi } from '../services/api'

const isConnected = ref(false)
const statusMessage = ref('Verificando conexão...')

const checkConnection = async () => {
  try {
    const response = await useApi().get('/api/health')
    isConnected.value = true
    statusMessage.value = `Conectado ao backend! ✅ - ${response.data.message || ''}`
  } catch (error) {
    isConnected.value = false
    statusMessage.value = 'Backend não está rodando ⚠️'
  }
}

onMounted(() => {
  checkConnection()
  setInterval(checkConnection, 5000)
})
</script>

<style scoped>
@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in {
  animation: fade-in 0.5s ease-in;
}
</style>

