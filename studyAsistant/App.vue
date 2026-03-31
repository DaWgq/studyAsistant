<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
			// 检查登录状态
			const token = uni.getStorageSync('token')
			const pages = getCurrentPages()
			const currentPage = pages[pages.length - 1]
			const currentPath = currentPage ? currentPage.route : ''
			
			// 如果已登录且不在登录页，无需处理
			// 如果未登录且在非登录页，跳转到登录页
			if (!token && currentPath && currentPath !== 'pages/login/login') {
				uni.reLaunch({
					url: '/pages/login/login'
				})
			}
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
/* 公共样式 */
page {
	background-color: #f9fafb;
	font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', sans-serif;
	font-size: 14px;
	line-height: 1.5;
	color: #1f2937;
}

/* 通用工具类 */
.container {
	padding: 16px;
}

.card {
	background-color: #ffffff;
	border-radius: 16px;
	padding: 20px;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
	border: 1px solid #f3f4f6;
}

.gradient-blue {
	background: linear-gradient(135deg, #3b82f6, #2563eb);
}

.gradient-emerald {
	background: linear-gradient(135deg, #10b981, #059669);
}
</style>
