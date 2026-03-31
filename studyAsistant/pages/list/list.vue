<template>
  <view class="list-container">
    <!-- 搜索栏 -->
    <view class="search-bar">
      <view class="search-input-wrapper">
        <text class="search-icon">🔍</text>
        <input 
          class="search-input" 
          placeholder="搜索姓名、学号或班级..." 
          placeholder-class="search-placeholder"
          v-model="searchQuery"
        />
      </view>
    </view>

    <!-- 学生列表 -->
    <view class="students-wrapper">
      <view 
        class="student-card" 
        v-for="(student, index) in filteredStudents" 
        :key="index"
        @tap="onStudentTap(student)"
      >
        <image class="student-avatar" :src="student.avatar" mode="aspectFill" />
        <view class="student-info">
          <view class="student-header">
            <text class="student-name">{{ student.name }}</text>
            <text :class="['student-status', student.status === '在校' ? 'status-in' : 'status-leave']">{{ student.status }}</text>
          </view>
          <view class="student-detail">
            <text>{{ student.id }}</text>
            <text class="divider">|</text>
            <text>{{ student.grade }}</text>
          </view>
        </view>
        <text class="student-arrow">›</text>
      </view>

      <!-- 空状态 -->
      <view class="empty-state" v-if="filteredStudents.length === 0">
        <text class="empty-icon">👤</text>
        <text class="empty-text">未找到匹配的学生记录</text>
      </view>
    </view>
  </view>
</template>

<script>
import { getStudents } from '@/utils/api.js'

export default {
  data() {
    return {
      students: [],
      searchQuery: '',
      page: 1,
      pageSize: 20,
      total: 0,
      loading: false
    }
  },
  computed: {
    filteredStudents() {
      if (!this.searchQuery) {
        return this.students
      }
      return this.students.filter(student =>
        student.name.includes(this.searchQuery) ||
        student.student_no.includes(this.searchQuery) ||
        student.grade.includes(this.searchQuery)
      )
    }
  },
  onLoad() {
    this.loadStudents()
  },
  onPullDownRefresh() {
    this.page = 1
    this.loadStudents().finally(() => {
      uni.stopPullDownRefresh()
    })
  },
  onReachBottom() {
    if (this.students.length < this.total) {
      this.page++
      this.loadStudents()
    }
  },
  methods: {
    async loadStudents() {
      this.loading = true
      try {
        const res = await getStudents(this.page, this.pageSize, '', '', this.searchQuery)
        const newList = res.list || []
        
        if (this.page === 1) {
          this.students = newList
        } else {
          this.students = [...this.students, ...newList]
        }
        
        this.total = res.total || 0
      } catch (err) {
        console.error('加载学生列表失败:', err)
        // 加载失败时使用空数组
        if (this.page === 1) {
          this.students = []
        }
      } finally {
        this.loading = false
      }
    },
    onStudentTap(student) {
      uni.navigateTo({
        url: `/pages/detail/detail?id=${student.id}`
      })
    },
    onSearch() {
      this.page = 1
      this.loadStudents()
    }
  }
}
</script>

<style lang="scss" scoped>
.list-container {
  min-height: 100vh;
  background-color: #f9fafb;
}

/* 搜索栏 */
.search-bar {
  background-color: #ffffff;
  padding: 16px;
  position: sticky;
  top: 0;
  z-index: 10;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid #f3f4f6;
}

.search-input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.search-icon {
  position: absolute;
  left: 12px;
  font-size: 16px;
  z-index: 1;
}

.search-input {
  width: 100%;
  background-color: #f3f4f6;
  border-radius: 9999px;
  padding: 10px 16px 10px 40px;
  font-size: 13px;
  outline: none;
}

.search-placeholder {
  color: #9ca3af;
}

/* 学生列表 */
.students-wrapper {
  padding: 16px;
}

.student-card {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: center;
  margin-bottom: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #f3f4f6;
}

.student-card:active {
  transform: scale(0.98);
}

.student-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background-color: #eff6ff;
  margin-right: 16px;
}

.student-info {
  flex: 1;
}

.student-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.student-name {
  font-size: 16px;
  font-weight: 700;
  color: #1f2937;
}

.student-status {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 12px;
}

.status-in {
  background-color: #dcfce7;
  color: #166534;
}

.status-leave {
  background-color: #ffedd5;
  color: #9a3412;
}

.student-detail {
  display: flex;
  align-items: center;
  font-size: 12px;
  color: #6b7280;
  margin-top: 4px;
}

.divider {
  margin: 0 8px;
}

.student-arrow {
  font-size: 20px;
  color: #d1d5db;
  margin-left: 8px;
}

/* 空状态 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 0;
  color: #9ca3af;
}

.empty-icon {
  font-size: 48px;
  margin-bottom: 12px;
  opacity: 0.2;
}

.empty-text {
  font-size: 14px;
}
</style>
