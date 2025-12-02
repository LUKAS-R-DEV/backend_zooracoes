<template>
  <div class="not-found">
    <div class="not-found-content">
      <h1>404</h1>
      <p>Página não encontrada</p>
      <Button icon="arrow-left" @click="$router.push('/dashboard')">
        Voltar ao Dashboard
      </Button>
    </div>
  </div>
</template>

<script setup>
import Button from '@/components/common/Button.vue'
import Icon from '@/components/common/Icon.vue'
</script>

<style scoped>
.not-found {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-gray-50);
}

.not-found-content {
  text-align: center;
}

.not-found-content h1 {
  font-size: 6rem;
  font-weight: 700;
  color: var(--color-gray-900);
  margin-bottom: var(--spacing-md);
}

.not-found-content p {
  font-size: 1.25rem;
  color: var(--color-gray-600);
  margin-bottom: var(--spacing-xl);
}
</style>

