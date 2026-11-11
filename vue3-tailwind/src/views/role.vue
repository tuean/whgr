<template>
  <div class="card bg-base-100 shadow-sm">
    <div class="card-body p-4">
      <div class="flex items-center gap-2 mb-3">
        <span class="text-sm text-base-content/60">等级越小权限越大：等级 1 可见所有菜单，菜单/接口的可见性都按等级判断</span>
        <div class="flex-1"></div>
        <button class="btn btn-sm btn-primary" @click="openCreate">新增</button>
      </div>

      <table class="table table-zebra table-sm">
        <thead>
          <tr>
            <th>ID</th>
            <th>角色名称</th>
            <th>权限等级</th>
            <th>数据范围</th>
            <th>状态</th>
            <th>备注</th>
            <th class="text-right">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in rows" :key="row.id">
            <td>{{ row.id }}</td>
            <td>
              {{ row.roleName }}
              <span v-if="row.level === 1" class="badge badge-sm badge-primary ml-1">内置</span>
            </td>
            <td>{{ row.level }}</td>
            <td>
              <span class="badge badge-sm" :class="row.dataScope === 1 ? 'badge-success' : 'badge-warning'">
                {{ scopeText(row.dataScope) }}
              </span>
            </td>
            <td>
              <span class="badge badge-sm" :class="row.status === 1 ? 'badge-success' : 'badge-ghost'">
                {{ row.status === 1 ? '启用' : '停用' }}
              </span>
            </td>
            <td class="text-base-content/60">{{ row.remark }}</td>
            <td class="text-right whitespace-nowrap">
              <button class="btn btn-ghost btn-xs" @click="openEdit(row)">编辑</button>
              <button class="btn btn-ghost btn-xs text-error" :disabled="row.level === 1" @click="remove(row)">
                删除
              </button>
            </td>
          </tr>
          <tr v-if="!rows.length">
            <td colspan="7" class="text-center text-base-content/50 py-8">暂无数据</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showModal" class="modal modal-open">
      <div class="modal-box">
        <h3 class="font-bold mb-4">{{ form.id ? '编辑角色' : '新增角色' }}</h3>
        <div class="space-y-3">
          <input v-model="form.roleName" class="input input-bordered input-sm w-full" placeholder="角色名称" />
          <input
            v-model.number="form.level"
            type="number"
            min="1"
            class="input input-bordered input-sm w-full"
            placeholder="权限等级（1 是内置管理员）"
            :disabled="form.level === 1 && !!form.id"
          />
          <select v-model.number="form.dataScope" class="select select-bordered select-sm w-full">
            <option :value="1">数据范围：全部数据</option>
            <option :value="2">数据范围：仅本人数据</option>
          </select>
          <select v-model.number="form.status" class="select select-bordered select-sm w-full">
            <option :value="1">启用</option>
            <option :value="0">停用</option>
          </select>
          <input v-model="form.remark" class="input input-bordered input-sm w-full" placeholder="备注" />
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
import { roleList, roleSave, roleDelete } from '/@/api'

const rows = ref([])
const saving = ref(false)
const showModal = ref(false)
const form = reactive({})

const scopeText = scope => (scope === 2 ? '仅本人数据' : '全部数据')

const load = async () => {
  rows.value = (await roleList()) || []
}

const openCreate = () => {
  Object.keys(form).forEach(k => delete form[k])
  Object.assign(form, { level: rows.value.length + 1, dataScope: 1, status: 1 })
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
    await roleSave(form)
    ElMessage.success('保存成功')
    showModal.value = false
    load()
  } finally {
    saving.value = false
  }
}

const remove = async row => {
  await ElMessageBox.confirm(`确定删除角色「${row.roleName}」？`, '提示', { type: 'warning' })
  await roleDelete(row.id)
  ElMessage.success('已删除')
  load()
}

onMounted(load)
</script>
