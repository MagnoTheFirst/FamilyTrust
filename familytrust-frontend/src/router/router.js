import { createRouter, createWebHistory } from 'vue-router';

// Lazy loading für bessere Performance
const AccountsView = () => import('../views/AccountsView.vue');
const AccountDetailsView = () => import('../views/AccountDetailsView.vue');
const PortfolioDashboard = () => import('../views/PortfolioDashboard.vue');
const PortfolioAnalyticsView = () => import('../views/PortfolioAnalyticsView.vue');
const AccountTransferView = () => import('../views/AccountTransferView.vue');
const LoggedOutView = () => import('../views/LoggedOutView.vue');

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
    },
    { 
        path: '/analytics', 
        component: PortfolioAnalyticsView,
        meta: { title: 'Portfolio Analytics' }
    },
    { 
        path: '/transfers', 
        component: AccountTransferView,
        meta: { title: 'Account Transfers' }
    },
    { 
        path: '/logged-out', 
        component: LoggedOutView,
        meta: { title: 'Abgemeldet', requiresAuth: false }
    }
];

export default createRouter({
    history: createWebHistory(),
    routes,
});