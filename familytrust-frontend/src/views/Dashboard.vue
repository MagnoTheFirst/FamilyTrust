<template>
  <div class="max-w-2xl mx-auto bg-white rounded-lg shadow-lg p-6">
    <h1 class="text-2xl font-bold mb-4 text-blue-900">FamilyTrust Dashboard</h1>
    <div v-if="!account" class="text-gray-500">Bitte Konto anlegen oder auswählen.</div>
    <div v-else>
      <AccountDetails :account="account"/>
      <DepositForm :userId="userId" :accountId="account.id" @deposited="reload"/>
      <AssetBuySellForm :accountId="account.id" @changed="reload"/>
      <AssetList :userId="userId" :accountId="account.id" @selectAsset="selectAsset"/>
      <AssetTransactionList v-if="selectedAssetName" :accountId="account.id" :assetName="selectedAssetName"/>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue'
import AccountDetails from '../components/AccountDetails.vue'
import DepositForm from '../components/DepositForm.vue'
import AssetBuySellForm from '../components/AssetBuySellForm.vue'
import AssetList from '../components/AssetList.vue'
import AssetTransactionList from '../components/AssetTransactionList.vue'
import axios from 'axios'

const userId = ref('DEINE_USER_ID_HIER')
const account = ref(null)
const selectedAssetName = ref('')

const reload = async () => {
  if (account.value) {
    try {
      const res = await axios.get(`http://localhost:8084/user/${userId.value}/get/account/${account.value.id}`)
      account.value = res.data
    } catch (e) {
      alert("Fehler beim Laden des Accounts")
    }
  }
}
const selectAsset = (assetName) => selectedAssetName.value = assetName
</script>