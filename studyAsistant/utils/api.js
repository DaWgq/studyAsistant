/**
 * API 接口封装
 */
import request from './request.js'

// ============ 认证模块 ============

/**
 * 用户登录
 */
export function login(username, password) {
  return request.post('/auth/login', { username, password })
}

/**
 * 用户登出
 */
export function logout() {
  return request.post('/auth/logout')
}

/**
 * 获取当前用户信息
 */
export function getCurrentUser() {
  return request.get('/auth/me')
}

// ============ 首页模块 ============

/**
 * 获取首页统计数据
 */
export function getHomeStats() {
  return request.get('/home/stats')
}

/**
 * 获取快捷操作菜单
 */
export function getQuickActions() {
  return request.get('/home/quick-actions')
}

/**
 * 获取通知列表
 */
export function getNotifications(page = 1, pageSize = 10) {
  return request.get('/home/notifications', { page, page_size: pageSize })
}

/**
 * 标记通知为已读
 */
export function markNotificationRead(id) {
  return request.put(`/home/notifications/${id}/read`)
}

/**
 * 获取未读通知数量
 */
export function getUnreadNotificationCount() {
  return request.get('/home/notifications/unread-count')
}

// ============ 学生管理模块 ============

/**
 * 获取学生列表
 */
export function getStudents(page = 1, pageSize = 20, grade = '', status = '', keyword = '') {
  return request.get('/students', { page, page_size: pageSize, grade, status, keyword })
}

/**
 * 获取学生详情
 */
export function getStudentDetail(id) {
  return request.get(`/students/${id}`)
}

/**
 * 创建学生
 */
export function createStudent(data) {
  return request.post('/students', data)
}

/**
 * 更新学生信息
 */
export function updateStudent(id, data) {
  return request.put(`/students/${id}`, data)
}

/**
 * 删除学生
 */
export function deleteStudent(id) {
  return request.delete(`/students/${id}`)
}

/**
 * 获取学生成绩列表
 */
export function getStudentScores(id) {
  return request.get(`/students/${id}/scores`)
}

// ============ 个人中心模块 ============

/**
 * 获取个人信息
 */
export function getProfile() {
  return request.get('/profile')
}

/**
 * 更新个人信息
 */
export function updateProfile(data) {
  return request.put('/profile', data)
}

/**
 * 修改密码
 */
export function changePassword(oldPassword, newPassword) {
  return request.put('/profile/password', { old_password: oldPassword, new_password: newPassword })
}

/**
 * 获取通知设置
 */
export function getNotificationSettings() {
  return request.get('/profile/notification-settings')
}

/**
 * 更新通知设置
 */
export function updateNotificationSettings(settings) {
  return request.put('/profile/notification-settings', settings)
}

// ============ 考勤模块 ============

/**
 * 获取考勤统计
 */
export function getAttendanceStats(date = '') {
  return request.get('/attendance/stats', { date })
}

/**
 * 提交考勤
 */
export function submitAttendance(data) {
  return request.post('/attendance', data)
}

/**
 * 获取学生考勤记录
 */
export function getStudentAttendance(studentId, page = 1, size = 30) {
  return request.get(`/attendance/student/${studentId}`, { page, size })
}

// ============ 成绩模块 ============

/**
 * 获取学生成绩列表
 */
export function getScoresByStudentId(studentId) {
  return request.get(`/scores/student/${studentId}`)
}

/**
 * 录入成绩
 */
export function createScore(data) {
  return request.post('/scores', data)
}

/**
 * 更新成绩
 */
export function updateScore(id, data) {
  return request.put(`/scores/${id}`, data)
}

/**
 * 删除成绩
 */
export function deleteScore(id) {
  return request.delete(`/scores/${id}`)
}
