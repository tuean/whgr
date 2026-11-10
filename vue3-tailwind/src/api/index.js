import request, { getToken, clearToken } from './request'
import env from '@/config/index'

export const login = data => request.post('/adminUser/login', data)
export const logout = () => request.delete('/logout')
export const profile = () => request.get('/adminUser/profile')
export const menuList = () => request.get('/menu/list')

export const accountList = data => request.post('/admin/list', data)
export const accountSave = data => request.post('/admin/save', data)
export const accountDelete = id => request.post('/admin/delete', null, { params: { id } })

// AI 入口：SSE 流式返回事件 {type:'thinking'|'tool'|'action'|'answer'|'done'|'error'}
// 用 fetch 而不是 EventSource：要 POST + 自定义 token 头
export function aiChatStream(message, onEvent) {
  const controller = new AbortController()
  const promise = (async () => {
    const res = await fetch(`${env.baseUrl}/ai/chat`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', Accept: 'text/event-stream', token: getToken() },
      body: JSON.stringify({ message }),
      signal: controller.signal
    })
    const contentType = res.headers.get('content-type') || ''
    if (!contentType.includes('text/event-stream')) {
      // 鉴权失败/参数错时后端回的是普通 JSON
      const body = await res.json().catch(() => ({}))
      if (body.code === 419) {
        clearToken()
        window.location.hash = '#/login'
      }
      throw new Error(body.message || `AI 请求失败(${res.status})`)
    }

    const reader = res.body.getReader()
    const decoder = new TextDecoder()
    let buffer = ''
    for (;;) {
      const { value, done } = await reader.read()
      if (done) break
      buffer += decoder.decode(value, { stream: true })
      let index
      while ((index = buffer.indexOf('\n\n')) >= 0) {
        const chunk = buffer.slice(0, index)
        buffer = buffer.slice(index + 2)
        const data = chunk
          .split('\n')
          .filter(line => line.startsWith('data:'))
          .map(line => line.slice(5).trim())
          .join('')
        if (!data) continue
        try {
          onEvent(JSON.parse(data))
        } catch (e) {
          // 半包/心跳忽略
        }
      }
    }
  })()
  return { promise, abort: () => controller.abort() }
}

export const aiHistory = data => request.post('/ai/history', data)
export const aiHistoryDetail = id => request.get(`/ai/history/${id}`)
