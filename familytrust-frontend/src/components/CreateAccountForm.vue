<template>
  <form @submit.prevent="create">
    <h2>Konto anlegen</h2>
    <input v-model="accountName" placeholder="Konto-Name" required>
    <input v-model="currencyCode" placeholder="Währung (z.B. EUR)" required>
    <input v-model="ownerUserId" placeholder="User-ID" required>
    <button type="submit">Anlegen</button>
  </form>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'

const accountName = ref("")
const currencyCode = ref("")
const ownerUserId = ref("")
const emit = defineEmits(['created'])

const create = async () => {
  const res = await axios.post('http://localhost:8084/user/create/account/' + ownerUserId.value, {
    accountName: accountName.value,
    currencyCode: currencyCode.value,
    ownerUserId: ownerUserId.value
  })
  emit('created', res.data)
}
</script>