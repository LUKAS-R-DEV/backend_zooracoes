<template>
  <div class="card">
    <div v-if="$slots.header" class="card-header">
      <slot name="header" />
    </div>
    <slot />
  </div>
</template>

<script setup>
</script>

<style scoped>
</style>



