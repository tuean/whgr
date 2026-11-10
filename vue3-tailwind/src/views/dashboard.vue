<template>
  <div class="space-y-4">
    <div class="card bg-base-100 shadow-sm">
      <div class="card-body">
        <h2 class="card-title text-base">开箱即用</h2>
        <p class="text-sm text-base-content/70">
          登录 → 左侧菜单（数据库 menus 表驱动）→ 列表页增删改查，这条链路已经打通。
        </p>
        <ul class="text-sm text-base-content/70 list-disc pl-5 space-y-1">
          <li>后端统一响应 <code>MineResp{code,message,data}</code>，异常由 GlobalExceptionHandler 兜底</li>
          <li>鉴权由 AuthAspect 统一拦截，未登录返回 419，前端自动跳登录页</li>
          <li>新增接口照 <code>func/admin</code> 抄：Controller + Service + Mapper 三件套</li>
          <li>新增页面照 <code>views/account.vue</code> 抄：查询 / 分页 / 弹窗表单 / 删除</li>
        </ul>
        <div class="card-actions mt-2">
          <router-link to="/account" class="btn btn-sm btn-primary">去看账号管理</router-link>
        </div>
      </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <div class="card bg-base-100 shadow-sm">
        <div class="card-body py-4">
          <span class="text-xs text-base-content/50">后端</span>
          <span class="text-sm">Spring Boot 2.7 · MyBatis · JDK 21</span>
        </div>
      </div>
      <div class="card bg-base-100 shadow-sm">
        <div class="card-body py-4">
          <span class="text-xs text-base-content/50">前端</span>
          <span class="text-sm">Vue 3 · Vite · Tailwind + daisyUI</span>
        </div>
      </div>
      <div class="card bg-base-100 shadow-sm">
        <div class="card-body py-4">
          <span class="text-xs text-base-content/50">登录账号</span>
          <span class="text-sm">admin / admin（见 sql/init-table.sql）</span>
        </div>
      </div>
    </div>
  </div>
</template>
