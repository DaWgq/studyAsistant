"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_api = require("../../utils/api.js");
const utils_request = require("../../utils/request.js");
const _sfc_main = {
  data() {
    return {
      username: "",
      password: "",
      loading: false
    };
  },
  onLoad() {
    const token = common_vendor.index.getStorageSync("token");
    if (token) {
      common_vendor.index.switchTab({
        url: "/pages/home/home"
      });
    }
  },
  methods: {
    async handleLogin() {
      if (!this.username.trim()) {
        common_vendor.index.showToast({ title: "请输入用户名", icon: "none" });
        return;
      }
      if (!this.password.trim()) {
        common_vendor.index.showToast({ title: "请输入密码", icon: "none" });
        return;
      }
      this.loading = true;
      try {
        const res = await utils_api.login(this.username, this.password);
        utils_request.request.setToken(res.token);
        common_vendor.index.showToast({
          title: "登录成功",
          icon: "success"
        });
        setTimeout(() => {
          common_vendor.index.switchTab({
            url: "/pages/home/home"
          });
        }, 1500);
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/login/login.vue:102", "登录失败:", err);
      } finally {
        this.loading = false;
      }
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: $data.username,
    b: common_vendor.o(($event) => $data.username = $event.detail.value, "a7"),
    c: $data.password,
    d: common_vendor.o(($event) => $data.password = $event.detail.value, "eb"),
    e: $data.loading,
    f: common_vendor.o((...args) => $options.handleLogin && $options.handleLogin(...args), "5a")
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-e4e4508d"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/login/login.js.map
