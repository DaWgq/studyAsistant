"use strict";
const utils_request = require("./request.js");
function login(username, password) {
  return utils_request.request.post("/auth/login", { username, password });
}
function logout() {
  return utils_request.request.post("/auth/logout");
}
function getHomeStats() {
  return utils_request.request.get("/home/stats");
}
function getQuickActions() {
  return utils_request.request.get("/home/quick-actions");
}
function getNotifications(page = 1, pageSize = 10) {
  return utils_request.request.get("/home/notifications", { page, page_size: pageSize });
}
function getStudents(page = 1, pageSize = 20, grade = "", status = "", keyword = "") {
  return utils_request.request.get("/students", { page, page_size: pageSize, grade, status, keyword });
}
function getStudentDetail(id) {
  return utils_request.request.get(`/students/${id}`);
}
function getProfile() {
  return utils_request.request.get("/profile");
}
function getNotificationSettings() {
  return utils_request.request.get("/profile/notification-settings");
}
function updateNotificationSettings(settings) {
  return utils_request.request.put("/profile/notification-settings", settings);
}
exports.getHomeStats = getHomeStats;
exports.getNotificationSettings = getNotificationSettings;
exports.getNotifications = getNotifications;
exports.getProfile = getProfile;
exports.getQuickActions = getQuickActions;
exports.getStudentDetail = getStudentDetail;
exports.getStudents = getStudents;
exports.login = login;
exports.logout = logout;
exports.updateNotificationSettings = updateNotificationSettings;
//# sourceMappingURL=../../.sourcemap/mp-weixin/utils/api.js.map
