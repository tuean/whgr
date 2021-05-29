import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '/@/router/index'
import { localGet } from './index'
import env from '/config/index'

const instant = axios.create()
// 这边由于后端没有区分测试和正式，姑且都写成一个接口。
instant.defaults.baseURL = env.baseUrl
console.log('baseUrl:' + env.baseUrl)
// 携带 cookie，对目前的项目没有什么作用，因为我们是 token 鉴权
instant.defaults.withCredentials = false
// 请求头，headers 信息
instant.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
instant.defaults.headers['token'] = localGet('token') || ''
// 默认 post 请求，使用 application/json 形式
instant.defaults.headers.post['Content-Type'] = 'application/json'

// 请求拦截器，内部根据返回值，重新组装，统一管理。
instant.interceptors.response.use(res => {
	debugger
  if (typeof res.data !== 'object') {
    ElMessage.error('服务端异常！')
    return Promise.reject(res)
  }
  if (res.data.code != 0) {
    if (res.data.message) ElMessage.error(res.data.message)
    if (res.data.code == 419) {
      router.push({ path: '/login' })
    }
    return Promise.reject(res.data)
  }

  return res.data.data
}, err => {
	console.log(err)
	ElMessage.error('服务器发生小问题(～￣(OO)￣)ブ')
})

export function get(url){
	let config = {
		headers: {
			'Content-Type' : 'application/json',
			'token-pc': sessionStorage.getItem('token')
		}
	}
	return new Promise((resolve, reject) => {
		instant.get(url, config)
			.then(res => {
				resolve(res)
			})
			.catch(err => {
				reject(err)
			})
	})
}


export function getWithParam(url, data) {
	let whole = url
	let dataStr = ''
	Object.keys(data).forEach(key => {
		dataStr += key + '=' + data[key] + '&';
	})

	if (dataStr !== '') {
		dataStr = dataStr.substr(0, dataStr.lastIndexOf('&'));
		whole = url + '?' + dataStr;
	}

	let config = {
		headers: {
			'Content-Type' : 'application/json',
			'token-pc': sessionStorage.getItem('token')
		}
	}
	return new Promise((resolve, reject) => {
		instant.get(whole, config)
			.then(res => {
				resolve(res)
			})
			.catch(err => {
				reject(err)
			})
		}) 
}


export function post(url, params) {
	let config = {
		headers: {
			"Content-Type" : "application/json",
			'token-pc': sessionStorage.getItem('token')
		}
	}
	return new Promise((resolve, reject) => {
		instant.post(url, params, config)
			.then(res => {
				resolve(res);
			})
			.catch(err =>{
				// if (err.response.data.code != null) {
				// 	_this.$message.error(err.response.data.message)
				// } else {
				// 	_this.$message.error('请求出错：'+res)
				// }
				reject(err)
			})
	});
}

export function del(url, params) {
	let config = {
		headers: {
			"Content-Type" : "application/json",
			'token-pc': sessionStorage.getItem('token')
		}
	}
	return new Promise((resolve, reject) => {
		instant.delete(url, params, config)
			.then(res => {
				resolve(res)
			})
			.catch(err => {
				reject(err)
			})
	})
}


export function uploadFile(url, formData, type) {
	return new Promise((resolve, reject) => {
		instant.post(url, formData , {
			headers : {
				'Content-Type': 'multipart/form-data',
				'token-pc': sessionStorage.getItem('token'),
				'type': type
			}
		})
			.then(res => {
				resolve(res);
			})
			.catch(err =>{
				reject(err)
			})
	});
}




export function download(url) {
	let config = {
		headers: {
			"Content-Type" : "application/json",
			'token-pc': sessionStorage.getItem('token')
		},
		responseType: 'blob'
	}
	return new Promise((resolve, reject) => {
		instant.get(url, config).then(res => {

			let disposition = res.headers['content-disposition']
			let fileName = disposition.substring(disposition.indexOf('filename=') + 9, disposition.length)
			fileName = decodeURI(fileName)
			const blob = res
			const reader = new FileReader()
			reader.readAsDataURL(blob)
			reader.onload = (e) => {
				const a = document.createElement('a')
				a.download = fileName
				a.href = e.target.result
				document.body.appendChild(a)
				a.click()
				document.body.removeChild(a)
			}
		}).catch(err => {
			reject(err)
		})
	})
}

export function downloadWithParam(url, data) {
	let config = {
		headers: {
			"Content-Type" : "application/json",
			'token-pc': sessionStorage.getItem('token')
		},
		responseType: 'blob'
	}
	return new Promise((resolve, reject) => {
		instant.post(url, data, config).then(res => {

			let disposition = res.headers['content-disposition']
			let fileName = disposition.substring(disposition.indexOf('filename=') + 9, disposition.length)
			fileName = decodeURI(fileName)
			const blob = res
			const reader = new FileReader()
			reader.readAsDataURL(blob)
			reader.onload = (e) => {
				const a = document.createElement('a')
				a.download = fileName
				a.href = e.target.result
				document.body.appendChild(a)
				a.click()
				document.body.removeChild(a)
			}
			resolve(null)
		}).catch(err => {
			reject(err)
		})
	})
}

export function exportFile(url, data) {
	let config = {
		headers: {
			"Content-Type" : "application/json",
			'token-pc': sessionStorage.getItem('token')
		},
		responseType: 'blob'
	}

	return new Promise((resolve, reject) => {
		instant.post(url, data, config).then(res => {

			let disposition = res.headers['content-disposition']
			let fileName = disposition.substring(disposition.indexOf('filename=') + 9, disposition.length)
			fileName = decodeURI(fileName)
			const blob = res
			const reader = new FileReader()
			reader.readAsDataURL(blob)
			reader.onload = (e) => {
				const a = document.createElement('a')
				a.download = fileName
				a.href = e.target.result
				document.body.appendChild(a)
				a.click()
				document.body.removeChild(a)
			}
		}).catch(err => {
			reject(err)
		})
	})
}

export default instant