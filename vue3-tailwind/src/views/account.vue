<template>
  <div class="card bg-base-100 shadow-sm">
    <div class="card-body p-4">
      <div class="flex items-center gap-2 mb-3">
        <input
          v-model="query.searchKey"
          class="input input-bordered input-sm w-56"
          placeholder="账号 / 姓名"
          @keyup.enter="search"
        />
        <button class="btn btn-sm" @click="search">查询</button>
        <div class="flex-1"></div>
        <button class="btn btn-sm btn-primary" @click="openCreate">新增</button>
      </div>

      <table class="table table-zebra table-sm">
        <thead>
          <tr>
            <th>ID</th>
            <th>账号</th>
            <th>姓名</th>
            <th>手机</th>
            <th>角色</th>
            <th>状态</th>
            <th>创建时间</th>
            <th class="text-right">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in rows" :key="row.id">
            <td>{{ row.id }}</td>
            <td>{{ row.account }}</td>
            <td>{{ row.name }}</td>
            <td>{{ row.mobile }}</td>
            <td>{{ row.role === 1 ? '管理员' : '普通用户' }}</td>
            <td>
              <span class="badge badge-sm" :class="row.status === 1 ? 'badge-success' : 'badge-ghost'">
                {{ row.status === 1 ? '启用' : '停用' }}
              </span>
            </td>
            <td>{{ row.createTime }}</td>
            <td class="text-right whitespace-nowrap">
              <button class="btn btn-ghost btn-xs" @click="openEdit(row)">编辑</button>
              <button class="btn btn-ghost btn-xs text-error" @click="remove(row)">删除</button>
            </td>
          </tr>
          <tr v-if="!rows.length">
            <td colspan="8" class="text-center text-base-content/50 py-8">暂无数据</td>
          </tr>
        </tbody>
      </table>

      <div class="flex items-center justify-between mt-3 text-sm">
        <span class="text-base-content/60">共 {{ total }} 条</span>
        <div class="flex items-center gap-1">
          <button class="btn btn-sm" :disabled="query.pageNum <= 1" @click="turn(-1)">上一页</button>
          <span class="px-2 text-base-content/60">{{ query.pageNum }} / {{ maxPage }}</span>
          <button class="btn btn-sm" :disabled="query.pageNum >= maxPage" @click="turn(1)">下一页</button>
        </div>
      </div>
    </div>

    <div v-if="showModal" class="modal modal-open">
      <div class="modal-box">
        <h3 class="font-bold mb-4">{{ form.id ? '编辑账号' : '新增账号' }}</h3>
        <div class="space-y-3">
          <input
            v-model="form.account"
            class="input input-bordered input-sm w-full"
            placeholder="账号"
            :disabled="!!form.id"
          />
          <input v-model="form.name" class="input input-bordered input-sm w-full" placeholder="姓名" />
          <input v-model="form.mobile" class="input input-bordered input-sm w-full" placeholder="手机号" />
          <input
            v-model="form.pwd"
            type="password"
            class="input input-bordered input-sm w-full"
            :placeholder="form.id ? '密码（留空不修改）' : '密码'"
          />
          <select v-model.number="form.role" class="select select-bordered select-sm w-full">
            <option :value="1">管理员</option>
            <option :value="2">普通用户</option>
          </select>
          <select v-model.number="form.status" class="select select-bordered select-sm w-full">
            <option :value="1">启用</option>
            <option :value="0">停用</option>
          </select>
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
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { accountList, accountSave, accountDelete } from '/@/api'

const rows = ref([])
const total = ref(0)
const saving = ref(false)
const showModal = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, searchKey: '' })
const form = reactive({})

const maxPage = computed(() => Math.max(1, Math.ceil(total.value / query.pageSize)))

const load = async () => {
  const data = await accountList(query)
  rows.value = data.list || []
  total.value = data.total || 0
}

const search = () => {
  query.pageNum = 1
  load()
}

const turn = step => {
  query.pageNum += step
  load()
}

const openCreate = () => {
  Object.keys(form).forEach(k => delete form[k])
  Object.assign(form, { role: 1, status: 1 })
  showModal.value = true
}

const openEdit = row => {
  Object.keys(form).forEach(k => delete form[k])
  Object.assign(form, { ...row, pwd: '' })
  showModal.value = true
}

const submit = async () => {
  saving.value = true
  try {
    await accountSave(form)
    ElMessage.success('保存成功')
    showModal.value = false
    load()
  } finally {
    saving.value = false
  }
}

const remove = async row => {
  await ElMessageBox.confirm(`确定删除账号「${row.account}」？`, '提示', { type: 'warning' })
  await accountDelete(row.id)
  ElMessage.success('已删除')
  load()
}

onMounted(load)
</script>
