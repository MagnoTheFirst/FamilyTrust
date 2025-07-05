import { createRouter, createWebHistory } from 'vue-router';

// Lazy loading für bessere Performance
const AccountsView = () => import('../views/AccountsView.vue');
const AccountDetailsView = () => import('../views/AccountDetailsView.vue');
const PortfolioDashboard = () => import('../views/PortfolioDashboard.vue');

const routes = [
    { 
        path: '/', 
        component: AccountsView,
        meta: { title: 'Accounts' }
    },
    { 
        path: '/account/:id', 
        component: AccountDetailsView,
        meta: { title: 'Account Details' }
    },
    { 
        path: '/dashboard', 
        component: PortfolioDashboard,
        meta: { title: 'Portfolio Dashboard' }
    }
];

export default createRouter({
    history: createWebHistory(),
    routes,
});