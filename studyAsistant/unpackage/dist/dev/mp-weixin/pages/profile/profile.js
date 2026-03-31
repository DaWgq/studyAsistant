"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_api = require("../../utils/api.js");
const utils_request = require("../../utils/request.js");
const _sfc_main = {
  data() {
    return {
      userProfile: null,
      notificationsEnabled: true
    };
  },
  onLoad() {
    this.loadProfile();
    this.loadNotificationSettings();
  },
  methods: {
    async loadProfile() {
      try {
        const res = await utils_api.getProfile();
        this.userProfile = res;
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/profile/profile.vue:68", "加载个人信息失败:", err);
      }
    },
    async loadNotificationSettings() {
      try {
        const res = await utils_api.getNotificationSettings();
        this.notificationsEnabled = res.notifications_enabled !== void 0 ? res.notifications_enabled : true;
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/profile/profile.vue:76", "加载通知设置失败:", err);
      }
    },
    async toggleNotifications() {
      this.notificationsEnabled = !this.notificationsEnabled;
      try {
        await utils_api.updateNotificationSettings({
          notifications_enabled: this.notificationsEnabled
        });
      } catch (err) {
        common_vendor.index.__f__("error", "at pages/profile/profile.vue:86", "更新通知设置失败:", err);
        this.notificationsEnabled = !this.notificationsEnabled;
      }
    },
    onProfileSettings() {
      common_vendor.index.showToast({
        title: "个人资料设置",
        icon: "none"
      });
    },
    onNotificationSettings() {
      common_vendor.index.showToast({
        title: "消息通知设置",
        icon: "none"
      });
    },
    onSystemSettings() {
      common_vendor.index.showToast({
        title: "系统设置",
        icon: "none"
      });
    },
    async onLogout() {
      common_vendor.index.showModal({
        title: "确认退出",
        content: "确定要退出登录吗？",
        success: async (res) => {
          if (res.confirm) {
            try {
              await utils_api.logout();
            } catch (err) {
              common_vendor.index.__f__("error", "at pages/profile/profile.vue:118", "登出失败:", err);
            } finally {
              utils_request.request.removeToken();
              common_vendor.index.reLaunch({
                url: "/pages/login/login"
              });
            }
          }
        }
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o((...args) => $options.onProfileSettings && $options.onProfileSettings(...args), "4e"),
    b: common_vendor.n($data.notificationsEnabled ? "handle-on" : "handle-off"),
    c: common_vendor.n($data.notificationsEnabled ? "switch-on" : "switch-off"),
    d: common_vendor.o((...args) => $options.toggleNotifications && $options.toggleNotifications(...args), "a1"),
    e: common_vendor.o((...args) => $options.onNotificationSettings && $options.onNotificationSettings(...args), "9f"),
    f: common_vendor.o((...args) => $options.onSystemSettings && $options.onSystemSettings(...args), "c9"),
    g: common_vendor.o((...args) => $options.onLogout && $options.onLogout(...args), "2b")
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-dd383ca2"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/profile/profile.js.map
