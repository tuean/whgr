<template>
  <div class="h-full flex flex-col">
    <div class="flex items-center gap-3 px-4 h-11 border-b border-base-300 shrink-0">
      <span class="font-semibold">{{ menu.menuName || '外部页面' }}</span>
      <span class="text-xs text-base-content/40 font-mono truncate">{{ menu.menuUrl }}</span>
      <div class="flex-1"></div>
      <button class="btn btn-xs btn-ghost" @click="reload">刷新</button>
      <a
        v-if="menu.menuUrl"
        class="btn btn-xs btn-ghost"
        :href="menu.menuUrl"
        target="_blank"
        rel="noreferrer"
      >
        新窗口
      </a>
    </div>

    <div v-if="error" class="p-8 text-sm text-error">{{ error }}</div>
    <!-- 外部系统常要 cookie/弹窗/下载；跨域下 allow-same-origin 只作用于它自己的源 -->
    <iframe
      v-else-if="src"
      :src="src"
      class="flex-1 w-full border-0"
      sandbox="allow-scripts allow-same-origin allow-forms allow-popups allow-downloads allow-modals"
    ></iframe>
    <div v-else class="p-8 text-sm text-base-content/50">加载中…</div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { menuFrame } from '/@/api'

const route = useRoute()
const menu = reactive({})
const src = ref('')
const error = ref('')

const load = async () => {
  error.value = ''
  src.value = ''
  try {
    const data = await menuFrame(route.params.id)
    Object.assign(menu, data || {})
    src.value = menu.menuUrl
  } catch (e) {
    // 接口错误消息已由 request 拦截器弹过，这里只留一句页面内的说明
    error.value = '打开失败：菜单不存在，或当前账号无权访问'
  }
}

// 刷新走新 query 破缓存，而不是 iframe.reload()——后者对跨域内容可能仍是缓存页
const reload = () => {
  if (!menu.menuUrl) return
  src.value = `${menu.menuUrl}${menu.menuUrl.includes('?') ? '&' : '?'}_t=${Date.now()}`
}

onMounted(load)
watch(() => route.params.id, load)
</script>
