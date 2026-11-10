<template>
  <div class="flex gap-3 h-[calc(100vh-7rem)]">
    <!-- 主对话区 -->
    <section class="flex-1 flex flex-col rounded-box border border-base-300 bg-base-100 overflow-hidden">
      <header class="px-4 py-3 border-b border-base-300 flex items-start justify-between">
        <div>
          <h1 class="font-semibold">AI 入口</h1>
          <p class="text-xs text-base-content/50">说需求 → 看它怎么想 → 直接跳到对应页面</p>
        </div>
        <button class="btn btn-ghost btn-xs" :disabled="loading" @click="reset">新对话</button>
      </header>

      <div ref="scrollEl" class="flex-1 overflow-y-auto p-4 space-y-4">
        <div v-if="!turns.length" class="max-w-xl mx-auto text-center mt-16 space-y-4">
          <div class="text-4xl">🤖</div>
          <p class="text-sm text-base-content/60">整个后台都可以从这里进，随便说一句</p>
          <div class="flex flex-wrap gap-2 justify-center">
            <button v-for="s in suggestions" :key="s" class="btn btn-sm btn-outline" @click="ask(s)">{{ s }}</button>
          </div>
        </div>

        <div v-for="(turn, ti) in turns" :key="ti" class="space-y-2">
          <div class="chat chat-end">
            <div class="chat-bubble chat-bubble-primary text-sm">{{ turn.question }}</div>
          </div>

          <div class="pl-1 space-y-1">
            <div class="flex items-center gap-2" v-if="turn.history">
              <span class="badge badge-xs badge-ghost">历史记录回放</span>
              <span class="text-xs text-base-content/40">{{ turn.time }}</span>
            </div>

            <div v-for="(s, si) in turn.steps" :key="si" class="border-l-2 border-base-300 pl-3 py-1">
              <span class="badge badge-xs" :class="badgeClass(s)">{{ stepLabel(s) }}</span>
              <div
                v-if="s.kind !== 'tool'"
                class="text-sm whitespace-pre-wrap mt-1"
                :class="s.kind === 'thinking' ? 'text-base-content/60 italic' : 'text-base-content/80'"
              >
                {{ s.text }}
              </div>
              <details v-else class="mt-1">
                <summary class="text-xs cursor-pointer text-base-content/50">参数 / 结果</summary>
                <div class="text-xs font-mono bg-base-200 rounded p-2 mt-1 whitespace-pre-wrap break-all">
                  <div>参数：{{ s.args || '{}' }}</div>
                  <div class="mt-1">结果：{{ s.result }}</div>
                </div>
              </details>
            </div>

            <div
              v-for="a in turn.actions"
              :key="a.path"
              class="flex items-center gap-3 rounded-box border border-success/40 bg-success/10 px-3 py-2"
            >
              <span class="text-sm">
                已定位到 <b>{{ a.label }}</b>
                <span class="text-base-content/50 text-xs ml-1">{{ a.path }}</span>
              </span>
              <button class="btn btn-xs btn-primary ml-auto" @click="go(a)">打开</button>
            </div>

            <div v-if="turn.reply" class="chat chat-start">
              <div class="chat-bubble text-sm whitespace-pre-wrap">{{ turn.reply }}</div>
            </div>

            <div v-if="turn.error" class="text-sm text-error">{{ turn.error }}</div>
          </div>
        </div>

        <div v-if="loading" class="text-xs text-base-content/50 pl-1">思考中…</div>
      </div>

      <div class="p-3 border-t border-base-300 flex gap-2">
        <input
          v-model="input"
          class="input input-bordered flex-1"
          placeholder="比如：我要管理账号 / 你都能干什么"
          :disabled="loading"
          @keyup.enter="ask()"
        />
        <button class="btn btn-primary" :disabled="loading || !input.trim()" @click="ask()">发送</button>
      </div>
    </section>

    <!-- 历史回溯 -->
    <aside class="w-72 shrink-0 flex flex-col rounded-box border border-base-300 bg-base-100 overflow-hidden">
      <div class="px-3 py-2 border-b border-base-300 flex items-center justify-between">
        <span class="text-sm font-semibold">历史回溯</span>
        <button class="btn btn-ghost btn-xs" @click="loadHistory">刷新</button>
      </div>
      <div class="flex-1 overflow-y-auto">
        <button
          v-for="h in history"
          :key="h.id"
          class="w-full text-left px-3 py-2 border-b border-base-300/60 hover:bg-base-200"
          :class="activeLogId === h.id ? 'bg-base-200' : ''"
          @click="replay(h.id)"
        >
          <div class="text-sm truncate">{{ h.message }}</div>
          <div class="text-xs text-base-content/40">{{ h.createTime }}</div>
        </button>
        <p v-if="!history.length" class="p-3 text-xs text-base-content/40">还没有记录</p>
      </div>
    </aside>
  </div>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { aiChatStream, aiHistory, aiHistoryDetail } from '/@/api'

const router = useRouter()
const suggestions = ['我要管理账号', '你都能干什么', '打开工作台']
const CACHE_KEY = 'aiTurns'

const turns = ref(JSON.parse(sessionStorage.getItem(CACHE_KEY) || '[]'))
const history = ref([])
const input = ref('')
const loading = ref(false)
const activeLogId = ref(null)
const scrollEl = ref(null)

// 跳走再回来（AI 会把人送到别的页面）对话还在
watch(
  turns,
  value => sessionStorage.setItem(CACHE_KEY, JSON.stringify(value.slice(-10))),
  { deep: true }
)

const stepLabel = s => (s.kind === 'tool' ? '调用 ' + s.tool : s.kind === 'thinking' ? '思考' : '决策')
const badgeClass = s => (s.kind === 'tool' ? 'badge-info' : s.kind === 'thinking' ? 'badge-ghost' : 'badge-warning')

const scrollBottom = () =>
  nextTick(() => {
    const el = scrollEl.value
    if (el) el.scrollTop = el.scrollHeight
  })

const ask = async preset => {
  const question = (preset || input.value).trim()
  if (!question || loading.value) return
  input.value = ''
  loading.value = true

  // reactive：直接改普通对象不会触发 watch/template 更新（跳走前存缓存就存成空的了）
  const turn = reactive({ question, steps: [], actions: [], reply: '', error: '', history: false })
  turns.value.push(turn)
  scrollBottom()

  const { promise } = aiChatStream(question, ev => {
    if (ev.type === 'thinking') turn.steps.push({ kind: 'thinking', text: ev.text })
    else if (ev.type === 'decision') turn.steps.push({ kind: 'decision', text: ev.text })
    else if (ev.type === 'tool') turn.steps.push({ kind: 'tool', tool: ev.tool, args: ev.args, result: ev.result })
    else if (ev.type === 'action') turn.actions.push({ path: ev.path, label: ev.label })
    else if (ev.type === 'answer') turn.reply = ev.reply
    else if (ev.type === 'error') turn.error = ev.message
    else if (ev.type === 'done') {
      activeLogId.value = ev.logId
      loadHistory()
    }
    scrollBottom()
  })

  try {
    await promise
  } catch (e) {
    turn.error = e.message
    ElMessage.error(e.message)
  } finally {
    loading.value = false
    scrollBottom()
  }
}

const go = action => {
  // 后端已按角色过滤；这里用同一份菜单再兜一层，防止 URL 里手填越权页面
  const allowed = JSON.parse(sessionStorage.getItem('menuPaths') || '[]')
  if (allowed.length && !allowed.includes(action.path)) {
    return ElMessage.error(`没有权限访问 ${action.label || action.path}`)
  }
  router.push(action.path)
}

const loadHistory = async () => {
  try {
    const data = await aiHistory({ pageNum: 1, pageSize: 20 })
    history.value = data.list || []
  } catch (e) {
    // 拦截器已经提示过了
  }
}

// 回溯：把一次历史问答的完整轨迹铺回对话区
const parse = (text, fallback) => {
  try {
    return JSON.parse(text || '') || fallback
  } catch (e) {
    return fallback
  }
}

const replay = async id => {
  const data = await aiHistoryDetail(id)
  activeLogId.value = id
  turns.value = [
    {
      question: data.message,
      steps: parse(data.trace, [])
        .filter(e => e.type === 'thinking' || e.type === 'decision' || e.type === 'tool')
        .map(e =>
          e.type === 'tool'
            ? { kind: 'tool', tool: e.tool, args: e.args, result: e.result }
            : { kind: e.type, text: e.text }
        ),
      actions: parse(data.actions, []),
      reply: data.reply,
      error: '',
      history: true,
      time: data.createTime
    }
  ]
  scrollBottom()
}

const reset = () => {
  turns.value = []
  activeLogId.value = null
  input.value = ''
}

onMounted(() => {
  loadHistory()
  scrollBottom()
})
</script>
