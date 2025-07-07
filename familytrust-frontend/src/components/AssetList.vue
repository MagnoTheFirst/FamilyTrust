<template>
  <div class="asset-list">
    <div class="asset-list-header">
      <h3>Assets Portfolio</h3>
      <input v-model="filter" placeholder="Asset-Name filtern" class="filter-input" />
    </div>
    
    <div v-if="filteredAssets.length === 0" class="no-assets">
      <p>Keine Assets gefunden.</p>
    </div>
    
    <div v-else class="assets-grid">
      <div v-for="asset in filteredAssets" :key="asset.assetId" class="asset-card">
        <div class="asset-header">
          <h4 class="asset-name">{{ asset.name }}</h4>
          <span class="asset-type-badge" :class="`type-${asset.assetType?.toLowerCase()}`">
            {{ getAssetTypeLabel(asset.assetType) }}
          </span>
        </div>
        
        <div class="asset-details">
          <div class="detail-row">
            <span class="label">Menge:</span>
            <span class="value">{{ asset.quantity || 0 }}</span>
          </div>
          <div class="detail-row">
            <span class="label">Preis:</span>
            <span class="value">{{ formatCurrency(asset.currentPrice || 0) }}</span>
          </div>
          <div class="detail-row">
            <span class="label">Gesamtwert:</span>
            <span class="value total-value">{{ formatCurrency(asset.assetBalance || 0) }}</span>
          </div>
        </div>
        
        <div class="asset-actions">
          <button 
            @click="viewTransactions(asset.name)" 
            class="btn btn-info btn-sm"
            title="Transaktionen anzeigen"
          >
            📊 Verlauf
          </button>
          <button 
            @click="sellAsset(asset)" 
            class="btn btn-danger btn-sm"
            title="Asset verkaufen"
            :disabled="!asset.quantity || asset.quantity <= 0"
          >
            💰 Verkaufen
          </button>
        </div>
      </div>
    </div>
    
    <!-- Asset Sell Dialog -->
    <AssetSellDialog
      v-if="selectedAssetForSale"
      :asset="selectedAssetForSale"
      :account-id="accountId"
      :is-visible="showSellDialog"
      @close="closeSellDialog"
      @sold="onAssetSold"
    />
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import axios from 'axios'
import AssetSellDialog from './AssetSellDialog.vue'

const props = defineProps({ userId: String, accountId: String })
const emit = defineEmits(['selectAsset', 'assetSold'])

const assets = ref([])
const filter = ref('')
const selectedAssetForSale = ref(null)
const showSellDialog = ref(false)

const filteredAssets = computed(() =>
    assets.value.filter(a => a.name.toLowerCase().includes(filter.value.toLowerCase()))
)

const getAssetTypeLabel = (type) => {
  const labels = {
    'STOCK': 'Aktie',
    'ETF': 'ETF',
    'CRYPTO_CURRENCY': 'Krypto',
    'PHYSICAL_ASSET': 'Physisch'
  };
  return labels[type] || type;
};

const formatCurrency = (amount) => {
  if (amount === null || amount === undefined) return '0,00 €';
  return new Intl.NumberFormat('de-DE', {
    style: 'currency',
    currency: 'EUR'
  }).format(amount);
};

const loadAssets = async () => {
  try {
    const res = await axios.get(`http://localhost:8084/user/${props.userId}/account/${props.accountId}/list/assets`)
    assets.value = res.data.assets ?? res.data
  } catch (error) {
    console.error('Error loading assets:', error)
    assets.value = []
  }
}

const viewTransactions = (assetName) => {
  emit('selectAsset', assetName)
}

const sellAsset = (asset) => {
  selectedAssetForSale.value = asset
  showSellDialog.value = true
}

const closeSellDialog = () => {
  showSellDialog.value = false
  selectedAssetForSale.value = null
}

const onAssetSold = (saleData) => {
  emit('assetSold', saleData)
  loadAssets() // Reload assets after sale
}

onMounted(loadAssets)
watch([() => props.accountId, () => props.userId], loadAssets)
</script>

<style scoped>
.asset-list {
  max-width: 1200px;
  margin: 0 auto;
}

.asset-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.asset-list-header h3 {
  margin: 0;
  color: var(--text-primary, #333);
}

.filter-input {
  padding: 0.5rem 1rem;
  border: 1px solid var(--border-color, #ddd);
  border-radius: 4px;
  font-size: 1rem;
  min-width: 200px;
}

.filter-input:focus {
  outline: none;
  border-color: var(--primary-color, #007bff);
  box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

.no-assets {
  text-align: center;
  padding: 2rem;
  color: var(--text-secondary, #666);
}

.assets-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.asset-card {
  background: var(--card-background, white);
  border: 1px solid var(--border-color, #ddd);
  border-radius: 8px;
  padding: 1.5rem;
  transition: box-shadow 0.2s ease, transform 0.2s ease;
}

.asset-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.asset-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1rem;
}

.asset-name {
  margin: 0;
  font-size: 1.25rem;
  color: var(--text-primary, #333);
  flex: 1;
}

.asset-type-badge {
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 600;
  color: white;
  white-space: nowrap;
}

.type-stock { background-color: #10b981; }
.type-etf { background-color: #3b82f6; }
.type-crypto_currency { background-color: #f59e0b; }
.type-physical_asset { background-color: #64748b; }

.asset-details {
  margin-bottom: 1.5rem;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
}

.detail-row:last-child {
  margin-bottom: 0;
}

.label {
  color: var(--text-secondary, #666);
  font-weight: 500;
}

.value {
  color: var(--text-primary, #333);
  font-weight: 600;
}

.total-value {
  color: var(--success-color, #28a745);
  font-size: 1.1rem;
}

.asset-actions {
  display: flex;
  gap: 0.5rem;
  justify-content: flex-end;
}

.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-sm {
  padding: 0.375rem 0.75rem;
  font-size: 0.8rem;
}

.btn-info {
  background-color: var(--info-color, #17a2b8);
  color: white;
}

.btn-info:hover:not(:disabled) {
  background-color: var(--info-color-dark, #138496);
}

.btn-danger {
  background-color: var(--danger-color, #dc3545);
  color: white;
}

.btn-danger:hover:not(:disabled) {
  background-color: var(--danger-color-dark, #c82333);
}

@media (max-width: 768px) {
  .assets-grid {
    grid-template-columns: 1fr;
  }
  
  .asset-list-header {
    flex-direction: column;
    align-items: stretch;
  }
  
  .filter-input {
    min-width: 100%;
  }
}
</style>