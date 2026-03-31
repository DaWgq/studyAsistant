-- 智慧校园管理系统数据库初始化脚本
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS school_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE school_management;

-- 用户表 (users)
CREATE TABLE IF NOT EXISTS users (
    id              VARCHAR(32) PRIMARY KEY,
    username        VARCHAR(50) NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    real_name       VARCHAR(50) NOT NULL,
    employee_id     VARCHAR(20) UNIQUE,
    role            VARCHAR(20) DEFAULT 'teacher',
    avatar_url      VARCHAR(255),
    phone           VARCHAR(20),
    email           VARCHAR(100),
    status          TINYINT DEFAULT 1,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 学生表 (students)
CREATE TABLE IF NOT EXISTS students (
    id              VARCHAR(32) PRIMARY KEY,
    student_no      VARCHAR(20) UNIQUE NOT NULL,
    name            VARCHAR(50) NOT NULL,
    gender          VARCHAR(10),
    avatar_url      VARCHAR(255),
    grade           VARCHAR(50) NOT NULL,
    major           VARCHAR(50),
    phone           VARCHAR(20),
    address         VARCHAR(255),
    status          VARCHAR(20) DEFAULT '在校',
    enrollment_year INT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_grade (grade),
    INDEX idx_status (status),
    INDEX idx_student_no (student_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 通知表 (notifications)
CREATE TABLE IF NOT EXISTS notifications (
    id              INT PRIMARY KEY AUTO_INCREMENT,
    user_id         VARCHAR(32) NOT NULL,
    title           VARCHAR(200) NOT NULL,
    content         TEXT,
    type            VARCHAR(20) NOT NULL,
    priority        VARCHAR(20) DEFAULT 'normal',
    is_read         TINYINT DEFAULT 0,
    related_type    VARCHAR(50),
    related_id      VARCHAR(32),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_is_read (is_read),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 考勤表 (attendance)
CREATE TABLE IF NOT EXISTS attendance (
    id              INT PRIMARY KEY AUTO_INCREMENT,
    student_id      VARCHAR(32) NOT NULL,
    date            DATE NOT NULL,
    status          VARCHAR(20) NOT NULL,
    remark          VARCHAR(255),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_date (student_id, date),
    INDEX idx_date (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 成绩表 (scores)
CREATE TABLE IF NOT EXISTS scores (
    id              INT PRIMARY KEY AUTO_INCREMENT,
    student_id      VARCHAR(32) NOT NULL,
    subject         VARCHAR(50) NOT NULL,
    exam_type       VARCHAR(50),
    score           DECIMAL(5,2),
    class_rank      INT,
    term            VARCHAR(50),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id),
    INDEX idx_term (term)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 教师陪伴天数表 (teacher_days)
CREATE TABLE IF NOT EXISTS teacher_days (
    id              INT PRIMARY KEY AUTO_INCREMENT,
    user_id         VARCHAR(32) NOT NULL,
    total_days      INT DEFAULT 0,
    start_date      DATE,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入默认管理员用户 (密码：admin123, bcrypt 加密)
INSERT INTO users (id, username, password_hash, real_name, employee_id, role, avatar_url, phone, email, status)
VALUES (
    'admin001',
    'admin',
    '$2a$10$RQ/43AAfFYWLfFIKt4I6NejgOqn8qKrxdL.OAO54TuxsHyYLinSOW',
    '系统管理员',
    'T00001',
    'admin',
    'https://api.dicebear.com/7.x/avataaars/svg?seed=Admin',
    '13900000000',
    'admin@school.edu.cn',
    1
);

-- 插入示例教师用户 (密码：teacher123)
INSERT INTO users (id, username, password_hash, real_name, employee_id, role, avatar_url, phone, email, status)
VALUES (
    'teacher001',
    'lihua',
    '$2a$10$SsmxBc..9njGOPY28wcNPuZEqzEukl/OFH1NCF5x2jcTtW1yTh6tO',
    '李华',
    'T89201',
    'teacher',
    'https://api.dicebear.com/7.x/avataaars/svg?seed=TeacherLi',
    '13900139000',
    'lihua@school.edu.cn',
    1
);

-- 插入示例学生数据
INSERT INTO students (id, student_no, name, gender, grade, major, avatar_url, phone, address, status, enrollment_year) VALUES
('stu001', '2023001', '张伟', '男', '高一 (1) 班', '理科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=ZhangWei', '13800138001', '科技路 128 号阳光小区 3 栋', '在校', 2023),
('stu002', '2023002', '王芳', '女', '高一 (1) 班', '理科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=WangFang', '13800138002', '人民路 56 号', '在校', 2023),
('stu003', '2023003', '李娜', '女', '高一 (1) 班', '文科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=LiNa', '13800138003', '建设路 88 号', '在校', 2023),
('stu004', '2023004', '刘强', '男', '高一 (2) 班', '理科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=LiuQiang', '13800138004', '和平大道 100 号', '在校', 2023),
('stu005', '2023005', '陈静', '女', '高一 (2) 班', '文科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=ChenJing', '13800138005', '中山路 200 号', '在校', 2023),
('stu006', '2023006', '杨洋', '男', '高一 (3) 班', '理科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=YangYang', '13800138006', '解放路 300 号', '在校', 2023),
('stu007', '2023007', '赵敏', '女', '高一 (3) 班', '文科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=ZhaoMin', '13800138007', '胜利路 400 号', '在校', 2023),
('stu008', '2023008', '周杰', '男', '高一 (1) 班', '理科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=ZhouJie', '13800138008', '友谊路 500 号', '在校', 2023),
('stu009', '2023009', '吴婷', '女', '高一 (2) 班', '文科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=WuTing', '13800138009', '文化路 600 号', '在校', 2023),
('stu010', '2023010', '郑浩', '男', '高一 (3) 班', '理科', 'https://api.dicebear.com/7.x/avataaars/svg?seed=ZhengHao', '13800138010', '体育路 700 号', '在校', 2023);

-- 插入示例通知数据
INSERT INTO notifications (user_id, title, content, type, priority, is_read, related_type, related_id) VALUES
('teacher001', '高一 (1) 班家长会通知', '定于本周五下午 2 点在学校会议室召开高一 (1) 班家长会，请各位家长准时参加。', '通知', 'high', 0, null, null),
('teacher001', '审批李娜的病假申请', '学生李娜因身体不适申请病假 3 天，请审批。', '待办', 'high', 0, 'student_leave', 'stu003'),
('teacher001', '录入期中考试成绩', '请各位老师在下周五之前完成期中考试成绩的录入工作。', '待办', 'normal', 1, null, null),
('teacher001', '学校春季运动会通知', '学校将于 4 月 15 日举行春季运动会，请各班做好准备工作。', '通知', 'normal', 1, null, null),
('teacher001', '学生体检安排', '下周一上午 8 点开始进行学生体检，请通知各班学生按时参加。', '通知', 'normal', 1, null, null);

-- 插入示例考勤数据
INSERT INTO attendance (student_id, date, status, remark) VALUES
('stu001', '2024-01-15', '出勤', null),
('stu002', '2024-01-15', '出勤', null),
('stu003', '2024-01-15', '出勤', null),
('stu004', '2024-01-15', '出勤', null),
('stu005', '2024-01-15', '出勤', null),
('stu006', '2024-01-15', '出勤', null),
('stu007', '2024-01-15', '出勤', null),
('stu008', '2024-01-15', '出勤', null),
('stu009', '2024-01-15', '出勤', null),
('stu010', '2024-01-15', '出勤', null);

-- 插入示例成绩数据
INSERT INTO scores (student_id, subject, exam_type, score, class_rank, term) VALUES
('stu001', '数学', '期中', 95.0, 5, '2023-2024-1'),
('stu001', '语文', '期中', 88.0, 12, '2023-2024-1'),
('stu001', '英语', '期中', 92.0, 8, '2023-2024-1'),
('stu002', '数学', '期中', 98.0, 2, '2023-2024-1'),
('stu002', '语文', '期中', 90.0, 8, '2023-2024-1'),
('stu002', '英语', '期中', 96.0, 3, '2023-2024-1');

-- 插入示例教师陪伴天数数据
INSERT INTO teacher_days (user_id, total_days, start_date) VALUES
('teacher001', 342, '2023-09-01');
