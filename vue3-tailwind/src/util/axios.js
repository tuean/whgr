import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '/@/router/index'
import { localGet } from './index'
import env from '/config/index'


// 这边由于后端没有区分测试和正式，姑且都写成一个接口。
axios.defaults.baseURL = env.baseUrl
// 携带 cookie，对目前的项目没有什么作用，因为我们是 token 鉴权
axios.defaults.withCredentials = true
// 请求头，headers 信息
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
axios.defaults.headers['token'] = localGet('token') || ''
// 默认 post 请求，使用 application/json 形式
axios.defaults.headers.post['Content-Type'] = 'application/json'

// 请求拦截器，内部根据返回值，重新组装，统一管理。
axios.interceptors.response.use(res => {
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
})

export function get(url){
	let config = {
		headers: {
			'Content-Type' : 'application/json',
			'token-pc': sessionStorage.getItem('token')
		}
	}
	return new Promise((resolve, reject) => {
		axios.get(url, config)
			.then(res => {
				resolve(res.data)
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
		axios.get(whole, config)
			.then(res => {
				resolve(res.data)
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
		axios.post(url, params, config)
			.then(res => {
				resolve(res.data);
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
		axios.delete(url, params, config)
			.then(res => {
				resolve(res.data)
			})
			.catch(err => {
				reject(err)
			})
	})
}


export function uploadFile(url, formData, type) {
	return new Promise((resolve, reject) => {
		axios.post(url, formData , {
			headers : {
				'Content-Type': 'multipart/form-data',
				'token-pc': sessionStorage.getItem('token'),
				'type': type
			}
		})
			.then(res => {
				resolve(res.data);
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
		axios.get(url, config).then(res => {

			let disposition = res.headers['content-disposition']
			let fileName = disposition.substring(disposition.indexOf('filename=') + 9, disposition.length)
			fileName = decodeURI(fileName)
			const blob = res.data
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
		axios.post(url, data, config).then(res => {

			let disposition = res.headers['content-disposition']
			let fileName = disposition.substring(disposition.indexOf('filename=') + 9, disposition.length)
			fileName = decodeURI(fileName)
			const blob = res.data
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
		axios.post(url, data, config).then(res => {

			let disposition = res.headers['content-disposition']
			let fileName = disposition.substring(disposition.indexOf('filename=') + 9, disposition.length)
			fileName = decodeURI(fileName)
			const blob = res.data
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

export default axios