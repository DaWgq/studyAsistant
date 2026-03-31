# 前端 API 对接说明

## 配置说明

### 后端 API 地址

在 `utils/request.js` 中配置后端 API 地址：

```javascript
const BASE_URL = 'http://localhost:8080/api'
```

**注意**: 
- 微信小程序需要使用 HTTPS
- 开发环境可使用 http://localhost:8080
- 生产环境请修改为实际域名

### 跨域配置

开发环境如遇到跨域问题，可在 `manifest.json` 中配置：

```json
"mp-weixin": {
  "appid": "wxcc1baa57046d4fc7",
  "setting": {
    "urlCheck": false
  }
}
```

## API 接口列表

### 认证模块

| 方法 | 接口 | 说明 |
|-----|------|------|
| POST | /api/auth/login | 用户登录 |
| POST | /api/auth/logout | 用户登出 |
| GET | /api/auth/me | 获取当前用户信息 |

### 首页模块

| 方法 | 接口 | 说明 |
|-----|------|------|
| GET | /api/home/stats | 获取首页统计数据 |
| GET | /api/home/quick-actions | 获取快捷操作菜单 |
| GET | /api/home/notifications | 获取通知列表 |
| PUT | /api/home/notifications/{id}/read | 标记通知为已读 |

### 学生管理模块

| 方法 | 接口 | 说明 |
|-----|------|------|
| GET | /api/students | 获取学生列表（支持分页、搜索、筛选） |
| GET | /api/students/{id} | 获取学生详情 |
| POST | /api/students | 创建学生 |
| PUT | /api/students/{id} | 更新学生信息 |
| DELETE | /api/students/{id} | 删除学生 |
| GET | /api/students/{id}/scores | 获取学生成绩列表 |

### 个人中心模块

| 方法 | 接口 | 说明 |
|-----|------|------|
| GET | /api/profile | 获取个人信息 |
| PUT | /api/profile | 更新个人信息 |
| PUT | /api/profile/password | 修改密码 |
| GET | /api/profile/notification-settings | 获取通知设置 |
| PUT | /api/profile/notification-settings | 更新通知设置 |

### 考勤模块

| 方法 | 接口 | 说明 |
|-----|------|------|
| GET | /api/attendance/stats | 获取考勤统计 |
| POST | /api/attendance | 提交考勤 |
| GET | /api/attendance/student/{studentId} | 获取学生考勤记录 |

### 成绩模块

| 方法 | 接口 | 说明 |
|-----|------|------|
| GET | /api/scores/student/{studentId} | 获取学生成绩列表 |
| POST | /api/scores | 录入成绩 |
| PUT | /api/scores/{id} | 更新成绩 |
| DELETE | /api/scores/{id} | 删除成绩 |

## 使用示例

### 1. 用户登录

```javascript
import { login } from '@/utils/api.js'
import { setToken } from '@/utils/request.js'

async handleLogin() {
  try {
    const res = await login('lihua', 'teacher123')
    setToken(res.token)
    // 登录成功，跳转首页
    uni.switchTab({ url: '/pages/home/home' })
  } catch (err) {
    console.error('登录失败:', err)
  }
}
```

### 2. 获取学生列表

```javascript
import { getStudents } from '@/utils/api.js'

async loadStudents() {
  try {
    const res = await getStudents(1, 20, '', '', '张三')
    console.log('学生列表:', res.list)
    console.log('总数:', res.total)
  } catch (err) {
    console.error('加载失败:', err)
  }
}
```

### 3. 获取学生详情

```javascript
import { getStudentDetail } from '@/utils/api.js'

async loadStudentDetail(id) {
  try {
    const res = await getStudentDetail('stu001')
    console.log('学生详情:', res)
  } catch (err) {
    console.error('加载失败:', err)
  }
}
```

### 4. 获取首页统计数据

```javascript
import { getHomeStats, getNotifications } from '@/utils/api.js'

async loadHomeData() {
  try {
    // 加载统计数据
    const stats = await getHomeStats()
    console.log('总人数:', stats.total_students)
    console.log('出勤率:', stats.today_attendance_rate)
    console.log('陪伴天数:', stats.teacher_days)
    
    // 加载通知列表
    const notifications = await getNotifications(1, 10)
    console.log('通知列表:', notifications.list)
  } catch (err) {
    console.error('加载失败:', err)
  }
}
```

## 错误处理

所有 API 请求都已封装统一的错误处理：

- **200**: 成功
- **400**: 请求参数错误
- **401**: Token 过期/未授权（自动跳转登录页）
- **500**: 服务器错误

如需自定义错误处理，可在请求时捕获异常：

```javascript
try {
  const res = await getStudents(1, 20)
} catch (err) {
  // 自定义错误处理
  uni.showToast({ title: '加载失败', icon: 'none' })
}
```

## Token 管理

Token 自动管理，无需手动处理：

- 登录后自动存储 Token
- 请求时自动携带 Token
- Token 过期自动跳转登录

手动管理 Token：

```javascript
import { setToken, getToken, removeToken } from '@/utils/request.js'

// 存储 Token
setToken('your_token')

// 获取 Token
const token = getToken()

// 清除 Token
removeToken()
```

## 测试账号

| 角色 | 用户名 | 密码 |
|-----|-------|-----|
| 教师 | lihua | teacher123 |
| 管理员 | admin | admin123 |

## 注意事项

1. **首次运行前**确保后端服务已启动
2. **微信小程序**需要配置合法域名（生产环境）
3. **开发环境**可关闭域名校验
4. **请求超时**默认 60 秒，可在 request.js 中修改
5. **数据格式**所有日期格式为 YYYY-MM-DD

## 常见问题

### Q: 请求失败，提示网络错误
A: 检查后端服务是否启动，地址是否正确

### Q: 跨域错误
A: 开发环境在 manifest.json 中关闭 urlCheck，或配置代理

### Q: Token 过期
A: 自动跳转登录页，重新登录即可

### Q: 数据为空
A: 检查数据库中是否有数据，查看 schema.sql 初始化脚本
