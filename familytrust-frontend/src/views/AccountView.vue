<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">Accounts</h1>
    <div v-for="a in accounts" :key="a.id" class="mb-3 border p-4 rounded shadow">
      <div class="flex justify-between items-center">
        <div>
          <div class="font-medium text-lg">{{ a.accountName }}</div>
          <div class="text-gray-500">{{ a.currencyCode }} | {{ a.balance }} CHF</div>
        </div>
        <div class="space-x-2">
          <button @click="editAccount(a)" class="text-blue-600">Bearbeiten</button>
          <button @click="deleteAccount(a.id)" class="text-red-600">Löschen</button>
          <router-link :to="`/account/${a.id}`" class="btn btn-details">Details</router-link>
        </div>
      </div>
    </div>
    <button @click="create" class="mt-4 btn btn-details">+ Neuer Account</button>
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useAccountStore } from '../accountStore';

const store = useAccountStore();
store.userId = '123e4567-e89b-12d3-a456-426614174000';
const accounts = computed(() => store.accounts);

onMounted(() => store.fetchAccounts());

function create() {
  const name = prompt('Account Name:');
  if (name) {
    store.createAccount({ accountName: name, currencyCode: 'CHF', ownerUserId: store.userId });
  }
}

function editAccount(account) {
  const name = prompt('Neuer Name:', account.accountName);
  if (name) {
    store.updateAccount({ ...account, accountName: name });
  }
}

async function deleteAccount(id) {
  if (confirm('Account löschen?')) {
    try {
      console.log('Attempting to delete account:', id);
      await store.deleteAccount(id);
      console.log('Account deleted successfully');
    } catch (error) {
      console.error('Error deleting account:', error);
      alert('Fehler beim Löschen des Accounts: ' + (error.response?.data || error.message));
    }
  }
}
</script>

<style scoped>
button:hover {
  text-decoration: underline;
}
</style>
