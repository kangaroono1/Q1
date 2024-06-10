<template>
  <el-row @click="emit('send-nav', false)">
    <el-col><RouterLink to="/home" active-class="active">首页</RouterLink></el-col>
    <el-col><RouterLink to="/add-ledger" active-class="active">增添台账</RouterLink></el-col>
    <el-col><RouterLink to="/ledger-detail" active-class="active">台账管理</RouterLink></el-col>
  </el-row>
</template>

<script lang="ts" setup>
  import { ref, defineEmits } from 'vue'
  import { RouterLink } from 'vue-router'

  const showAdmin = ref(true)

  const emit = defineEmits(['send-nav']) // to close header-component's drawer
</script>

<style scoped>
.el-row a {
  display: block;
  text-align: center;
  width: 200px;
  height: 50px;
  line-height: 50px;
  border-radius: 5px;
  text-decoration: none;
  font-size: 18px;
  letter-spacing: 5px;
}
.el-row a.active {
  background-color: #74e6ee;
  color: #2c1ad2;
  font-weight: 900;
  text-shadow: 0 0 1px black;
  font-family: 微软雅黑;
}
</style>