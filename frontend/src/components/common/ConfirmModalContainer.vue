<template>
  <Teleport to="body">
    <ConfirmModal
      :visible="modalState.visible"
      :title="modalState.title"
      :message="modalState.message"
      :confirm-text="modalState.confirmText"
      :cancel-text="modalState.cancelText"
      :variant="modalState.variant"
      :on-confirm="modalState.onConfirm"
      :on-cancel="modalState.onCancel"
      @confirm="handleConfirm"
      @cancel="handleCancel"
    />
  </Teleport>
</template>

<script setup>
import { useConfirmModal } from '@/composables/useConfirmModal'
import ConfirmModal from './ConfirmModal.vue'

const { modalState } = useConfirmModal()

const handleConfirm = () => {
  if (modalState.value.onConfirm) {
    modalState.value.onConfirm()
  }
}

const handleCancel = () => {
  if (modalState.value.onCancel) {
    modalState.value.onCancel()
  }
}
</script>

