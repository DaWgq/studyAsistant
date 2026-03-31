"use strict";
const common_vendor = require("../common/vendor.js");
const BASE_URL = "http://127.0.0.1:8080/api";
const setToken = (token) => {
  common_vendor.index.setStorageSync("token", token);
};
const getToken = () => {
  return common_vendor.index.getStorageSync("token") || "";
};
const removeToken = () => {
  common_vendor.index.removeStorageSync("token");
};
function request(options) {
  return new Promise((resolve, reject) => {
    const token = getToken();
    common_vendor.index.request({
      url: BASE_URL + options.url,
      method: options.method || "GET",
      data: options.data || {},
      header: {
        "Content-Type": "application/json",
        "Authorization": token ? `Bearer ${token}` : ""
      },
      success: (res) => {
        const response = res.data;
        if (response.code === 200) {
          resolve(response.data);
        } else if (response.code === 401) {
          removeToken();
          common_vendor.index.navigateTo({ url: "/pages/login/login" });
          reject(response);
        } else {
          common_vendor.index.showToast({ title: response.message || "请求失败", icon: "none" });
          reject(response);
        }
      },
      fail: (err) => {
        common_vendor.index.__f__("error", "at utils/request.js:60", "请求失败:", err);
        common_vendor.index.showToast({ title: "网络请求失败", icon: "none" });
        reject(err);
      }
    });
  });
}
const request$1 = {
  get: (url, data) => request({ url, method: "GET", data }),
  post: (url, data) => request({ url, method: "POST", data }),
  put: (url, data) => request({ url, method: "PUT", data }),
  delete: (url, data) => request({ url, method: "DELETE", data }),
  setToken,
  getToken,
  removeToken
};
exports.request = request$1;
//# sourceMappingURL=../../.sourcemap/mp-weixin/utils/request.js.map
