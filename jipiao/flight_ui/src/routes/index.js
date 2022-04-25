import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // 公共路由
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/auth/Home.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/Login.vue'),
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/auth/Register.vue'),
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/auth/Profile.vue'),
  },
  // 管理员路由
  {
    path: '/flight-manage',
    name: 'FlightManage',
    component: () => import('../views/manage/FlightManage.vue'),
  },
  {
    path: '/user-manage',
    name: 'UserManage',
    component: () => import('../views/manage/UserManage.vue'),
  },
  {
    path: '/message-manage',
    name: 'MessageManage',
    component: () => import('../views/manage/MessageManage.vue'),
  },
  // 普通用户路由
  {
    path: '/flight-list',
    name: 'FlightList',
    component: () => import('../views/flight/FlightList.vue'),
    meta: {
      keepAlive: true // 需要缓存
    }
  },
  {
    path: '/flight-detail/:id',
    name: 'FlightDetail',
    component: () => import('../views/flight/FlightDetail.vue'),
    // meta: {
      // keepAlive: true // 需要缓存
    // }
  },
  {
    path: '/wait-pay/:id',
    name: 'WaitPay',
    component: () => import('../views/flight/WaitPay.vue'),
    // meta: {
      // keepAlive: true // 需要缓存
    // }
  },
  {
    path: '/order/',
    name: 'order',
    component: () => import('../views/flight/FlightOrder.vue'),
    // meta: {
      // keepAlive: true // 需要缓存
    // }
  },
  {
    path: '/message/',
    name: 'message',
    component: () => import('../views/auth/Message.vue')
  },
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

// 全局导航守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/register') {
    return next()
  }
  const tokenStr = window.localStorage.getItem('token')
  if (!tokenStr) {
    Vue.prototype.$message.error("请先登录！！！");
    return next('/login')
  }
  next()
})

export default router
