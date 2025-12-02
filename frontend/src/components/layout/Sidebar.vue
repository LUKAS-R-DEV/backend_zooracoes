<template>
  <div class="sidebar">
    <div class="sidebar-header">
      <div class="sidebar-logo">
        <Icon name="heart-pulse" :size="28" color="white" />
      </div>
      <div class="sidebar-brand">
        <div class="sidebar-title">ZooRacoes</div>
        <div class="sidebar-subtitle">Veterinária</div>
      </div>
    </div>
    <nav class="sidebar-nav">
      <router-link to="/dashboard" class="nav-item" :class="{ active: $route.path === '/dashboard' }">
        <Icon name="layout-dashboard" :size="20" class="nav-item-icon" />
        <span>Dashboard</span>
      </router-link>
      <router-link to="/tutors" class="nav-item" :class="{ active: $route.path.startsWith('/tutors') }">
        <Icon name="users" :size="20" class="nav-item-icon" />
        <span>Tutores</span>
      </router-link>
      <router-link to="/pets" class="nav-item" :class="{ active: $route.path.startsWith('/pets') }">
        <Icon name="dog" :size="20" class="nav-item-icon" />
        <span>Pets</span>
      </router-link>
      <router-link to="/schedules" class="nav-item" :class="{ active: $route.path.startsWith('/schedules') }">
        <Icon name="calendar" :size="20" class="nav-item-icon" />
        <span>Agendamentos</span>
      </router-link>
      <router-link to="/services" class="nav-item" :class="{ active: $route.path.startsWith('/services') }">
        <Icon name="stethoscope" :size="20" class="nav-item-icon" />
        <span>Serviços</span>
      </router-link>
      <router-link 
        v-if="userRole === 'ADMIN'"
        to="/users" 
        class="nav-item" 
        :class="{ active: $route.path.startsWith('/users') }"
      >
        <Icon name="users" :size="20" class="nav-item-icon" />
        <span>Usuários</span>
      </router-link>
    </nav>
    
    <div class="sidebar-footer">
      <div class="sidebar-version">v1.0.0</div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useAuth } from '@/composables/useAuth'
import Icon from '@/components/common/Icon.vue'

const { userRole } = useAuth()
</script>

<style scoped>
</style>

