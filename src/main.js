import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import request from "./utls/request";
import { createPinia } from 'pinia'
import locale from 'element-plus/dist/locale/zh-cn.js'


//导入持久化插件
import {createPersistedState} from'pinia-persistedstate-plugin'
const pinia = createPinia()
const persist = createPersistedState()
const app = createApp(App);
//pinia使用持久化插件
pinia.use(persist)
app.config.globalProperties.request = request
app.use(pinia)
// {locale}:使用中文语言包，解决分页条中文问题
app.use(ElementPlus,{locale});
app.use(router);
app.mount('#app');
// https://gitee.com/xiaodu_git/springboot_vue3_big_event.git