/**
 * 封装请求工具
 */
// #ifdef MP-WEIXIN
// 微信小程序环境 - 开发阶段使用不校验域名，生产需改为正式域名
const BASE_URL = 'http://127.0.0.1:8080/api'
// #endif

// #ifndef MP-WEIXIN
// H5 或其他环境
const BASE_URL = 'http://localhost:8080/api'
// #endif

// 存储 token
const setToken = (token) => {
  uni.setStorageSync('token', token)
}

// 获取 token
const getToken = () => {
  return uni.getStorageSync('token') || ''
}

// 清除 token
const removeToken = () => {
  uni.removeStorageSync('token')
}

/**
 * 封装请求
 */
function request(options) {
  return new Promise((resolve, reject) => {
    const token = getToken()

    uni.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
      },
      success: (res) => {
        const response = res.data
        
        if (response.code === 200) {
          resolve(response.data)
        } else if (response.code === 401) {
          // Token 过期，跳转登录
          removeToken()
          uni.navigateTo({ url: '/pages/login/login' })
          reject(response)
        } else {
          uni.showToast({ title: response.message || '请求失败', icon: 'none' })
          reject(response)
        }
      },
      fail: (err) => {
        console.error('请求失败:', err)
        uni.showToast({ title: '网络请求失败', icon: 'none' })
        reject(err)
      }
    })
  })
}

export default {
  get: (url, data) => request({ url, method: 'GET', data }),
  post: (url, data) => request({ url, method: 'POST', data }),
  put: (url, data) => request({ url, method: 'PUT', data }),
  delete: (url, data) => request({ url, method: 'DELETE', data }),
  setToken,
  getToken,
  removeToken
}
