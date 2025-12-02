/**
 * Aplica máscara a um valor baseado no padrão fornecido
 * @param {string} value - Valor a ser mascarado
 * @param {string} pattern - Padrão da máscara (ex: "(##) #####-####" ou "phone")
 * @returns {string} - Valor com máscara aplicada
 */
export function applyMask(value, pattern) {
  if (!value || !pattern) return value || ''
  
  // Remove todos os caracteres não numéricos
  const numbers = value.replace(/\D/g, '')
  
  // Se o padrão for "phone", aplica máscara de telefone brasileiro
  if (pattern === 'phone') {
    if (numbers.length === 0) return ''
    
    if (numbers.length <= 10) {
      // Telefone fixo: (##) ####-####
      if (numbers.length <= 2) {
        return `(${numbers}`
      } else if (numbers.length <= 6) {
        return `(${numbers.slice(0, 2)}) ${numbers.slice(2)}`
      } else {
        return `(${numbers.slice(0, 2)}) ${numbers.slice(2, 6)}-${numbers.slice(6, 10)}`
      }
    } else {
      // Celular: (##) #####-####
      if (numbers.length <= 2) {
        return `(${numbers}`
      } else if (numbers.length <= 7) {
        return `(${numbers.slice(0, 2)}) ${numbers.slice(2)}`
      } else {
        return `(${numbers.slice(0, 2)}) ${numbers.slice(2, 7)}-${numbers.slice(7, 11)}`
      }
    }
  }
  
  // Máscara customizada com #
  let masked = ''
  let numberIndex = 0
  
  for (let i = 0; i < pattern.length && numberIndex < numbers.length; i++) {
    if (pattern[i] === '#') {
      masked += numbers[numberIndex]
      numberIndex++
    } else {
      masked += pattern[i]
    }
  }
  
  return masked
}

/**
 * Remove a máscara de um valor
 * @param {string} value - Valor com máscara
 * @returns {string} - Valor sem máscara (apenas números)
 */
export function removeMask(value) {
  if (!value) return ''
  return value.replace(/\D/g, '')
}

