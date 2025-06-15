<template>
  <form @submit.prevent="deposit">
    <h3>Einzahlung</h3>
    <input v-model.number="amount" type="number" min="0.01" step="0.01" placeholder="Betrag" required>
    <button type="submit">Einzahlen</button>
  </form>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const props = defineProps({ userId: String, accountId: String })
const emit = defineEmits(['deposited'])
const amount = ref(0)

const deposit = async () => {
  await axios.post('http://localhost:8084/user/account/cashFlowTransaction', {
    userId: props.userId,
    accountId: props.accountId,
    cashFlowAmount: amount.value,
    cashFlowType: "DEPOSIT"
  })
  emit('deposited')
}
</script>