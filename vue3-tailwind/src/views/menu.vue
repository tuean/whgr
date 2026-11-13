<template>
  <div class="card bg-base-100 shadow-sm">
    <div class="card-body p-4">
      <div class="flex items-center gap-2 mb-3">
        <span class="text-sm text-base-content/60">
          菜单即前端路由白名单：可见范围 = 0 所有人，否则等级 ≤ N 的账号可见（AI 入口按同一份数据做跳转校验）
        </span>
        <div class="flex-1"></div>
        <button class="btn btn-sm btn-primary" @click="openCreate">新增</button>
      </div>

      <table class="table table-zebra table-sm">
        <thead>
          <tr>
            <th>ID</th>
            <th>菜单名称</th>
            <th>类型</th>
            <th>地址</th>
            <th>可见范围</th>
            <th class="text-right">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in rows" :key="row.id">
            <td>{{ row.id }}</td>
            <td>
              {{ row.menuName }}
              <span v-if="row.menuUrl === '/ai'" class="badge badge-sm badge-primary ml-1">主入口</span>
            </td>
            <td>
              <span :class="row.menuType === 2 ? 'badge badge-sm badge-info' : 'badge badge-sm badge-ghost'">
                {{ row.menuType === 2 ? '外部页面' : '内部路由' }}
              </span>
            </td>
            <td class="font-mono text-xs">{{ row.menuUrl }}</td>
            <td>{{ roleText(row.role) }}</td>
            <td class="text-right whitespace-nowrap">
              <button class="btn btn-ghost btn-xs" @click="openEdit(row)">编辑</button>
              <button
                class="btn btn-ghost btn-xs text-error"
                :disabled="row.menuUrl === '/ai'"
                @click="remove(row)"
              >
                删除
              </button>
            </td>
          </tr>
          <tr v-if="!rows.length">
            <td colspan="6" class="text-center text-base-content/50 py-8">暂无数据</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showModal" class="modal modal-open">
      <div class="modal-box">
        <h3 class="font-bold mb-4">{{ form.id ? '编辑菜单' : '新增菜单' }}</h3>
        <div class="space-y-3">
          <input v-model="form.menuName" class="input input-bordered input-sm w-full" placeholder="菜单名称" />
          <select v-model.number="form.menuType" class="select select-bordered select-sm w-full">
            <option :value="1">内部路由（前端已有页面）</option>
            <option :value="2">外部页面（iframe 加载，如 TBS 查询）</option>
          </select>
          <input
            v-model="form.menuUrl"
            class="input input-bordered input-sm w-full font-mono"
            :placeholder="
              form.menuType === 2
                ? '完整地址，例如 http://tbs.internal/tbs/query'
                : '路由，必须以 / 开头，例如 /report'
            "
          />
          <select v-model.number="form.role" class="select select-bordered select-sm w-full">
            <option :value="0">所有人可见</option>
            <option v-for="r in roles" :key="r.id" :value="r.level">
              {{ r.roleName }}及以上（等级 ≤ {{ r.level }}）
            </option>
          </select>
          <p class="text-xs text-base-content/50">
            <template v-if="form.menuType === 2">
              外部页面用 iframe 加载，AI 跳转和菜单高亮都按 /frame/{id} 走，不用改前端代码；
              目标站点若设了 X-Frame-Options: DENY 会显示空白，需要对方放行。
            </template>
            <template v-else>
              路由要对应前端已有页面（views/&lt;xxx&gt;.vue + router 注册），新建菜单不会自动生成页面。
            </template>
          </p>
        </div>
        <div class="modal-action">
          <button class="btn btn-sm btn-ghost" @click="showModal = false">取消</button>
          <button class="btn btn-sm btn-primary" :disabled="saving" @click="submit">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { menuAll, menuSave, menuDelete, roleList } from '/@/api'

const rows = ref([])
const roles = ref([])
const saving = ref(false)
const showModal = ref(false)
const form = reactive({})

const roleText = role => {
  if (!role) return '所有人'
  const hit = roles.value.find(r => r.level === role)
  return hit ? `${hit.roleName}及以上（等级 ≤ ${role}）` : `等级 ≤ ${role}`
}

const load = async () => {
  const [list, roleRows] = await Promise.all([menuAll(), roleList()])
  rows.value = list || []
  roles.value = roleRows || []
}

const openCreate = () => {
  Object.keys(form).forEach(k => delete form[k])
  Object.assign(form, { role: 0, menuType: 1 })
  showModal.value = true
}

const openEdit = row => {
  Object.keys(form).forEach(k => delete form[k])
  Object.assign(form, { ...row })
  showModal.value = true
}

const submit = async () => {
  saving.value = true
  try {
    await menuSave(form)
    ElMessage.success('保存成功，重新登录或刷新后菜单生效')
    showModal.value = false
    load()
  } finally {
    saving.value = false
  }
}

const remove = async row => {
  await ElMessageBox.confirm(`确定删除菜单「${row.menuName}」？`, '提示', { type: 'warning' })
  await menuDelete(row.id)
  ElMessage.success('已删除')
  load()
}

onMounted(load)
</script>
