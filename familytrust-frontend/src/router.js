import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from './views/Dashboard.vue'
import CreateAccountForm from './components/CreateAccountForm.vue'

const routes = [
    { path: '/', name: 'Dashboard', component: Dashboard },
    { path: '/konto-anlegen', name: 'KontoAnlegen', component: CreateAccountForm }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router