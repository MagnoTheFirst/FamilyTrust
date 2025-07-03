import { defineStore } from 'pinia';
import { api } from './api';

export const useAccountStore = defineStore('accountStore', {
    state: () => ({ 
        accounts: [], 
        selectedAccount: null,
        userId: '123e4567-e89b-12d3-a456-426614174000', // Default test user ID
        loading: false,
        error: null,
        lastFetch: null,
        cache: new Map() // Cache für API-Responses
    }),
    
    getters: {
        getAccountById: (state) => (accountId) => {
            return state.accounts.find(account => account.id === accountId);
        },
        totalBalance: (state) => {
            return state.accounts.reduce((sum, account) => sum + (account.balance || 0), 0);
        }
    },
    
    actions: {
        async fetchAccounts(forceRefresh = false) {
            // Cache-Check (5 Minuten gültig)
            const cacheKey = `accounts-${this.userId}`;
            const cached = this.cache.get(cacheKey);
            const now = Date.now();
            
            if (!forceRefresh && cached && (now - cached.timestamp < 300000)) {
                this.accounts = cached.data;
                return;
            }
            
            this.loading = true;
            this.error = null;
            try {
                const res = await api.getAccounts(this.userId);
                this.accounts = res.data || [];
                this.lastFetch = now;
                
                // Cache setzen
                this.cache.set(cacheKey, {
                    data: this.accounts,
                    timestamp: now
                });
            } catch (error) {
                this.error = 'Fehler beim Laden der Accounts';
                console.error('Error fetching accounts:', error);
            } finally {
                this.loading = false;
            }
        },
        
        async fetchAccount(accountId) {
            this.loading = true;
            try {
                const res = await api.getAccount(this.userId, accountId);
                this.selectedAccount = res.data;
                return res.data;
            } catch (error) {
                this.error = 'Fehler beim Laden des Accounts';
                console.error('Error fetching account:', error);
            } finally {
                this.loading = false;
            }
        },
        
        async createAccount(data) {
            this.loading = true;
            try {
                await api.createAccount(this.userId, {
                    ...data,
                    ownerUserId: this.userId
                });
                await this.fetchAccounts();
            } catch (error) {
                this.error = 'Fehler beim Erstellen des Accounts';
                throw error;
            } finally {
                this.loading = false;
            }
        },
        
        async updateAccount(data) {
            this.loading = true;
            try {
                await api.updateAccount(this.userId, data);
                await this.fetchAccounts();
            } catch (error) {
                this.error = 'Fehler beim Aktualisieren des Accounts';
                throw error;
            } finally {
                this.loading = false;
            }
        },
        
        async deleteAccount(accountId) {
            this.loading = true;
            try {
                await api.deleteAccount(this.userId, { 
                    userId: this.userId, 
                    accountId 
                });
                await this.fetchAccounts();
            } catch (error) {
                this.error = 'Fehler beim Löschen des Accounts';
                throw error;
            } finally {
                this.loading = false;
            }
        },
        
        async addCashFlow(cashFlowData) {
            this.loading = true;
            try {
                await api.postCashflow({
                    ...cashFlowData,
                    userId: this.userId
                });
                await this.fetchAccounts();
            } catch (error) {
                this.error = 'Fehler beim Hinzufügen der Transaktion';
                throw error;
            } finally {
                this.loading = false;
            }
        }
    },
});
