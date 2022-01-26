import { ElMessage } from "element-plus";


export function localGet(key) {
  const value = window.localStorage.getItem(key)
  try {
    return JSON.parse(window.localStorage.getItem(key))
  } catch (error) {
    return value
  }
}

export function localSet(key, value) {
  window.localStorage.setItem(key, JSON.stringify(value))
}

export function localRemove(key) {
  window.localStorage.removeItem(key)
}

// 判断内容是否含有表情字符，现有数据库不支持。
export function hasEmoji(str = '') {
  const reg = /[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f\u2026\u2022\u20ac\r\n]/g;
  return str.match(reg) && str.match(reg).length
}

export const pathMap = {
  login: '登录',
  introduce: '系统介绍',
  dashboard: '大盘数据',
  add: '添加商品',
  swiper: '轮播图配置',
  hot: '热销商品配置',
  new: '新品上线配置',
  recommend: '为你推荐配置',
  category: '分类管理',
  level2: '分类二级管理',
  level3: '分类三级管理',
  good: '商品管理',
  guest: '会员管理',
  order: '订单管理',
  order_detail: '订单详情',
  account: '修改账户',
  menu: '菜单',
}

export function firstLetter(text) {
  if (text === null) text = "默"
  return text.substring(0, 1)
}

export function tabIdEqual(tab, id) {
  return tab.id === id
}

export function tabEqual(tabs, id) {
  if (tabs == null || tabs.length < 1) return -1
  for (let x = 0; tabs.length > x; x++) {
    if (tabIdEqual(tabs[x], id)) return x
  }
  return -1
}

export function getNextShow(tabs, toRemoveId) {
  if (tabs == null || tabs.length < 1) return
  for (let x = 0; tabs.length > x; x++) {
    if (tabIdEqual(tabs[x], toRemoveId)) {
      return tabs[x - 1]
    }
  }
  return null
}


export const getLastNum = s => {
  if (s == null || s.length < 1) return 1
  let last = s.chatAt(s.length - 1)
  try {
    return parseInt(last)
  } catch {
    return 1
  }
}

