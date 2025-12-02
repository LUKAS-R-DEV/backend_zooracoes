import { createRouter, createWebHistory } from 'vue-router'
import { authGuard } from './guards'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('@/views/dashboard/Dashboard.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/tutors',
    name: 'TutorList',
    component: () => import('@/views/tutors/TutorList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/tutors/new',
    name: 'TutorForm',
    component: () => import('@/views/tutors/TutorForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/tutors/:id/edit',
    name: 'TutorEdit',
    component: () => import('@/views/tutors/TutorForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/pets',
    name: 'PetList',
    component: () => import('@/views/pets/PetList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/pets/new',
    name: 'PetForm',
    component: () => import('@/views/pets/PetForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/pets/:id',
    name: 'PetDetail',
    component: () => import('@/views/pets/PetDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/pets/:id/edit',
    name: 'PetEdit',
    component: () => import('@/views/pets/PetForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/schedules',
    name: 'ScheduleList',
    component: () => import('@/views/schedules/ScheduleList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/schedules/new',
    name: 'ScheduleForm',
    component: () => import('@/views/schedules/ScheduleForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/schedules/:id/edit',
    name: 'ScheduleEdit',
    component: () => import('@/views/schedules/ScheduleForm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/schedules/:id',
    name: 'ScheduleDetail',
    component: () => import('@/views/schedules/ScheduleDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/services',
    name: 'ServiceList',
    component: () => import('@/views/services/ServiceList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/services/new',
    name: 'ServiceForm',
    component: () => import('@/views/services/ServiceForm.vue'),
    meta: { requiresAuth: true, requiresRole: ['ADMIN'] }
  },
  {
    path: '/services/:id/edit',
    name: 'ServiceEdit',
    component: () => import('@/views/services/ServiceForm.vue'),
    meta: { requiresAuth: true, requiresRole: ['ADMIN'] }
  },
  {
    path: '/users',
    name: 'UserList',
    component: () => import('@/views/users/UserList.vue'),
    meta: { requiresAuth: true, requiresRole: ['ADMIN'] }
  },
  {
    path: '/users/new',
    name: 'UserForm',
    component: () => import('@/views/users/UserForm.vue'),
    meta: { requiresAuth: true, requiresRole: ['ADMIN'] }
  },
  {
    path: '/users/:id/edit',
    name: 'UserEdit',
    component: () => import('@/views/users/UserForm.vue'),
    meta: { requiresAuth: true, requiresRole: ['ADMIN'] }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/profile/Profile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/prescriptions/:id',
    name: 'PrescriptionDetail',
    component: () => import('@/views/prescriptions/PrescriptionDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(authGuard)

export default router
