<template>
  <div>
    <h3>Transaktionen für {{ assetName }}</h3>
    <ul>
      <li v-for="tx in transactions" :key="tx.assetTransactionId">
        {{ tx.assetTransactionType }} - {{ tx.quantity }} @ {{ tx.price }} am {{ tx.transactionDate }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import axios from 'axios'

const props = defineProps({ accountId: String, assetName: String })
const transactions = ref([])

const loadTransactions = async () => {
  if (!props.assetName) return
  const res = await axios.get(`/account/${props.accountId}/list/assetTransactions/${props.assetName}`)
  transactions.value = res.data.assetTransactions ?? res.data
}
watch(() => props.assetName, loadTransactions, { immediate: true })
</script>