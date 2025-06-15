<template>
  <div v-if="account">
    <h2>Kontoübersicht</h2>
    <p>Kontoname: {{ account.accountName }}</p>
    <p>Balance: {{ account.balance }} {{ account.currencyCode }}</p>
    <p>Investiert: {{ account.investedMoney }} {{ account.currencyCode }}</p>
    <p>Gewinn/Verlust: {{ profitLoss }} {{ account.currencyCode }}</p>
  </div>
</template>

<script setup>
import { computed } from 'vue'
const props = defineProps({ account: Object })

const profitLoss = computed(() =>
    (props.account?.balance ?? 0) - (props.account?.investedMoney ?? 0)
)
</script>