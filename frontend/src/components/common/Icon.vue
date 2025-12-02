<template>
  <component 
    :is="iconComponent" 
    :size="size" 
    :stroke-width="strokeWidth" 
    :color="color"
    :class="iconClass" 
  />
</template>

<script setup>
import { computed } from 'vue'
import * as LucideIcons from 'lucide-vue-next'

const props = defineProps({
  name: {
    type: String,
    required: true
  },
  size: {
    type: [Number, String],
    default: 20
  },
  strokeWidth: {
    type: [Number, String],
    default: 1.5
  },
  color: {
    type: String,
    default: 'currentColor'
  },
  class: {
    type: String,
    default: ''
  }
})

const iconClass = computed(() => props.class)

const iconComponent = computed(() => {
  // Converte kebab-case para PascalCase
  let iconName = props.name
    .split('-')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1))
    .join('')
  
  // Tenta encontrar o ícone
  let icon = LucideIcons[iconName]
  
  // Se não encontrar, tenta algumas variações comuns
  if (!icon) {
    // LayoutDashboard -> Layout
    if (iconName === 'LayoutDashboard') {
      icon = LucideIcons['Layout'] || LucideIcons['LayoutGrid']
    }
    // HeartPulse -> Heart ou Activity
    else if (iconName === 'HeartPulse') {
      icon = LucideIcons['Activity'] || LucideIcons['Heart']
    }
    // Stethoscope -> pode não existir, usar Activity ou Heart ou Activity
    else if (iconName === 'Stethoscope') {
      icon = LucideIcons['Activity'] || LucideIcons['Heart'] || LucideIcons['Cross']
    }
    // CalendarClock -> Calendar ou Clock
    else if (iconName === 'CalendarClock') {
      icon = LucideIcons['CalendarClock'] || LucideIcons['Calendar'] || LucideIcons['Clock']
    }
    // CalendarX -> Calendar ou X
    else if (iconName === 'CalendarX') {
      icon = LucideIcons['CalendarX'] || LucideIcons['Calendar'] || LucideIcons['X']
    }
    // ClipboardList -> Clipboard ou List
    else if (iconName === 'ClipboardList') {
      icon = LucideIcons['ClipboardList'] || LucideIcons['Clipboard'] || LucideIcons['List']
    }
    // Syringe -> Syringe ou Activity
    else if (iconName === 'Syringe') {
      icon = LucideIcons['Syringe'] || LucideIcons['Activity'] || LucideIcons['Cross']
    }
    // Pill -> Pill ou Activity
    else if (iconName === 'Pill') {
      icon = LucideIcons['Pill'] || LucideIcons['Circle'] || LucideIcons['Activity']
    }
    // AlertCircle -> AlertCircle ou AlertTriangle
    else if (iconName === 'AlertCircle') {
      icon = LucideIcons['AlertCircle'] || LucideIcons['AlertTriangle'] || LucideIcons['Alert']
    }
    // AlertTriangle -> AlertTriangle ou AlertCircle
    else if (iconName === 'AlertTriangle') {
      icon = LucideIcons['AlertTriangle'] || LucideIcons['AlertCircle'] || LucideIcons['Alert']
    }
    // ArrowLeft -> ArrowLeft ou ChevronLeft
    else if (iconName === 'ArrowLeft') {
      icon = LucideIcons['ArrowLeft'] || LucideIcons['ChevronLeft']
    }
    // LogIn -> LogIn ou ArrowRight
    else if (iconName === 'LogIn') {
      icon = LucideIcons['LogIn'] || LucideIcons['ArrowRight'] || LucideIcons['Login']
    }
    // LogOut -> LogOut ou ArrowLeft
    else if (iconName === 'LogOut') {
      icon = LucideIcons['LogOut'] || LucideIcons['ArrowLeft'] || LucideIcons['Logout']
    }
    // Trash2 -> Trash2 ou Trash
    else if (iconName === 'Trash2') {
      icon = LucideIcons['Trash2'] || LucideIcons['Trash']
    }
    // PawPrint -> PawPrint ou Circle
    else if (iconName === 'PawPrint') {
      icon = LucideIcons['PawPrint'] || LucideIcons['Circle'] || LucideIcons['Paw']
    }
    // CalendarDays -> CalendarDays ou Calendar
    else if (iconName === 'CalendarDays') {
      icon = LucideIcons['CalendarDays'] || LucideIcons['Calendar'] || LucideIcons['CalendarClock']
    }
    // DollarSign -> DollarSign ou Circle
    else if (iconName === 'DollarSign') {
      icon = LucideIcons['DollarSign'] || LucideIcons['Circle'] || LucideIcons['Dollar']
    }
    // Hash -> Hash ou PoundSign
    else if (iconName === 'Hash') {
      icon = LucideIcons['Hash'] || LucideIcons['PoundSign'] || LucideIcons['Hashtag']
    }
    // FileText -> FileText ou File
    else if (iconName === 'FileText') {
      icon = LucideIcons['FileText'] || LucideIcons['File'] || LucideIcons['Document']
    }
    // MapPin -> MapPin ou Map
    else if (iconName === 'MapPin') {
      icon = LucideIcons['MapPin'] || LucideIcons['Map'] || LucideIcons['Pin']
    }
    // Tag -> Tag ou Label
    else if (iconName === 'Tag') {
      icon = LucideIcons['Tag'] || LucideIcons['Label'] || LucideIcons['Tags']
    }
    // Scale -> Scale ou Weight
    else if (iconName === 'Scale') {
      icon = LucideIcons['Scale'] || LucideIcons['Weight'] || LucideIcons['Activity']
    }
    // Loader2 -> Loader ou Loader2
    else if (iconName === 'Loader2') {
      icon = LucideIcons['Loader2'] || LucideIcons['Loader'] || LucideIcons['RefreshCw']
    }
    // EyeOff -> EyeOff
    else if (iconName === 'EyeOff') {
      icon = LucideIcons['EyeOff'] || LucideIcons['Eye']
    }
    // XCircle -> XCircle ou X
    else if (iconName === 'XCircle') {
      icon = LucideIcons['XCircle'] || LucideIcons['X']
    }
    // CheckCircle -> CheckCircle ou Check
    else if (iconName === 'CheckCircle') {
      icon = LucideIcons['CheckCircle'] || LucideIcons['Check']
    }
  }
  
  return icon || LucideIcons['HelpCircle']
})
</script>

