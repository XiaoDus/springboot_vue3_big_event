<script setup>
import {User, Lock} from '@element-plus/icons-vue'
import {reactive, ref, getCurrentInstance} from 'vue'
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {useTokenStore} from "@/store/index.js";
//获取全局变量
const {proxy} = getCurrentInstance()
//获取路由
const router=useRouter()
//获取pinia
const tokenStore = useTokenStore()

//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
const changeRegister = () => {
  loginForm.username = ""
  loginForm.password = ""
  loginForm.rePassword = ""
  isRegister.value = !isRegister.value
}
const loginForm = reactive({
  username: "",
  password: "",
  rePassword: ""
})
//rePassword验证
const checkRePassword = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请再次确认密码！"))
  } else if (value !== loginForm.password) {
    callback(new Error("两次密码不一致！"))
  } else {
    callback()
  }
}
//注册表单验证
const rules = reactive({
  username: [
    {required: true, message: '用户名不能为空！', trigger: 'blur'},
    {min: 5, max: 11, message: '长度为5-11非空字符串', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '密码不能为空！', trigger: 'blur'},
    {min: 5, max: 11, message: '长度为5-11非空字符串', trigger: 'blur'},
  ],
  rePassword: [
    {validator: checkRePassword, trigger: 'blur'}
  ]
})
//请求登录/注册接口
const sendForm = (e) => {
  if (e === "login") { //登录
    if (loginForm.password.length > 0 && loginForm.username.length > 0){
      proxy.request.post("/user/login", loginForm).then(res => {
        if (res.code === '200') {
          tokenStore.setToken(res.data.authorization)
          res.data.authorization = ""
          localStorage.setItem("user",JSON.stringify(res.data))
          ElMessage.success('登录成功！')
          router.push("/Layout")
        } else {
          ElMessage.error(res.message)
        }
      })
    } else {
      ElMessage.error("登录信息不能为空！")
    }

  } else { //注册
    if (loginForm.password.length > 0 && loginForm.rePassword.length > 0 && loginForm.username.length > 0) {
      proxy.request.post("/user/register", loginForm).then(res => {
        if (res.code === '200') {
          ElMessage.success('注册成功！')
          isRegister.value = false
        } else {
          ElMessage.error(res.message)
        }
      })
    } else {
      ElMessage.error("注册信息不能为空！")
    }

  }
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 注册表单 -->
      <el-form :rules="rules" :model="loginForm" size="large" autocomplete="off" v-if="isRegister">
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" v-model="loginForm.username" type="password"
                    placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" v-model="loginForm.password" show-password placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" v-model="loginForm.rePassword" type="password" show-password
                    placeholder="请输入再次密码"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" @click="sendForm('register')" auto-insert-space>
            注册
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="changeRegister">
            ← 返回
          </el-link>
        </el-form-item>
      </el-form>
      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else>
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item>
          <el-input :prefix-icon="User" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input name="password" :prefix-icon="Lock" v-model="loginForm.password" type="password" show-password
                    placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" @click="sendForm('login')" auto-insert-space>登录</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="changeRegister">
            注册 →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="less" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
    url('@/assets/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>