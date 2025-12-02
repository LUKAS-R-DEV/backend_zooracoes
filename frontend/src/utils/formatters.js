export const formatters = {
  date: (dateString) => {
    if (!dateString) return '-'
    const date = new Date(dateString)
    return date.toLocaleDateString('pt-BR')
  },

  dateTime: (dateString) => {
    if (!dateString) return '-'
    const date = new Date(dateString)
    return date.toLocaleString('pt-BR')
  },

  currency: (value) => {
    if (!value) return 'R$ 0,00'
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value)
  },

  phone: (phone) => {
    if (!phone) return '-'
    return phone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3')
  }
}



