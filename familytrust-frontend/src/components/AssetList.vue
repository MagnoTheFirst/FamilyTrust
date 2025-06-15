<template>
  <div>
    <input v-model="filter" placeholder="Asset-Name filtern" />
    <ul>
      <li v-for="asset in filteredAssets" :key="asset.assetId">
        {{ asset.name }} - {{ asset.assetBalance }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import axios from 'axios'

const props = defineProps({ userId: String, accountId: String })
const assets = ref([])
const filter = ref('')

const filteredAssets = computed(() =>
    assets.value.filter(a => a.name.toLowerCase().includes(filter.value.toLowerCase()))
)

const loadAssets = async () => {
  const res = await axios.get(`/user/${props.userId}/account/${props.accountId}/list/assets`)
  assets.value = res.data.assets ?? res.data
}

onMounted(loadAssets)
watch([() => props.accountId, () => props.userId], loadAssets)
</script>