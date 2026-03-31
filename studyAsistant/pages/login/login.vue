<template>
  <view class="login-container">
    <view class="login-content">
      <!-- Logo 区域 -->
      <view class="logo-section">
        <view class="logo-icon">🎓</view>
        <text class="logo-title">智慧校园管理系统</text>
        <text class="logo-subtitle">欢迎登录</text>
      </view>

      <!-- 登录表单 -->
      <view class="form-section">
        <view class="form-item">
          <view class="input-label">
            <text class="label-icon">👤</text>
            <text>用户名</text>
          </view>
          <input
            class="form-input"
            v-model="username"
            placeholder="请输入用户名"
            placeholder-class="input-placeholder"
          />
        </view>

        <view class="form-item">
          <view class="input-label">
            <text class="label-icon">🔒</text>
            <text>密码</text>
          </view>
          <input
            class="form-input"
            type="password"
            v-model="password"
            placeholder="请输入密码"
            placeholder-class="input-placeholder"
          />
        </view>

        <button class="login-btn" :loading="loading" @tap="handleLogin">
          登 录
        </button>

        <view class="tips-section">
          <text class="tips-title">测试账号：</text>
          <text class="tips-text">教师：lihua / teacher123</text>
          <text class="tips-text">管理员：admin / admin123</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { login } from '@/utils/api.js'
import request from '@/utils/request.js'

export default {
  data() {
    return {
      username: '',
      password: '',
      loading: false
    }
  },
  onLoad() {
    // 检查是否已登录
    const token = uni.getStorageSync('token')
    if (token) {
      uni.switchTab({
        url: '/pages/home/home'
      })
    }
  },
  methods: {
    async handleLogin() {
      if (!this.username.trim()) {
        uni.showToast({ title: '请输入用户名', icon: 'none' })
        return
      }
      if (!this.password.trim()) {
        uni.showToast({ title: '请输入密码', icon: 'none' })
        return
      }

      this.loading = true
      try {
        const res = await login(this.username, this.password)
        request.setToken(res.token)

        uni.showToast({
          title: '登录成功',
          icon: 'success'
        })

        setTimeout(() => {
          uni.switchTab({
            url: '/pages/home/home'
          })
        }, 1500)
      } catch (err) {
        console.error('登录失败:', err)
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

.login-content {
  width: 100%;
  max-width: 400px;
}

/* Logo 区域 */
.logo-section {
  text-align: center;
  margin-bottom: 48px;
}

.logo-icon {
  font-size: 64px;
  margin-bottom: 16px;
}

.logo-title {
  display: block;
  font-size: 24px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 8px;
}

.logo-subtitle {
  display: block;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
}

/* 表单区域 */
.form-section {
  background-color: #ffffff;
  border-radius: 16px;
  padding: 32px 24px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
}

.form-item {
  margin-bottom: 24px;
}

.input-label {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  font-size: 14px;
  color: #374151;
  font-weight: 500;
}

.label-icon {
  margin-right: 6px;
  font-size: 16px;
}

.form-input {
  width: 100%;
  height: 48px;
  background-color: #f3f4f6;
  border-radius: 12px;
  padding: 0 16px;
  font-size: 15px;
  border: 1px solid transparent;
  transition: all 0.3s;
}

.form-input:focus {
  background-color: #ffffff;
  border-color: #3b82f6;
}

.input-placeholder {
  color: #9ca3af;
}

/* 登录按钮 */
.login-btn {
  width: 100%;
  height: 48px;
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  color: #ffffff;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  border: none;
  margin-top: 8px;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.login-btn:active {
  opacity: 0.9;
}

/* 提示区域 */
.tips-section {
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid #f3f4f6;
  text-align: center;
}

.tips-title {
  display: block;
  font-size: 12px;
  color: #6b7280;
  margin-bottom: 8px;
}

.tips-text {
  display: block;
  font-size: 12px;
  color: #9ca3af;
  margin-top: 4px;
}
</style>
