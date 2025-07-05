<template>
  <div>
    <h2>Konto: {{ account.accountName }}</h2>
    <p>Kontostand: {{ account.balance }} {{ account.currencyCode }}</p>
    <p>Investiert: {{ account.investedMoney }} {{ account.currencyCode }}</p>
    <p>Verfügbares Kapital: {{ account.availableMoney }} {{ account.currencyCode }}</p>
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