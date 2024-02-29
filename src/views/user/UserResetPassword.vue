<script setup>
import {getCurrentInstance, reactive} from "vue";
import {ElMessage} from "element-plus";
import {useTokenStore} from "@/store/index.js";
const {proxy} = getCurrentInstance()
//获取pinia
const tokenStore = useTokenStore()
const updatePassword =reactive({
  olePwd:'',
  newPwd:'',
  rePwd:''
})
//rePassword验证
const checkRePassword = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请再次确认密码！"))
  } else if (value !== updatePassword.newPwd) {
    callback(new Error("两次密码不一致！"))
  } else {
    callback()
  }
}
//注册表单验证
const rules = reactive({
  olePwd: [
    {required: true, message: '旧密码不能为空！', trigger: 'blur'},
  ],
  newPwd: [
    {required: true, message: '密码不能为空！', trigger: 'blur'},
    {min: 5, max: 11, message: '长度为5-11非空字符串', trigger: 'blur'},
  ],
  rePwd: [
    {validator: checkRePassword, trigger: 'blur',required: true},
  ]
})
const update=async ()=>{
  const result = await proxy.request.patch('/user/updatePwd',updatePassword)
  if (result.code === '200'){
    for (let updatePasswordKey in updatePassword) {
      updatePassword[updatePasswordKey] = ""
    }
    tokenStore.setToken(result.data)
    ElMessage.success("修改成功！")
  }else {
    ElMessage.error(result.message)
  }
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>修改密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="updatePassword" :rules="rules" label-width="100px" size="large">
          <el-form-item label="旧密码" prop="olePwd">
            <el-input v-model="updatePassword.olePwd" show-password></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPwd">
            <el-input v-model="updatePassword.newPwd" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="rePwd">
            <el-input v-model="updatePassword.rePwd" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="update">提交修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>