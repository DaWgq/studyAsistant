<template>
  <view class="detail-container" v-if="student">
    <!-- 头部信息 -->
    <view class="detail-header">
      <view class="status-badge">{{ student.status }}</view>
      <image class="detail-avatar" :src="student.avatar" mode="aspectFill" />
      <text class="detail-name">{{ student.name }}</text>
      <text class="detail-id">学号：{{ student.id }}</text>
      
      <view class="action-buttons">
        <button class="action-btn btn-primary" @tap="onCallPhone">
          <text class="btn-icon">📞</text>
          <text>拨打电话</text>
        </button>
        <button class="action-btn btn-secondary" @tap="onSendMessage">
          <text class="btn-icon">✉️</text>
          <text>发送消息</text>
        </button>
      </view>
    </view>

    <!-- 基础信息卡片 -->
    <view class="info-card">
      <view class="card-title">基础信息</view>
      <view class="info-list">
        <view class="info-item" v-for="(item, index) in baseInfo" :key="index">
          <text class="info-label">{{ item.label }}</text>
          <text class="info-value">{{ item.value }}</text>
        </view>
      </view>
    </view>

    <!-- 学习概况 -->
    <view class="info-card">
      <view class="card-header">
        <text class="card-title">学习概况 (本学期)</text>
        <text class="card-more">查看全部成绩</text>
      </view>
      <view class="stats-row">
        <view class="stat-item">
          <text class="stat-value text-blue">89</text>
          <text class="stat-label">平均分</text>
        </view>
        <view class="stat-divider"></view>
        <view class="stat-item">
          <text class="stat-value text-orange">12</text>
          <text class="stat-label">班级排名</text>
        </view>
        <view class="stat-divider"></view>
        <view class="stat-item">
          <text class="stat-value text-green">100%</text>
          <text class="stat-label">出勤率</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { getStudentDetail } from '@/utils/api.js'

export default {
  data() {
    return {
      student: null,
      baseInfo: [],
      studentId: ''
    }
  },
  onLoad(options) {
    this.studentId = options.id || ''
    if (this.studentId) {
      this.loadStudentDetail()
    }
  },
  methods: {
    async loadStudentDetail() {
      try {
        const res = await getStudentDetail(this.studentId)
        this.student = {
          id: res.student_no,
          name: res.name,
          grade: res.grade,
          major: res.major,
          phone: res.phone,
          address: res.address,
          status: res.status,
          avatar: res.avatar_url,
          gender: res.gender,
          enrollment_year: res.enrollment_year
        }
        
        this.baseInfo = [
          { label: '所在班级', value: res.grade },
          { label: '文理分科', value: res.major || '未分科' },
          { label: '联系电话', value: res.phone || '暂无' },
          { label: '家庭住址', value: res.address || '暂无' },
          { label: '性别', value: res.gender || '未知' },
          { label: '入学年份', value: res.enrollment_year || '未知' },
        ]

        // 设置页面标题
        uni.setNavigationBarTitle({
          title: `${res.name} - 学生档案`
        })
      } catch (err) {
        console.error('加载学生详情失败:', err)
        uni.showToast({ title: '加载失败', icon: 'none' })
      }
    },
    onCallPhone() {
      if (this.student && this.student.phone) {
        uni.makePhoneCall({
          phoneNumber: this.student.phone
        })
      }
    },
    onSendMessage() {
      uni.showToast({
        title: '消息功能开发中',
        icon: 'none'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.detail-container {
  min-height: 100vh;
  background-color: #f9fafb;
  padding-bottom: 24px;
}

/* 头部信息 */
.detail-header {
  background-color: #ffffff;
  padding: 32px 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  position: relative;
}

.status-badge {
  position: absolute;
  top: 16px;
  right: 16px;
  background-color: #dcfce7;
  color: #166534;
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 6px;
  border: 1px solid #86efac;
}

.detail-avatar {
  width: 96px;
  height: 96px;
  border-radius: 50%;
  background-color: #eff6ff;
  border: 4px solid #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 12px;
}

.detail-name {
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 4px;
}

.detail-id {
  font-size: 14px;
  color: #6b7280;
  margin-bottom: 24px;
}

.action-buttons {
  display: flex;
  width: 100%;
  gap: 16px;
}

.action-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px 0;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 500;
  border: none;
}

.btn-primary {
  background-color: #eff6ff;
  color: #2563eb;
}

.btn-primary:active {
  background-color: #dbeafe;
}

.btn-secondary {
  background-color: #f9fafb;
  color: #374151;
}

.btn-secondary:active {
  background-color: #f3f4f6;
}

.btn-icon {
  margin-right: 6px;
  font-size: 16px;
}

/* 信息卡片 */
.info-card {
  background-color: #ffffff;
  margin-top: 16px;
  padding: 8px 16px 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border-top: 1px solid #f3f4f6;
  border-bottom: 1px solid #f3f4f6;
}

.card-title {
  font-size: 14px;
  font-weight: 700;
  color: #1f2937;
  padding: 12px 0;
  border-bottom: 1px solid #f9fafb;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #f9fafb;
}

.card-more {
  font-size: 13px;
  color: #2563eb;
}

.info-list {
  margin-top: 8px;
}

.info-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #f9fafb;
}

.info-item:last-child {
  border-bottom: none;
}

.info-label {
  font-size: 14px;
  color: #6b7280;
}

.info-value {
  font-size: 14px;
  color: #1f2937;
  font-weight: 500;
}

/* 学习概况统计 */
.stats-row {
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 16px 0 8px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
}

.text-blue {
  color: #2563eb;
}

.text-orange {
  color: #f97316;
}

.text-green {
  color: #22c55e;
}

.stat-label {
  font-size: 13px;
  color: #6b7280;
  margin-top: 4px;
}

.stat-divider {
  width: 1px;
  height: 40px;
  background-color: #f3f4f6;
}
</style>
