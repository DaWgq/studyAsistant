"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_api = require("../../utils/api.js");
const _sfc_main = {
  data() {
    return {
      student: null,
      baseInfo: [],
      studentId: ""
    };
  },
  onLoad(options) {
    this.studentId = options.id || "";
    if (this.studentId) {
      this.loadStudentDetail();
    }
  },
  methods: {
    async loadStudentDetail() {
      try {
        const res = await utils_api.getStudentDetail(this.studentId);
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
        };
        this.baseInfo = [
          { label: "所在班级", value: res.grade },
          { label: "文理分科", value: res.major || "未分科" },
          { label: "联系电话", value: res.phone || "暂无" },
          { label: "家庭住址", value: res.address || "暂无" },
          { label: "性别", value: res.gender || "未知" },
          { label: "入学年份", value: res.enrollment_year || "未知" }
        ];
        common_vendor.index.setNavigationBarTitle({
          title: `${res.name} - 学生档案`
        });
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/detail/detail.vue:107", "加载学生详情失败:", err);
        common_vendor.index.showToast({ title: "加载失败", icon: "none" });
      }
    },
    onCallPhone() {
      if (this.student && this.student.phone) {
        common_vendor.index.makePhoneCall({
          phoneNumber: this.student.phone
        });
      }
    },
    onSendMessage() {
      common_vendor.index.showToast({
        title: "消息功能开发中",
        icon: "none"
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $data.student
  }, $data.student ? {
    b: common_vendor.t($data.student.status),
    c: $data.student.avatar,
    d: common_vendor.t($data.student.name),
    e: common_vendor.t($data.student.id),
    f: common_vendor.o((...args) => $options.onCallPhone && $options.onCallPhone(...args), "f2"),
    g: common_vendor.o((...args) => $options.onSendMessage && $options.onSendMessage(...args), "c3"),
    h: common_vendor.f($data.baseInfo, (item, index, i0) => {
      return {
        a: common_vendor.t(item.label),
        b: common_vendor.t(item.value),
        c: index
      };
    })
  } : {});
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-eca06f3c"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/detail/detail.js.map
