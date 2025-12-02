export const validators = {
  required: (value) => {
    if (!value || (typeof value === 'string' && !value.trim())) {
      return 'Este campo é obrigatório'
    }
    return true
  },

  email: (value) => {
    if (!value) return true
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailRegex.test(value)) {
      return 'Email inválido'
    }
    return true
  },

  minLength: (min) => (value) => {
    if (!value) return true
    if (value.length < min) {
      return `Mínimo de ${min} caracteres`
    }
    return true
  },

  maxLength: (max) => (value) => {
    if (!value) return true
    if (value.length > max) {
      return `Máximo de ${max} caracteres`
    }
    return true
  },

  phone: (value) => {
    if (!value) return true
    const phoneRegex = /^[\d\s\(\)\-]+$/
    const digits = value.replace(/\D/g, '')
    if (!phoneRegex.test(value) || digits.length < 10 || digits.length > 11) {
      return 'Telefone inválido (mínimo 10 dígitos)'
    }
    return true
  },

  positiveNumber: (value) => {
    if (!value && value !== 0) return true
    const num = typeof value === 'string' ? parseFloat(value) : value
    if (isNaN(num) || num < 0) {
      return 'Deve ser um número positivo'
    }
    return true
  },

  minValue: (min) => (value) => {
    if (!value && value !== 0) return true
    const num = typeof value === 'string' ? parseFloat(value) : value
    if (isNaN(num) || num < min) {
      return `Valor mínimo: ${min}`
    }
    return true
  },

  maxValue: (max) => (value) => {
    if (!value && value !== 0) return true
    const num = typeof value === 'string' ? parseFloat(value) : value
    if (isNaN(num) || num > max) {
      return `Valor máximo: ${max}`
    }
    return true
  },

  integer: (value) => {
    if (!value && value !== 0) return true
    const num = typeof value === 'string' ? parseInt(value) : value
    if (isNaN(num) || !Number.isInteger(num)) {
      return 'Deve ser um número inteiro'
    }
    return true
  },

  date: (value) => {
    if (!value) return true
    const date = new Date(value)
    if (isNaN(date.getTime())) {
      return 'Data inválida'
    }
    return true
  },

  dateNotFuture: (value) => {
    if (!value) return true
    const date = new Date(value)
    const today = new Date()
    today.setHours(23, 59, 59, 999)
    if (isNaN(date.getTime())) {
      return 'Data inválida'
    }
    if (date > today) {
      return 'Data não pode ser futura'
    }
    return true
  },

  dateNotPast: (value) => {
    if (!value) return true
    const date = new Date(value)
    const today = new Date()
    today.setHours(0, 0, 0, 0)
    if (isNaN(date.getTime())) {
      return 'Data inválida'
    }
    if (date < today) {
      return 'Data não pode ser passada'
    }
    return true
  },

  dateAfter: (otherDate) => (value) => {
    if (!value) return true
    if (!otherDate) return true
    const date = new Date(value)
    const other = new Date(otherDate)
    if (isNaN(date.getTime()) || isNaN(other.getTime())) {
      return 'Data inválida'
    }
    if (date <= other) {
      return 'Data deve ser posterior à data de início'
    }
    return true
  },

  password: (value) => {
    if (!value) return true
    if (value.length < 6) {
      return 'Senha deve ter no mínimo 6 caracteres'
    }
    return true
  },

  url: (value) => {
    if (!value) return true
    try {
      new URL(value)
      return true
    } catch {
      return 'URL inválida'
    }
  },

  select: (value) => {
    if (!value || value === '') {
      return 'Selecione uma opção'
    }
    return true
  }
}


