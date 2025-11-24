<template>
  <div class="max-w-4xl mx-auto animate-fade-in">
    <h1 class="text-4xl font-bold text-primary-500 mb-8">Sobre o ZooRacoes</h1>
    <div class="card">
      <p class="text-lg leading-relaxed text-gray-700 mb-8">
        Sistema desenvolvido para gerenciar rações e alimentação dos animais
        do zoológico de forma eficiente e organizada.
      </p>
      <div class="mt-8">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">Stack Tecnológico</h2>
        <div class="flex flex-col gap-4">
          <div class="p-4 bg-gray-50 rounded-lg border-l-4 border-primary-500">
            <strong class="text-primary-500">Backend:</strong> Spring Boot 3.5.7
          </div>
          <div class="p-4 bg-gray-50 rounded-lg border-l-4 border-primary-500">
            <strong class="text-primary-500">Frontend:</strong> Vue.js 3 + Vite + Tailwind CSS
          </div>
          <div class="p-4 bg-gray-50 rounded-lg border-l-4 border-primary-500">
            <strong class="text-primary-500">Comunicação:</strong> REST API + Axios
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
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

