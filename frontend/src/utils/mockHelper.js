// Helper para simular chamadas de API com dados mockados
import { mockData } from '../api/mockData'
import { API_CONFIG } from '../api/config'

const MOCK_MODE = API_CONFIG.mockMode // Controlado pela variável de ambiente

export const mockApiCall = async (endpoint, method = 'get', data = null) => {
  if (!MOCK_MODE) {
    return null // Retorna null para usar API real
  }

  // Simula delay de rede
  await new Promise(resolve => setTimeout(resolve, 300))

  const url = endpoint.toLowerCase()
  
  // Auth endpoints
  if (url.includes('/auth/login')) {
    return {
      data: { token: 'mock-token-' + Date.now() },
      status: 200
    }
  }
  
  if (url.includes('/auth/me')) {
    return {
      data: {
        id: 1,
        name: 'Dr. João Silva',
        email: 'admin@zooracoes.com',
        role: 'ADMIN',
        active: true,
        activeSession: true,
        lastLogin: new Date().toISOString()
      },
      status: 200
    }
  }
  
  // Users endpoints
  if (url.includes('/users')) {
    return handleUsersMock(url, method, data)
  }
  
  // Tutors endpoints
  if (url.includes('/tutors')) {
    if (method === 'get') {
      return {
        data: {
          content: mockData.mockTutors,
          page: 0,
          size: 10,
          totalElements: mockData.mockTutors.length,
          totalPages: 1
        },
        status: 200
      }
    }
    if (method === 'post' || method === 'put') {
      return {
        data: { ...data, id: data?.id || Date.now() },
        status: method === 'post' ? 201 : 200
      }
    }
    if (method === 'delete') {
      return { status: 200 }
    }
  }
  
  // Pets endpoints
  if (url.includes('/pets')) {
    if (url.includes('/tutor/')) {
      const tutorId = parseInt(url.split('/tutor/')[1])
      return {
        data: mockData.mockPets.filter(p => p.tutorId === tutorId),
        status: 200
      }
    }
    if (method === 'get') {
      if (url.match(/\/pets\/\d+$/)) {
        const id = parseInt(url.split('/pets/')[1])
        return {
          data: mockData.mockPets.find(p => p.id === id) || mockData.mockPets[0],
          status: 200
        }
      }
      return {
        data: {
          content: mockData.mockPets,
          page: 0,
          size: 10,
          totalElements: mockData.mockPets.length,
          totalPages: 1
        },
        status: 200
      }
    }
    if (method === 'post' || method === 'put') {
      return {
        data: { ...data, id: data?.id || Date.now() },
        status: method === 'post' ? 201 : 200
      }
    }
    if (method === 'delete') {
      return { status: 200 }
    }
  }
  
  // Schedules endpoints
  if (url.includes('/schedules')) {
    if (method === 'get') {
      if (url.match(/\/schedules\/\d+$/)) {
        const id = parseInt(url.split('/schedules/')[1])
        return {
          data: mockData.mockSchedules.find(s => s.id === id) || mockData.mockSchedules[0],
          status: 200
        }
      }
      return {
        data: {
          content: mockData.mockSchedules,
          page: 0,
          size: 10,
          totalElements: mockData.mockSchedules.length,
          totalPages: 1
        },
        status: 200
      }
    }
    if (method === 'post' || method === 'put') {
      return {
        data: { ...data, id: data?.id || Date.now() },
        status: method === 'post' ? 201 : 200
      }
    }
    if (method === 'delete') {
      return { status: 200 }
    }
  }
  
  // Services endpoints
  if (url.includes('/services')) {
    if (method === 'get') {
      if (url.match(/\/services\/\d+$/)) {
        const id = parseInt(url.split('/services/')[1])
        return {
          data: mockData.mockServices.find(s => s.id === id) || mockData.mockServices[0],
          status: 200
        }
      }
      return {
        data: mockData.mockServices,
        status: 200
      }
    }
    if (method === 'post' || method === 'put') {
      return {
        data: { ...data, id: data?.id || Date.now() },
        status: method === 'post' ? 201 : 200
      }
    }
    if (method === 'delete') {
      return { status: 200 }
    }
  }
  
  // Vaccines endpoints
  if (url.includes('/vaccines')) {
    if (url.includes('/pet/')) {
      const petId = parseInt(url.split('/pet/')[1])
      return {
        data: mockData.mockVaccines.filter(v => v.petId === petId),
        status: 200
      }
    }
    if (method === 'get') {
      // GET /vaccines/{id}
      const idMatch = url.match(/\/vaccines\/(\d+)$/)
      if (idMatch) {
        const id = parseInt(idMatch[1])
        const vaccine = mockData.mockVaccines.find(v => v.id === id)
        return vaccine ? { data: vaccine, status: 200 } : { data: { message: 'Vacina não encontrada' }, status: 404 }
      }
      // GET /vaccines
      return {
        data: mockData.mockVaccines,
        status: 200
      }
    }
    if (method === 'post') {
      const newVaccine = { id: mockData.mockVaccines.length + 1, ...data }
      mockData.mockVaccines.push(newVaccine)
      return {
        data: newVaccine,
        status: 201
      }
    }
    if (method === 'put') {
      const idMatch = url.match(/\/vaccines\/(\d+)$/)
      if (idMatch) {
        const id = parseInt(idMatch[1])
        const index = mockData.mockVaccines.findIndex(v => v.id === id)
        if (index !== -1) {
          mockData.mockVaccines[index] = { ...mockData.mockVaccines[index], ...data, id }
          return { data: mockData.mockVaccines[index], status: 200 }
        }
        return { data: { message: 'Vacina não encontrada' }, status: 404 }
      }
    }
    if (method === 'delete') {
      const idMatch = url.match(/\/vaccines\/(\d+)$/)
      if (idMatch) {
        const id = parseInt(idMatch[1])
        const index = mockData.mockVaccines.findIndex(v => v.id === id)
        if (index !== -1) {
          mockData.mockVaccines.splice(index, 1)
          return { status: 200, data: { message: 'Vacina removida com sucesso.' } }
        }
        return { data: { message: 'Vacina não encontrada' }, status: 404 }
      }
      return { status: 200 }
    }
  }
  
  // Prescriptions endpoints
  if (url.includes('/prescriptions')) {
    if (url.includes('/pet/')) {
      const petId = parseInt(url.split('/pet/')[1])
      return {
        data: mockData.mockPrescriptions.filter(p => p.petId === petId),
        status: 200
      }
    }
    if (method === 'get') {
      // GET /prescriptions/{id}
      const idMatch = url.match(/\/prescriptions\/(\d+)$/)
      if (idMatch) {
        const id = parseInt(idMatch[1])
        const prescription = mockData.mockPrescriptions.find(p => p.id === id)
        return prescription ? { data: prescription, status: 200 } : { data: { message: 'Prescrição não encontrada' }, status: 404 }
      }
      // GET /prescriptions
      return {
        data: mockData.mockPrescriptions,
        status: 200
      }
    }
    if (method === 'post') {
      const newPrescription = { id: mockData.mockPrescriptions.length + 1, ...data }
      mockData.mockPrescriptions.push(newPrescription)
      return {
        data: newPrescription,
        status: 201
      }
    }
    if (method === 'put') {
      const idMatch = url.match(/\/prescriptions\/(\d+)$/)
      if (idMatch) {
        const id = parseInt(idMatch[1])
        const index = mockData.mockPrescriptions.findIndex(p => p.id === id)
        if (index !== -1) {
          mockData.mockPrescriptions[index] = { ...mockData.mockPrescriptions[index], ...data, id }
          return { data: mockData.mockPrescriptions[index], status: 200 }
        }
        return { data: { message: 'Prescrição não encontrada' }, status: 404 }
      }
    }
    if (method === 'delete') {
      const idMatch = url.match(/\/prescriptions\/(\d+)$/)
      if (idMatch) {
        const id = parseInt(idMatch[1])
        const index = mockData.mockPrescriptions.findIndex(p => p.id === id)
        if (index !== -1) {
          mockData.mockPrescriptions.splice(index, 1)
          return { status: 200, data: { message: 'Prescrição removida com sucesso.' } }
        }
        return { data: { message: 'Prescrição não encontrada' }, status: 404 }
      }
      return { status: 200 }
    }
  }
  
  // Analytics endpoints
  if (url.includes('/analytics/dashboard')) {
    return {
      data: mockData.mockDashboard,
      status: 200
    }
  }
  
  return null
}

const handleUsersMock = (url, method, data) => {
  if (url === '/users' && method === 'get') {
    return {
      data: {
        content: mockData.mockUsers,
        page: 0,
        size: 10,
        totalElements: mockData.mockUsers.length,
        totalPages: 1
      },
      status: 200
    }
  }
  if (url.match(/^\/users\/\d+$/) && method === 'get') {
    const id = parseInt(url.split('/').pop())
    const user = mockData.mockUsers.find(u => u.id === id)
    return user ? { status: 200, data: user } : { status: 404, data: { message: 'Usuário não encontrado' } }
  }
  if (url === '/users' && method === 'post') {
    const newUser = { 
      id: mockData.mockUsers.length + 1, 
      ...data,
      createdAt: new Date().toISOString()
    }
    mockData.mockUsers.push(newUser)
    return { status: 201, data: newUser }
  }
  if (url.match(/^\/users\/\d+$/) && method === 'put') {
    const id = parseInt(url.split('/').pop())
    const index = mockData.mockUsers.findIndex(u => u.id === id)
    if (index !== -1) {
      mockData.mockUsers[index] = { ...mockData.mockUsers[index], ...data, id }
      return { status: 200, data: mockData.mockUsers[index] }
    }
    return { status: 404, data: { message: 'Usuário não encontrado' } }
  }
  if (url.match(/^\/users\/\d+$/) && method === 'delete') {
    const id = parseInt(url.split('/').pop())
    const index = mockData.mockUsers.findIndex(u => u.id === id)
    if (index !== -1) {
      mockData.mockUsers.splice(index, 1)
      return { status: 200, data: { message: 'Usuário removido com sucesso.' } }
    }
    return { status: 404, data: { message: 'Usuário não encontrado' } }
  }
  return { status: 404, data: { message: 'Users endpoint mockado não encontrado' } }
}

