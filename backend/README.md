# 智慧校园管理系统后端

基于 Spring Boot 3 + Redis + MySQL + JDK 17 的学生管理系统后端服务。

## 技术栈

- **JDK**: 17
- **框架**: Spring Boot 3.2.0
- **数据库**: MySQL 8.0+
- **缓存**: Redis
- **ORM**: Spring Data JPA
- **认证**: JWT
- **安全**: Spring Security

## 快速开始

### 1. 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 2. 数据库配置

1. 创建数据库并导入初始数据：
```sql
-- 执行 src/main/resources/schema.sql
```

2. 修改 `src/main/resources/application.yml` 中的数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/school_management
    username: root
    password: your_password
```

3. 修改 Redis 配置：
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

### 3. 运行项目

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

或者直接运行主类 `SchoolManagementApplication.java`

### 4. 默认账号

| 角色 | 用户名 | 密码 |
|-----|-------|-----|
| 管理员 | admin | admin123 |
| 教师 | lihua | teacher123 |

## API 接口文档

### 认证模块

| 接口 | 方法 | 描述 |
|-----|------|-----|
| `/api/auth/login` | POST | 用户登录 |
| `/api/auth/logout` | POST | 用户登出 |
| `/api/auth/me` | GET | 获取当前用户信息 |

### 首页模块

| 接口 | 方法 | 描述 |
|-----|------|-----|
| `/api/home/stats` | GET | 获取首页统计数据 |
| `/api/home/quick-actions` | GET | 获取快捷操作菜单 |
| `/api/home/notifications` | GET | 获取通知列表 |
| `/api/home/notifications/{id}/read` | PUT | 标记通知为已读 |

### 学生管理模块

| 接口 | 方法 | 描述 |
|-----|------|-----|
| `/api/students` | GET | 获取学生列表 |
| `/api/students/{id}` | GET | 获取学生详情 |
| `/api/students` | POST | 创建学生 |
| `/api/students/{id}` | PUT | 更新学生信息 |
| `/api/students/{id}` | DELETE | 删除学生 |
| `/api/students/{id}/scores` | GET | 获取学生成绩列表 |

### 个人中心模块

| 接口 | 方法 | 描述 |
|-----|------|-----|
| `/api/profile` | GET | 获取个人信息 |
| `/api/profile` | PUT | 更新个人信息 |
| `/api/profile/password` | PUT | 修改密码 |
| `/api/profile/notification-settings` | GET | 获取通知设置 |
| `/api/profile/notification-settings` | PUT | 更新通知设置 |

### 考勤模块

| 接口 | 方法 | 描述 |
|-----|------|-----|
| `/api/attendance/stats` | GET | 获取考勤统计 |
| `/api/attendance` | POST | 提交考勤 |
| `/api/attendance/student/{studentId}` | GET | 获取学生考勤记录 |

### 成绩模块

| 接口 | 方法 | 描述 |
|-----|------|-----|
| `/api/scores/student/{studentId}` | GET | 获取学生成绩列表 |
| `/api/scores` | POST | 录入成绩 |
| `/api/scores/{id}` | PUT | 更新成绩 |
| `/api/scores/{id}` | DELETE | 删除成绩 |

## 请求示例

### 登录
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"lihua","password":"teacher123"}'
```

### 获取学生列表
```bash
curl -X GET "http://localhost:8080/api/students?page=1&page_size=20" \
  -H "Authorization: Bearer {token}"
```

### 获取学生详情
```bash
curl -X GET http://localhost:8080/api/students/stu001 \
  -H "Authorization: Bearer {token}"
```

## 错误码

| 错误码 | 说明 |
|-------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权/Token 过期 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |
| 1001 | 用户名或密码错误 |
| 1002 | Token 无效 |
| 2001 | 学生不存在 |
| 2002 | 学号已存在 |
| 3001 | 通知不存在 |

## 项目结构

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/school/
│   │   │   ├── config/           # 配置类
│   │   │   ├── controller/       # 控制器层
│   │   │   ├── dto/              # 数据传输对象
│   │   │   ├── entity/           # 实体类
│   │   │   ├── exception/        # 异常处理
│   │   │   ├── repository/       # 数据访问层
│   │   │   ├── service/          # 业务逻辑层
│   │   │   ├── util/             # 工具类
│   │   │   └── SchoolManagementApplication.java
│   │   └── resources/
│   │       ├── application.yml   # 配置文件
│   │       └── schema.sql        # 数据库初始化脚本
│   └── test/
└── pom.xml
```

## 注意事项

1. 首次运行前请确保 MySQL 和 Redis 服务已启动
2. 默认数据库密码为 `root`，请根据实际情况修改
3. JWT Token 有效期为 7 天
4. 生产环境请修改默认密码和 JWT 密钥
