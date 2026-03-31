// 模拟学生数据
export const MOCK_STUDENTS = [
  { id: '2023001', name: '张伟', grade: '高一 (1) 班', major: '理科', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=ZhangWei', phone: '13800138001', status: '在校' },
  { id: '2023002', name: '王芳', grade: '高一 (1) 班', major: '理科', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=WangFang', phone: '13800138002', status: '在校' },
  { id: '2023003', name: '李娜', grade: '高一 (2) 班', major: '文科', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=LiNa', phone: '13800138003', status: '请假' },
  { id: '2023004', name: '赵强', grade: '高二 (3) 班', major: '理科', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=ZhaoQiang', phone: '13800138004', status: '在校' },
  { id: '2023005', name: '陈杰', grade: '高二 (4) 班', major: '文科', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=ChenJie', phone: '13800138005', status: '在校' },
  { id: '2023006', name: '杨婷', grade: '高三 (1) 班', major: '理科', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=YangTing', phone: '13800138006', status: '在校' },
];

// 模拟通知数据
export const MOCK_NOTIFICATIONS = [
  { id: 1, title: '高一 (1) 班家长会通知', time: '10:00 AM', type: '通知' },
  { id: 2, title: '审批李娜的病假申请', time: '昨天', type: '待办' },
  { id: 3, title: '录入期中考试成绩', time: '3 天前', type: '待办' },
];
