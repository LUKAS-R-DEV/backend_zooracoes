<template>
  <div class="header">
    <div class="header-left">
      <div class="header-title-section">
        <Icon :name="currentPageIcon" :size="24" class="header-page-icon" />
        <div>
          <h1 class="header-title">{{ currentPageTitle }}</h1>
          <p class="header-subtitle" v-if="currentPageSubtitle">{{ currentPageSubtitle }}</p>
        </div>
      </div>
    </div>
    
    <div class="header-right">
      <div class="header-actions">
        <div class="header-user" @click="showUserMenu = !showUserMenu" v-click-outside="() => showUserMenu = false">
          <div class="user-avatar">
            <span>{{ userInitials }}</span>
          </div>
          <div class="user-info">
            <span class="user-name">{{ user?.name || 'Usuário' }}</span>
            <span class="user-role">{{ userRoleLabel }}</span>
          </div>
          <Icon name="chevron-down" :size="16" class="user-chevron" :class="{ open: showUserMenu }" />
          
          <div v-if="showUserMenu" class="user-menu">
            <div class="user-menu-item" @click="handleProfileClick">
              <Icon name="user" :size="16" />
              <span>Meu Perfil</span>
            </div>
            <div class="user-menu-divider"></div>
            <div class="user-menu-item logout" @click="handleLogout">
              <Icon name="log-out" :size="16" />
              <span>Sair</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuth } from '@/composables/useAuth'
import { USER_ROLE_LABELS } from '@/utils/constants'
import Icon from '@/components/common/Icon.vue'

const route = useRoute()
const router = useRouter()
const showUserMenu = ref(false)
const { user, logout } = useAuth()

// Configuração de títulos e ícones por rota
const pageConfig = {
  '/dashboard': {
    title: 'Dashboard',
    subtitle: 'Visão geral do sistema',
    icon: 'layout-dashboard'
  },
  '/tutors': {
    title: 'Tutores',
    subtitle: 'Gerencie todos os tutores',
    icon: 'users',
    editTitle: 'Editar Tutor',
    editSubtitle: 'Edite as informações do tutor',
    newTitle: 'Novo Tutor',
    newSubtitle: 'Cadastre um novo tutor',
    newIcon: 'user-plus'
  },
  '/pets': {
    title: 'Pets',
    subtitle: 'Gerencie todos os pets',
    icon: 'dog',
    editTitle: 'Editar Pet',
    editSubtitle: 'Edite as informações do pet',
    newTitle: 'Novo Pet',
    newSubtitle: 'Cadastre um novo pet',
    newIcon: 'plus-circle',
    detailTitle: 'Detalhes do Pet',
    detailSubtitle: 'Visualize todas as informações'
  },
  '/schedules': {
    title: 'Agendamentos',
    subtitle: 'Gerencie os agendamentos',
    icon: 'calendar',
    editTitle: 'Editar Agendamento',
    editSubtitle: 'Edite as informações do agendamento',
    newTitle: 'Novo Agendamento',
    newSubtitle: 'Crie um novo agendamento',
    newIcon: 'calendar-plus'
  },
  '/services': {
    title: 'Serviços',
    subtitle: 'Gerencie os serviços disponíveis',
    icon: 'stethoscope',
    editTitle: 'Editar Serviço',
    editSubtitle: 'Edite as informações do serviço',
    newTitle: 'Novo Serviço',
    newSubtitle: 'Cadastre um novo serviço',
    newIcon: 'plus-circle'
  },
  '/users': {
    title: 'Usuários',
    subtitle: 'Gerencie todos os usuários do sistema',
    icon: 'users',
    editTitle: 'Editar Usuário',
    editSubtitle: 'Edite as informações do usuário',
    newTitle: 'Novo Usuário',
    newSubtitle: 'Cadastre um novo usuário',
    newIcon: 'user-plus'
  },
  '/profile': {
    title: 'Meu Perfil',
    subtitle: 'Visualize e gerencie suas informações',
    icon: 'user'
  }
}

const currentPageConfig = computed(() => {
  const path = route.path
  
  // Verifica rota exata primeiro
  if (pageConfig[path]) {
    return pageConfig[path]
  }
  
  // Verifica rotas que começam com o path base
  for (const [basePath, config] of Object.entries(pageConfig)) {
    if (path.startsWith(basePath) && basePath !== '/') {
      // Rota de edição: /tutors/:id/edit, /pets/:id/edit, etc
      if (path.includes('/edit')) {
        return {
          title: config.editTitle || `Editar ${config.title}`,
          subtitle: config.editSubtitle || 'Edite as informações',
          icon: config.icon
        }
      }
      // Rota de novo: /tutors/new, /pets/new, etc
      if (path.endsWith('/new')) {
        return {
          title: config.newTitle || `Novo ${config.title}`,
          subtitle: config.newSubtitle || 'Cadastre um novo registro',
          icon: config.newIcon || config.icon
        }
      }
      // Rota de detalhes: /pets/:id (sem /edit)
      if (path.match(/\/\d+$/) && config.detailTitle) {
        return {
          title: config.detailTitle,
          subtitle: config.detailSubtitle || 'Visualize os detalhes',
          icon: config.icon
        }
      }
      // Retorna a configuração base
      return config
    }
  }
  
  // Fallback padrão
  return {
    title: 'ZooRacoes',
    subtitle: 'Sistema de gestão veterinária',
    icon: 'heart-pulse'
  }
})

const currentPageTitle = computed(() => currentPageConfig.value.title)
const currentPageSubtitle = computed(() => currentPageConfig.value.subtitle)
const currentPageIcon = computed(() => currentPageConfig.value.icon)

const handleProfileClick = () => {
  router.push('/profile')
  showUserMenu.value = false
}

const userInitials = computed(() => {
  if (!user.value?.name) return 'U'
  const names = user.value.name.split(' ')
  if (names.length >= 2) {
    return (names[0][0] + names[names.length - 1][0]).toUpperCase()
  }
  return user.value.name.substring(0, 2).toUpperCase()
})

const userRoleLabel = computed(() => {
  if (!user.value?.role) return ''
  return USER_ROLE_LABELS[user.value.role] || user.value.role
})

const handleLogout = () => {
  logout()
  showUserMenu.value = false
}

// Diretiva para fechar menu ao clicar fora
const vClickOutside = {
  mounted(el, binding) {
    el.clickOutsideEvent = (event) => {
      if (!(el === event.target || el.contains(event.target))) {
        binding.value()
      }
    }
    document.addEventListener('click', el.clickOutsideEvent)
  },
  unmounted(el) {
    document.removeEventListener('click', el.clickOutsideEvent)
  }
}
</script>

<style scoped>
.header {
  background: linear-gradient(135deg, #ffffff 0%, #f9fafb 100%);
  border-bottom: 1px solid var(--color-gray-200);
  padding: 0 var(--spacing-xl);
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  z-index: 50;
  box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.05);
  height: 70px;
}

.header-left {
  flex: 1;
  display: flex;
  align-items: center;
}

.header-title-section {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-page-icon {
  color: var(--color-primary);
  flex-shrink: 0;
}

.header-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--color-gray-900);
  margin: 0;
  line-height: 1.2;
}

.header-subtitle {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin: 0;
  margin-top: 2px;
}

.header-right {
  display: flex;
  align-items: center;
}

.header-actions {
  display: flex;
  align-items: center;
}

.header-user {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 12px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.2s ease;
  position: relative;
}

.header-user:hover {
  background-color: var(--color-gray-50);
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 12px;
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 0.875rem;
  box-shadow: 0 2px 8px rgba(34, 197, 94, 0.2);
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.user-name {
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--color-gray-900);
  line-height: 1.2;
}

.user-role {
  font-size: 0.75rem;
  color: var(--color-gray-500);
  line-height: 1.2;
}

.user-chevron {
  color: var(--color-gray-400);
  transition: transform 0.2s ease;
}

.user-chevron.open {
  transform: rotate(180deg);
}

.user-menu-item svg {
  flex-shrink: 0;
}

.user-menu {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  border: 1px solid var(--color-gray-200);
  min-width: 200px;
  padding: 8px;
  z-index: 1000;
  animation: slideDown 0.2s ease;
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.user-menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 12px;
  border-radius: 8px;
  color: var(--color-gray-700);
  font-size: 0.875rem;
  cursor: pointer;
  transition: all 0.15s ease;
}

.user-menu-item:hover {
  background-color: var(--color-gray-50);
  color: var(--color-gray-900);
}

.user-menu-item.logout {
  color: var(--color-error);
}

.user-menu-item.logout:hover {
  background-color: #fee2e2;
  color: #991b1b;
}

.user-menu-divider {
  height: 1px;
  background-color: var(--color-gray-200);
  margin: 8px 0;
}

@media (max-width: 768px) {
  .header-title {
    font-size: 1.25rem;
  }
  
  .header-subtitle {
    display: none;
  }
  
  .header-page-icon {
    display: none;
  }
  
  .user-info {
    display: none;
  }
  
  .user-chevron {
    display: none;
  }
}
</style>

