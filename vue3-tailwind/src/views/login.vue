<template>
  <div
    data-theme="dark"
    class="w-full h-screen flex items-center justify-center bg-base-200 bg-gradient-to-br from-base-300 via-base-200 to-base-100 text-base-content"
  >
    <div class="w-80">
      <div class="font-title text-3xl font-bold text-center mb-6">
        <span class="lowercase text-primary">whgr</span>&nbsp;
        <span class="uppercase">admin</span>
      </div>

      <form class="space-y-3" @submit.prevent="submit">
        <input
          v-model="form.userName"
          class="input input-bordered w-full"
          placeholder="账号"
          autocomplete="username"
        />
        <input
          v-model="form.password"
          type="password"
          class="input input-bordered w-full"
          placeholder="密码"
          autocomplete="current-password"
        />
        <button class="btn btn-primary w-full mt-2" :disabled="loading">
          {{ loading ? '登录中…' : '立即登录' }}
        </button>
      </form>

      <p class="text-xs text-center text-base-content/50 mt-4">默认账号 admin / admin</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import md5 from 'js-md5'
import { login } from '/@/api'
import { setToken } from '/@/api/request'

const router = useRouter()
const loading = ref(false)
const form = reactive({ userName: 'admin', password: '' })

const submit = async () => {
  if (!form.userName || !form.password) return ElMessage.warning('请输入账号和密码')
  loading.value = true
  try {
    const data = await login({ userName: form.userName, passwordMd5: md5(form.password) })
    setToken(data.token)
    await router.push('/')
  } finally {
    loading.value = false
  }
}
</script>
