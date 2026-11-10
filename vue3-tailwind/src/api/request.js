import axios from 'axios'
import { ElMessage } from 'element-plus'
import env from '@/config/index'

const TOKEN_KEY = 'token'

export function getToken() {
  return localStorage.getItem(TOKEN_KEY) || ''
}

export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token)
}

export function clearToken() {
  localStorage.removeItem(TOKEN_KEY)
}

const request = axios.create({
  baseURL: env.baseUrl,
  timeout: 15000
})

request.interceptors.request.use(config => {
  const token = getToken()
  if (token) config.headers.token = token
  return config
})

// 后端统一返回 {code, message, data}，这里直接解包成 data，业务代码不用再判 code
request.interceptors.response.use(res => {
  const body = res.data
  if (!body || typeof body !== 'object') {
    ElMessage.error('服务端异常')
    return Promise.reject(res)
  }
  if (body.code !== 0) {
    ElMessage.error(body.message || '请求失败')
    if (body.code === 419) {
      clearToken()
      window.location.hash = '#/login'
    }
    return Promise.reject(body)
  }
  return body.data
}, err => {
  ElMessage.error(err.response ? `请求失败(${err.response.status})` : '网络异常')
  return Promise.reject(err)
})

export default request
