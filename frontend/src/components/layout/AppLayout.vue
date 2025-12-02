<template>
  <div class="app-layout">
    <Sidebar />
    <div class="main-content">
      <Header />
      <div class="content-area">
        <slot />
      </div>
    </div>
    <ConfirmModalContainer />
  </div>
</template>

<script setup>
import Sidebar from './Sidebar.vue'
import Header from './Header.vue'
import ConfirmModalContainer from '@/components/common/ConfirmModalContainer.vue'
</script>

<style scoped>
</style>





