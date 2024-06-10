import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    { path: '/', redirect: '/home'},
    { path: '/home', name: 'home', component: () => import('@/views/Home.vue') },
    { path: '/add-ledger', name: 'add-ledger', component: () => import('@/views/AddLedger.vue') },
    { path: '/ledger-detail', name: 'ledger-detail', component: () => import('@/views/LedgerDetail.vue') },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router