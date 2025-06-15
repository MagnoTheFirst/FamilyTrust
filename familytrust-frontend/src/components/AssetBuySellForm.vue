<template>
  <form @submit.prevent="submit">
    <h3>Aktie kaufen/verkaufen</h3>
    <input v-model="name" placeholder="Asset-Name/Symbol" required>
    <input v-model.number="quantity" type="number" min="1" step="1" placeholder="Menge" required>
    <input v-model.number="price" type="number" min="0.01" step="0.01" placeholder="Preis" required>
    <select v-model="type">
      <option value="STOCK_BUY">Kauf</option>
      <option value="STOCK_SELL">Verkauf</option>
    </select>
    <button type="submit">Ausführen</button>
  </form>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const props = defineProps({ accountId: String })
const emit = defineEmits(['changed'])
const name = ref("")
const quantity = ref(1)
const price = ref(0)
const type = ref("STOCK_BUY")

const submit = async () => {
  await axios.post('http://localhost:8084/asset-transaction', null, {
    params: {
      assetDto: JSON.stringify({
        name: name.value,
        quantityBigDecimal: quantity.value,
        currentPrice: price.value,
        assetTransactionType: type.value,
        accountId: props.accountId
      })
    }
  })
  emit('changed')
}
</script>