<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="bg-light">

	<div
		class="container d-flex justify-content-center align-items-center vh-100">
		<div class="card shadow p-4" style="min-width: 350px;">
			<h2 class="text-center mb-4">Đăng nhập</h2>

			<!-- Hiển thị thông báo lỗi nếu có -->
			<c:if test="${alert != null}">
				<div class="alert alert-danger text-center">${alert}</div>
			</c:if>

			<!-- Form đăng nhập -->
			<form action="login" method="post">
				<div class="mb-3">
					<label for="username" class="form-label">Tài khoản</label> <input
						type="text" id="username" name="username" class="form-control"
						placeholder="Nhập tài khoản" required>
				</div>

				<div class="mb-3">
					<label for="password" class="form-label">Mật khẩu</label> <input
						type="password" id="password" name="password" class="form-control"
						placeholder="Nhập mật khẩu" required>
				</div>

				<div class="text-center mt-3">
					<a href="${pageContext.request.contextPath}/forgot">Quên mật
						khẩu?</a>
				</div>

				<button type="submit" class="btn btn-primary w-100">Đăng
					nhập</button>
			</form>
		</div>
	</div>

	<!-- Bootstrap CSS & JS -->
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
		rel="stylesheet">
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>