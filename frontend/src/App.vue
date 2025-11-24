<template>
  <div id="app" class="min-h-screen">
    <nav class="bg-gradient-to-r from-primary-500 to-purple-600 text-white py-4 shadow-lg">
      <div class="container mx-auto px-8 max-w-7xl flex justify-between items-center">
        <h1 class="text-2xl font-bold m-0">🐾 ZooRacoes</h1>
        <div class="flex gap-8">
          <router-link 
            to="/" 
            class="text-white font-medium hover:opacity-80 transition-opacity duration-300"
            :class="{ 'underline': $route.path === '/' }"
          >
            Home
          </router-link>
          <router-link 
            to="/about" 
            class="text-white font-medium hover:opacity-80 transition-opacity duration-300"
            :class="{ 'underline': $route.path === '/about' }"
          >
            Sobre
          </router-link>
        </div>
      </div>
    </nav>
    <main class="container mx-auto px-8 py-8 max-w-7xl">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useApi } from './services/api'

onMounted(async () => {
  try {
    const response = await useApi().get('/api/health')
    console.log('Backend conectado:', response.data)
  } catch (error) {
    console.warn('Backend não está rodando ainda:', error.message)
  }
})
</script>

