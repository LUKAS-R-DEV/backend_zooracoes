// Mock data para testes sem backend

const mockUsers = [
  {
    id: 1,
    name: 'Dr. João Silva',
    email: 'admin@zooracoes.com',
    role: 'ADMIN',
    active: true,
    createdAt: '2024-01-15T10:00:00'
  },
  {
    id: 2,
    name: 'Maria Santos',
    email: 'maria@zooracoes.com',
    role: 'GESTOR',
    active: true,
    createdAt: '2024-01-20T14:30:00'
  },
  {
    id: 3,
    name: 'Pedro Oliveira',
    email: 'pedro@zooracoes.com',
    role: 'FUNCIONARIO',
    active: true,
    createdAt: '2024-02-01T09:15:00'
  },
  {
    id: 4,
    name: 'Ana Costa',
    email: 'ana@zooracoes.com',
    role: 'FUNCIONARIO',
    active: false,
    createdAt: '2024-02-10T11:20:00'
  }
]

const mockTutors = [
  {
    id: 1,
    name: 'João Silva',
    email: 'joao@example.com',
    phone: '(11) 99999-9999',
    address: 'Rua Exemplo, 123',
    active: true
  },
  {
    id: 2,
    name: 'Maria Santos',
    email: 'maria@example.com',
    phone: '(11) 88888-8888',
    address: 'Av. Teste, 456',
    active: true
  }
]

export const mockPets = [
  {
    id: 1,
    name: 'Rex',
    species: 'Cachorro',
    breed: 'Labrador',
    weight: 25.5,
    birthDate: '2020-01-15',
    tutorId: 1,
    tutorName: 'João Silva',
    active: true
  },
  {
    id: 2,
    name: 'Mimi',
    species: 'Gato',
    breed: 'Persa',
    weight: 4.2,
    birthDate: '2021-03-20',
    tutorId: 2,
    tutorName: 'Maria Santos',
    active: true
  }
]

export const mockServices = [
  {
    id: 1,
    name: 'Consulta Veterinária',
    description: 'Consulta de rotina',
    price: 150.00,
    duration: 30,
    active: true
  },
  {
    id: 2,
    name: 'Vacinação',
    description: 'Aplicação de vacinas',
    price: 80.00,
    duration: 15,
    active: true
  },
  {
    id: 3,
    name: 'Cirurgia',
    description: 'Procedimento cirúrgico',
    price: 500.00,
    duration: 120,
    active: true
  }
]

export const mockSchedules = [
  {
    id: 1,
    dateTime: new Date(Date.now() + 2 * 60 * 60 * 1000).toISOString(), // 2 horas no futuro
    petId: 1,
    petName: 'Rex',
    tutorId: 1,
    tutorName: 'João Silva',
    serviceId: 1,
    serviceName: 'Consulta Veterinária',
    status: 'PENDING',
    notes: 'Consulta de rotina'
  },
  {
    id: 2,
    dateTime: new Date(Date.now() + 5 * 60 * 60 * 1000).toISOString(), // 5 horas no futuro
    petId: 2,
    petName: 'Mimi',
    tutorId: 2,
    tutorName: 'Maria Santos',
    serviceId: 2,
    serviceName: 'Vacinação',
    status: 'PENDING',
    notes: 'Vacina anual'
  }
]

export const mockVaccines = [
  {
    id: 1,
    petId: 1,
    name: 'V8',
    applicationDate: '2024-01-15',
    nextDoseDate: '2024-02-15',
    veterinarian: 'Dr. Silva',
    batch: 'LOT123'
  }
]

export const mockPrescriptions = [
  {
    id: 1,
    petId: 1,
    medication: 'Antibiótico',
    dosage: '1 comprimido a cada 8 horas',
    startDate: '2024-01-15',
    endDate: '2024-01-22',
    veterinarian: 'Dr. Silva',
    notes: 'Tomar com alimento'
  }
]

export const mockDashboard = {
  totalTutors: 25,
  totalPets: 15,
  totalServices: 10,
  totalSchedules: 35,
  schedulesToday: 5,
  vaccinesLate: 2,
  vaccinesNext7Days: 3,
  petsBySpecies: {
    'Cachorro': 10,
    'Gato': 5
  },
  schedulesByService: {
    'Consulta': 20,
    'Vacinação': 15
  }
}

export const mockData = {
  mockUsers,
  mockTutors,
  mockPets,
  mockServices,
  mockSchedules,
  mockVaccines,
  mockPrescriptions,
  mockDashboard
}

