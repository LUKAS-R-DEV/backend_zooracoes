import { reactive } from 'vue'
import { validators } from '@/utils/validators'

/**
 * Composable para validação de formulários
 * @param {Object} schema - Schema de validação com regras para cada campo
 * @returns {Object} - Funções e estado de validação
 */
export function useFormValidation(schema = {}) {
  const errors = reactive({})

  /**
   * Valida um campo específico
   * @param {string} field - Nome do campo
   * @param {any} value - Valor a ser validado
   * @param {Array} rules - Regras de validação
   * @returns {string|true} - Mensagem de erro ou true se válido
   */
  const validateField = (field, value, rules = []) => {
    if (!rules || rules.length === 0) {
      return true
    }

    for (const rule of rules) {
      let validator
      let errorMessage

      // Se a regra é uma string, buscar no validators
      if (typeof rule === 'string') {
        validator = validators[rule]
        if (!validator) {
          console.warn(`Validador "${rule}" não encontrado`)
          continue
        }
      }
      // Se é um array [validator, message]
      else if (Array.isArray(rule) && rule.length === 2) {
        const [validatorName, message] = rule
        validator = validators[validatorName]
        errorMessage = message
        if (!validator) {
          console.warn(`Validador "${validatorName}" não encontrado`)
          continue
        }
      }
      // Se é uma função
      else if (typeof rule === 'function') {
        validator = rule
      }
      else {
        continue
      }

      // Se o validador retorna uma função (ex: minLength(5))
      if (typeof validator === 'function') {
        const result = validator(value)
        if (result !== true) {
          return errorMessage || result
        }
      }
    }

    return true
  }

  /**
   * Valida todos os campos do formulário
   * @param {Object} formData - Dados do formulário
   * @param {Object} customSchema - Schema customizado (opcional)
   * @returns {boolean} - true se válido, false caso contrário
   */
  const validate = (formData, customSchema = null) => {
    const validationSchema = customSchema || schema
    
    // Limpar erros anteriores
    Object.keys(errors).forEach(key => delete errors[key])

    let isValid = true

    for (const [field, rules] of Object.entries(validationSchema)) {
      const value = formData[field]
      const result = validateField(field, value, rules)

      if (result !== true) {
        errors[field] = result
        isValid = false
      }
    }

    return isValid
  }

  /**
   * Valida um único campo
   * @param {string} field - Nome do campo
   * @param {any} value - Valor a ser validado
   * @param {Array} rules - Regras de validação (opcional, usa schema se não fornecido)
   * @returns {boolean} - true se válido
   */
  const validateSingle = (field, value, rules = null) => {
    const fieldRules = rules || schema[field] || []
    const result = validateField(field, value, fieldRules)
    
    if (result !== true) {
      errors[field] = result
      return false
    } else {
      delete errors[field]
      return true
    }
  }

  /**
   * Limpa todos os erros
   */
  const clearErrors = () => {
    Object.keys(errors).forEach(key => delete errors[key])
  }

  /**
   * Limpa erro de um campo específico
   * @param {string} field - Nome do campo
   */
  const clearError = (field) => {
    delete errors[field]
  }

  /**
   * Verifica se o formulário tem erros
   * @returns {boolean}
   */
  const hasErrors = () => {
    return Object.keys(errors).length > 0
  }

  return {
    errors,
    validate,
    validateSingle,
    clearErrors,
    clearError,
    hasErrors
  }
}


