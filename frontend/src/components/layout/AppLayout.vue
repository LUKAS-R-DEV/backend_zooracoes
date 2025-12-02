<template>
  <div class="app-layout">
    <Sidebar />
    <div class="main-content">
      <Header />
      <div class="content-area">
        <slot />
      </div>
    </div>
  </div>
</template>

<script setup>
import Sidebar from './Sidebar.vue'
import Header from './Header.vue'
</script>

<style scoped>
</style>



