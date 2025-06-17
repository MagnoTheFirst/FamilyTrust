<template>
  <div>
    <h2>Neues Konto erstellen</h2>
    <form @submit.prevent="submit">
      <input v-model="accountName" placeholder="Konto-Name" />
      <input v-model="currencyCode" placeholder="Währung (z.B. CHF)" />
      <button type="submit">Erstellen</button>
    </form>
  </div>
</template>

<script>
import { createAccount } from '@/api/account';

export default {
  data() {
    return {
      accountName: '',
      currencyCode: '',
      userId: 'dein-user-id-hier', // später dynamisch setzen
    };
  },
  methods: {
    async submit() {
      try {
        await createAccount(this.userId, {
          accountName: this.accountName,
          currencyCode: this.currencyCode,
          ownerUserId: this.userId
        });
        alert('Konto erfolgreich erstellt');
      } catch (err) {
        console.error(err);
        alert('Fehler beim Erstellen des Kontos');
      }
    }
  }
};
</script>
