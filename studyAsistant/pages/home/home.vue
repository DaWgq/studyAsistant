<template>
  <view class="home-container">
    <!-- 自定义导航栏 -->
    <view class="custom-header">
      <view class="status-bar"></view>
      <view class="header-content">
        <text class="header-title">智慧校园管理系统</text>
        <text class="header-icon">🔔</text>
      </view>
    </view>

    <scroll-view scroll-y class="scroll-content">
      <!-- 欢迎卡片 -->
      <view class="card welcome-card">
        <view class="welcome-text">
          <text class="welcome-title">李老师，早上好！</text>
          <text class="welcome-subtitle">{{ welcomeText || '今天是你陪伴学生的第 342 天' }}</text>
        </view>
        <image class="teacher-avatar" src="https://api.dicebear.com/7.x/avataaars/svg?seed=TeacherLi" mode="aspectFill" />
      </view>

      <!-- 数据看板 -->
      <view class="stats-grid">
        <view class="stat-card gradient-blue">
          <view class="stat-header">
            <view class="stat-icon">👥</view>
            <text class="stat-tag">总人数</text>
          </view>
          <text class="stat-value">{{ stats.totalStudents.toLocaleString() || '1,248' }}</text>
          <text class="stat-desc">较上学期 +12</text>
        </view>
        <view class="stat-card gradient-emerald">
          <view class="stat-header">
            <view class="stat-icon">📅</view>
            <text class="stat-tag">今日出勤</text>
          </view>
          <text class="stat-value">{{ stats.attendanceRate }}%</text>
          <text class="stat-desc">{{ stats.absentCount }}人请假/缺勤</text>
        </view>
      </view>

      <!-- 快捷操作 -->
      <view class="section">
        <text class="section-title">常用功能</text>
        <view class="quick-actions">
          <view class="action-item" v-for="(item, index) in quickActions" :key="index">
            <view :class="['action-icon', item.bg]">
              <text>{{ item.icon }}</text>
            </view>
            <text class="action-label">{{ item.label }}</text>
          </view>
        </view>
      </view>

      <!-- 待办事项 -->
      <view class="section">
        <view class="section-header">
          <text class="section-title">最新通知 & 待办</text>
          <text class="section-more">查看全部</text>
        </view>
        <view class="notifications-card">
          <view class="notification-item" v-for="(item, index) in notifications" :key="index">
            <view :class="['notification-dot', item.type === '待办' ? 'dot-orange' : 'dot-blue']"></view>
            <view class="notification-content">
              <text class="notification-title">{{ item.title }}</text>
              <text class="notification-time">{{ item.time }}</text>
            </view>
            <text class="notification-arrow">›</text>
          </view>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script>
import { getHomeStats, getNotifications, getQuickActions } from '@/utils/api.js'

export default {
  data() {
    return {
      quickActions: [],
      notifications: [],
      stats: {
        totalStudents: 0,
        attendanceRate: 0,
        absentCount: 0,
        teacherDays: 0
      },
      welcomeText: ''
    }
  },
  onLoad() {
    this.loadHomeData()
  },
  onShow() {
    this.loadHomeData()
  },
  methods: {
    async loadHomeData() {
      // 加载统计数据
      this.loadStats()
      // 加载通知
      this.loadNotifications()
      // 加载快捷操作
      this.loadQuickActions()
    },
    async loadStats() {
      try {
        const res = await getHomeStats()
        this.stats = {
          totalStudents: res.total_students || 0,
          attendanceRate: res.today_attendance_rate || 0,
          absentCount: res.today_absent_count || 0,
          teacherDays: res.teacher_days || 0
        }
        this.welcomeText = `今天是你陪伴学生的第 ${this.stats.teacherDays} 天`
      } catch (err) {
        console.error('加载统计数据失败:', err)
      }
    },
    async loadNotifications() {
      try {
        const res = await getNotifications(1, 10)
        this.notifications = res.list || []
      } catch (err) {
        console.error('加载通知失败:', err)
      }
    },
    async loadQuickActions() {
      try {
        const res = await getQuickActions()
        this.quickActions = res.map(item => ({
          ...item,
          bg: this.getActionBg(item.id)
        }))
      } catch (err) {
        console.error('加载快捷操作失败:', err)
        // 使用默认数据
        this.quickActions = [
          { id: 1, icon: '➕', label: '录入学生', bg: 'bg-blue-50' },
          { id: 2, icon: '📅', label: '考勤打卡', bg: 'bg-orange-50' },
          { id: 3, icon: '📖', label: '成绩管理', bg: 'bg-purple-50' },
          { id: 4, icon: '🎓', label: '综合评价', bg: 'bg-green-50' },
        ]
      }
    },
    getActionBg(id) {
      const bgMap = {
        1: 'bg-blue-50',
        2: 'bg-orange-50',
        3: 'bg-purple-50',
        4: 'bg-green-50'
      }
      return bgMap[id] || 'bg-blue-50'
    }
  }
}
</script>

<style lang="scss" scoped>
.home-container {
  min-height: 100vh;
  background-color: #f9fafb;
}

/* 自定义导航栏 */
.custom-header {
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  padding-top: var(--status-bar-height);
  
  .status-bar {
    height: var(--status-bar-height);
  }
  
  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 16px;
  }
  
  .header-title {
    font-size: 17px;
    font-weight: 500;
    color: #ffffff;
  }
  
  .header-icon {
    font-size: 20px;
  }
}

.scroll-content {
  height: calc(100vh - var(--status-bar-height) - 50px);
}

/* 欢迎卡片 */
.welcome-card {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin: 16px;
  margin-bottom: 0;
}

.welcome-text {
  flex: 1;
}

.welcome-title {
  display: block;
  font-size: 20px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 4px;
}

.welcome-subtitle {
  display: block;
  font-size: 13px;
  color: #6b7280;
}

.teacher-avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background-color: #eff6ff;
  margin-left: 12px;
}

/* 数据看板 */
.stats-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
  padding: 16px;
}

.stat-card {
  border-radius: 16px;
  padding: 16px;
  color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.gradient-blue {
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  box-shadow: 0 1px 3px rgba(59, 130, 246, 0.3);
}

.gradient-emerald {
  background: linear-gradient(135deg, #10b981, #059669);
  box-shadow: 0 1px 3px rgba(16, 185, 129, 0.3);
}

.stat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.stat-icon {
  font-size: 20px;
  opacity: 0.8;
}

.stat-tag {
  font-size: 12px;
  background-color: rgba(255, 255, 255, 0.2);
  padding: 2px 8px;
  border-radius: 12px;
}

.stat-value {
  font-size: 30px;
  font-weight: 700;
  display: block;
}

.stat-desc {
  font-size: 13px;
  opacity: 0.8;
  display: block;
  margin-top: 4px;
}

/* 快捷操作 */
.section {
  padding: 0 16px;
  margin-bottom: 16px;
}

.section-title {
  display: block;
  font-size: 16px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 12px;
  padding-left: 4px;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
  padding-left: 4px;
}

.section-more {
  font-size: 12px;
  color: #9ca3af;
}

.quick-actions {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.action-icon {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 8px;
  font-size: 24px;
}

.bg-blue-50 {
  background-color: #eff6ff;
}

.bg-orange-50 {
  background-color: #fff7ed;
}

.bg-purple-50 {
  background-color: #faf5ff;
}

.bg-green-50 {
  background-color: #f0fdf4;
}

.action-label {
  font-size: 12px;
  color: #4b5563;
}

/* 通知列表 */
.notifications-card {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #f3f4f6;
  overflow: hidden;
}

.notification-item {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #f9fafb;
}

.notification-item:last-child {
  border-bottom: none;
}

.notification-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 12px;
}

.dot-orange {
  background-color: #fb923c;
}

.dot-blue {
  background-color: #3b82f6;
}

.notification-content {
  flex: 1;
}

.notification-title {
  display: block;
  font-size: 14px;
  color: #1f2937;
}

.notification-time {
  display: block;
  font-size: 12px;
  color: #9ca3af;
  margin-top: 4px;
}

.notification-arrow {
  font-size: 20px;
  color: #d1d5db;
  margin-left: 8px;
}
</style>
