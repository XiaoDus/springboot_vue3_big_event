<template>
  <router-view v-if="isRouterActive"/>
</template>
<script setup>
import { provide,nextTick,ref } from 'vue'
//用于页面刷新
const isRouterActive = ref(true)
provide('reload', () => {
  isRouterActive.value = false
  nextTick(() => {
    isRouterActive.value = true
  })
})

</script>
<style>
*{
  margin: 0;
  padding: 0;
}
</style>
