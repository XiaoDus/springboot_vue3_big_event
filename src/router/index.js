import { createRouter, createWebHistory } from 'vue-router'


const routes = [
  {
    path: '/',
    redirect:"/Login"
  },
   {
    path: '/Login',
    name: 'Login',
    component: ()=>import('../views/Login.vue')
  },
{
    path: '/Layout',
    name: 'Layout',
    component: ()=>import('../views/Layout.vue'),
    redirect:'/article/category',
    children:[
    { path: '/article/category', component: ()=>import('../views/article/ArticleCategory.vue') },
    { path: '/article/manage', component:()=>import('../views/article/ArticleManage.vue')  },
    { path: '/user/info', component: ()=>import('../views/user/UserInfo.vue') },
    { path: '/user/avatar', component: ()=>import('../views/user/UserAvatar.vue') },
    { path: '/user/password', component: ()=>import('../views/user/UserResetPassword.vue') },
]

  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
