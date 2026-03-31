<template>
  <view class="profile-container">
    <!-- 用户信息头部 -->
    <view class="profile-header">
      <image class="profile-avatar" src="https://api.dicebear.com/7.x/avataaars/svg?seed=TeacherLi" mode="aspectFill" />
      <view class="profile-info">
        <text class="profile-name">李华 (班主任)</text>
        <text class="profile-id">工号：T89201</text>
      </view>
    </view>

    <!-- 功能菜单 -->
    <view class="menu-section">
      <view class="menu-card">
        <view class="menu-item" @tap="onProfileSettings">
          <text class="menu-icon icon-blue">👤</text>
          <text class="menu-label">个人资料设置</text>
          <text class="menu-arrow">›</text>
        </view>
        <view class="menu-item" @tap="onNotificationSettings">
          <text class="menu-icon icon-orange">🔔</text>
          <text class="menu-label">消息通知</text>
          <view class="switch-wrapper">
            <view :class="['switch', notificationsEnabled ? 'switch-on' : 'switch-off']" @tap="toggleNotifications">
              <view :class="['switch-handle', notificationsEnabled ? 'handle-on' : 'handle-off']"></view>
            </view>
          </view>
        </view>
        <view class="menu-item" @tap="onSystemSettings">
          <text class="menu-icon icon-gray">⚙️</text>
          <text class="menu-label">系统设置</text>
          <text class="menu-arrow">›</text>
        </view>
      </view>
    </view>

    <!-- 退出登录 -->
    <view class="logout-section">
      <button class="logout-btn" @tap="onLogout">
        <text class="logout-icon">🚪</text>
        <text>退出登录</text>
      </button>
    </view>
  </view>
</template>

<script>
import { getProfile, logout, updateNotificationSettings, getNotificationSettings } from '@/utils/api.js'
import request from '@/utils/request.js'

export default {
  data() {
    return {
      userProfile: null,
      notificationsEnabled: true
    }
  },
  onLoad() {
    this.loadProfile()
    this.loadNotificationSettings()
  },
  methods: {
    async loadProfile() {
      try {
        const res = await getProfile()
        this.userProfile = res
      } catch (err) {
        console.error('加载个人信息失败:', err)
      }
    },
    async loadNotificationSettings() {
      try {
        const res = await getNotificationSettings()
        this.notificationsEnabled = res.notifications_enabled !== undefined ? res.notifications_enabled : true
      } catch (err) {
        console.error('加载通知设置失败:', err)
      }
    },
    async toggleNotifications() {
      this.notificationsEnabled = !this.notificationsEnabled
      try {
        await updateNotificationSettings({
          notifications_enabled: this.notificationsEnabled
        })
      } catch (err) {
        console.error('更新通知设置失败:', err)
        // 恢复状态
        this.notificationsEnabled = !this.notificationsEnabled
      }
    },
    onProfileSettings() {
      uni.showToast({
        title: '个人资料设置',
        icon: 'none'
      })
    },
    onNotificationSettings() {
      uni.showToast({
        title: '消息通知设置',
        icon: 'none'
      })
    },
    onSystemSettings() {
      uni.showToast({
        title: '系统设置',
        icon: 'none'
      })
    },
    async onLogout() {
      uni.showModal({
        title: '确认退出',
        content: '确定要退出登录吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await logout()
            } catch (err) {
              console.error('登出失败:', err)
            } finally {
              request.removeToken()
              uni.reLaunch({
                url: '/pages/login/login'
              })
            }
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.profile-container {
  min-height: 100vh;
  background-color: #f9fafb;
}

/* 用户信息头部 */
.profile-header {
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  padding: 24px 24px 48px;
  display: flex;
  align-items: center;
  color: #ffffff;
}

.profile-avatar {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.2);
  border: 2px solid rgba(255, 255, 255, 0.5);
  margin-right: 16px;
}

.profile-info {
  display: flex;
  flex-direction: column;
}

.profile-name {
  font-size: 20px;
  font-weight: 700;
}

.profile-id {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  margin-top: 4px;
}

/* 功能菜单 */
.menu-section {
  padding: 0 16px;
  margin-top: -24px;
}

.menu-card {
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #f3f4f6;
  overflow: hidden;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #f9fafb;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-item:active {
  background-color: #f9fafb;
}

.menu-icon {
  font-size: 20px;
  margin-right: 12px;
}

.icon-blue {
  color: #3b82f6;
}

.icon-orange {
  color: #f97316;
}

.icon-gray {
  color: #6b7280;
}

.menu-label {
  flex: 1;
  font-size: 15px;
  color: #1f2937;
}

.menu-arrow {
  font-size: 20px;
  color: #d1d5db;
}

/* 开关样式 */
.switch-wrapper {
  display: flex;
  align-items: center;
}

.switch {
  width: 44px;
  height: 24px;
  border-radius: 12px;
  position: relative;
  transition: background-color 0.3s;
}

.switch-on {
  background-color: #3b82f6;
}

.switch-off {
  background-color: #e5e7eb;
}

.switch-handle {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #ffffff;
  position: absolute;
  top: 2px;
  transition: left 0.3s;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.handle-on {
  left: 22px;
}

.handle-off {
  left: 2px;
}

/* 退出登录 */
.logout-section {
  padding: 24px 16px;
}

.logout-btn {
  width: 100%;
  background-color: #ffffff;
  color: #ef4444;
  font-size: 15px;
  font-weight: 500;
  padding: 16px 0;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #f3f4f6;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logout-btn::after {
  border: none;
}

.logout-btn:active {
  background-color: #f9fafb;
}

.logout-icon {
  margin-right: 8px;
  font-size: 18px;
}
</style>
