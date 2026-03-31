"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_api = require("../../utils/api.js");
const _sfc_main = {
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
      welcomeText: ""
    };
  },
  onLoad() {
    this.loadHomeData();
  },
  onShow() {
    this.loadHomeData();
  },
  methods: {
    async loadHomeData() {
      this.loadStats();
      this.loadNotifications();
      this.loadQuickActions();
    },
    async loadStats() {
      try {
        const res = await utils_api.getHomeStats();
        this.stats = {
          totalStudents: res.total_students || 0,
          attendanceRate: res.today_attendance_rate || 0,
          absentCount: res.today_absent_count || 0,
          teacherDays: res.teacher_days || 0
        };
        this.welcomeText = `今天是你陪伴学生的第 ${this.stats.teacherDays} 天`;
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/home/home.vue:119", "加载统计数据失败:", err);
      }
    },
    async loadNotifications() {
      try {
        const res = await utils_api.getNotifications(1, 10);
        this.notifications = res.list || [];
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/home/home.vue:127", "加载通知失败:", err);
      }
    },
    async loadQuickActions() {
      try {
        const res = await utils_api.getQuickActions();
        this.quickActions = res.map((item) => ({
          ...item,
          bg: this.getActionBg(item.id)
        }));
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/home/home.vue:138", "加载快捷操作失败:", err);
        this.quickActions = [
          { id: 1, icon: "➕", label: "录入学生", bg: "bg-blue-50" },
          { id: 2, icon: "📅", label: "考勤打卡", bg: "bg-orange-50" },
          { id: 3, icon: "📖", label: "成绩管理", bg: "bg-purple-50" },
          { id: 4, icon: "🎓", label: "综合评价", bg: "bg-green-50" }
        ];
      }
    },
    getActionBg(id) {
      const bgMap = {
        1: "bg-blue-50",
        2: "bg-orange-50",
        3: "bg-purple-50",
        4: "bg-green-50"
      };
      return bgMap[id] || "bg-blue-50";
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.t($data.welcomeText || "今天是你陪伴学生的第 342 天"),
    b: common_vendor.t($data.stats.totalStudents.toLocaleString() || "1,248"),
    c: common_vendor.t($data.stats.attendanceRate),
    d: common_vendor.t($data.stats.absentCount),
    e: common_vendor.f($data.quickActions, (item, index, i0) => {
      return {
        a: common_vendor.t(item.icon),
        b: common_vendor.n(item.bg),
        c: common_vendor.t(item.label),
        d: index
      };
    }),
    f: common_vendor.f($data.notifications, (item, index, i0) => {
      return {
        a: common_vendor.n(item.type === "待办" ? "dot-orange" : "dot-blue"),
        b: common_vendor.t(item.title),
        c: common_vendor.t(item.time),
        d: index
      };
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-07e72d3c"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/home/home.js.map
