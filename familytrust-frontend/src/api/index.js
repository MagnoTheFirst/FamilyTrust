import axios from 'axios';

// Request-Caching
const requestCache = new Map();
const CACHE_DURATION = 5 * 60 * 1000; // 5 Minuten

// Debounce-Funktion für API-Calls
const debounce = (func, wait) => {
  let timeout;
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
};

// Try proxy first, fallback to direct connection
const getBaseURL = () => {
  if (import.meta.env.DEV) {
    // In development, try proxy first
    return '/api';
  }
  return 'http://localhost:8084';
};

const API = axios.create({ 
  baseURL: getBaseURL(),
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  }
});

// Response interceptor for error handling
API.interceptors.response.use(
  response => {
    console.log('API Success:', response.config.method, response.config.url, response.status);
    return response;
  },
  async error => {
    console.error('API Error:', error.config?.method, error.config?.url, error.response?.status, error.message);
    
    // If proxy fails and we're in development, try direct connection
    if (import.meta.env.DEV && error.code === 'ERR_NETWORK' && API.defaults.baseURL === '/api') {
      console.log('Proxy failed, trying direct connection...');
      API.defaults.baseURL = 'http://localhost:8084';
      return API.request(error.config);
    }
    
    return Promise.reject(error);
  }
);

// Add request interceptor for debugging
API.interceptors.request.use(
  config => {
    console.log('API Request:', config.method?.toUpperCase(), config.url);
    return config;
  },
  error => {
    console.error('Request Error:', error);
    return Promise.reject(error);
  }
);

export const api = {
  // Test connection
  testConnection: () => API.get('/users').catch(() => API.get('/actuator/health')),
  
  // Account operations
  getAccounts: (userId) => API.get(`/user/${userId}/get/accounts`),
  getAccount: (userId, accountId) => API.get(`/user/${userId}/get/account/${accountId}`),
  createAccount: (userId, data) => API.post(`/user/create/account/${userId}`, data),
  updateAccount: (userId, data) => API.patch(`/user/${userId}/change/account`, data),
  deleteAccount: (userId, data) => API.delete(`/user/${userId}/delete/account/${data.accountId}`, { data }),
  
  // Cash flow operations
  postCashflow: (data) => API.post('/user/account/cashFlowTransaction', data),
  
  // Asset operations
  getAssets: (userId, accountId) => API.get(`/user/${userId}/account/${accountId}/list/assets`),
  getAsset: (userId, accountId, assetId) => API.get(`/user/${userId}/account/${accountId}/asset/${assetId}`),
  getAssetsByType: (userId, accountId, assetType) => API.get(`/user/${userId}/account/${accountId}/list/asset-types/${assetType}`),
  buyOrSellAsset: (assetDto) => API.post('/asset-transaction', null, { params: assetDto }),
  getAssetTransactions: (accountId, assetName) => API.get(`/account/${accountId}/list/assetTransactions/${assetName}`),
  
  // Admin operations
  createUser: () => API.post('/create/user'),
  getUsers: () => API.get('/users'),
};