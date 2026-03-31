"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_api = require("../../utils/api.js");
const _sfc_main = {
  data() {
    return {
      students: [],
      searchQuery: "",
      page: 1,
      pageSize: 20,
      total: 0,
      loading: false
    };
  },
  computed: {
    filteredStudents() {
      if (!this.searchQuery) {
        return this.students;
      }
      return this.students.filter(
        (student) => student.name.includes(this.searchQuery) || student.student_no.includes(this.searchQuery) || student.grade.includes(this.searchQuery)
      );
    }
  },
  onLoad() {
    this.loadStudents();
  },
  onPullDownRefresh() {
    this.page = 1;
    this.loadStudents().finally(() => {
      common_vendor.index.stopPullDownRefresh();
    });
  },
  onReachBottom() {
    if (this.students.length < this.total) {
      this.page++;
      this.loadStudents();
    }
  },
  methods: {
    async loadStudents() {
      this.loading = true;
      try {
        const res = await utils_api.getStudents(this.page, this.pageSize, "", "", this.searchQuery);
        const newList = res.list || [];
        if (this.page === 1) {
          this.students = newList;
        } else {
          this.students = [...this.students, ...newList];
        }
        this.total = res.total || 0;
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/list/list.vue:104", "加载学生列表失败:", err);
        if (this.page === 1) {
          this.students = [];
        }
      } finally {
        this.loading = false;
      }
    },
    onStudentTap(student) {
      common_vendor.index.navigateTo({
        url: `/pages/detail/detail?id=${student.id}`
      });
    },
    onSearch() {
      this.page = 1;
      this.loadStudents();
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $data.searchQuery,
    b: common_vendor.o(($event) => $data.searchQuery = $event.detail.value, "0c"),
    c: common_vendor.f($options.filteredStudents, (student, index, i0) => {
      return {
        a: student.avatar,
        b: common_vendor.t(student.name),
        c: common_vendor.t(student.status),
        d: common_vendor.n(student.status === "在校" ? "status-in" : "status-leave"),
        e: common_vendor.t(student.id),
        f: common_vendor.t(student.grade),
        g: index,
        h: common_vendor.o(($event) => $options.onStudentTap(student), index)
      };
    }),
    d: $options.filteredStudents.length === 0
  }, $options.filteredStudents.length === 0 ? {} : {});
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-98a9e0b2"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/list/list.js.map
