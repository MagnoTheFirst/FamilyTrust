<template>
  <div style="max-width:800px;margin:auto">
    <h1>FamilyTrust Dashboard</h1>
    <CreateAccountForm @created="onAccountCreated"/>
    <div v-if="account">
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
import CreateAccountForm from '../components/CreateAccountForm.vue'
import AccountDetails from '../components/AccountDetails.vue'
import DepositForm from '../components/DepositForm.vue'
import AssetBuySellForm from '../components/AssetBuySellForm.vue'
import AssetList from '../components/AssetList.vue'
import AssetTransactionList from '../components/AssetTransactionList.vue'
import axios from 'axios'

const userId = ref('123e4567-e89b-12d3-a456-426614174000')
const account = ref({ id: 'bfbee9f9-32fe-45be-b5bd-04845fd1a926' })
const selectedAssetName = ref('')

const reload = async () => {
  if (account.value && account.value.id) {
    const res = await axios.get(`http://localhost:8084/user/${userId.value}/get/account/${account.value.id}`)
    account.value = res.data
  }
}
const selectAsset = (assetName) => {
  selectedAssetName.value = assetName
}
const onAccountCreated = (acc) => {
  account.value = acc
  reload()
}
</script>