<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Yêu cầu đặt lại mật khẩu đã gửi</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container d-flex justify-content-center align-items-center vh-100">
  <div class="card shadow p-4" style="min-width: 420px;">
    <h2 class="text-center mb-3">Kiểm tra email của bạn</h2>
    <p class="text-muted">Nếu email tồn tại, chúng tôi đã tạo yêu cầu đặt lại mật khẩu.</p>

    <!-- DEMO: hiển thị link reset ngay tại đây thay cho gửi email -->
    <c:if test="${not empty resetLink}">
      <div class="alert alert-success">
        Link đặt lại mật khẩu (demo): <a href="${resetLink}">${resetLink}</a><br/>
        (Link sẽ hết hạn sau ~15 phút)
      </div>
    </c:if>

    <div class="text-center">
      <a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Về trang đăng nhập</a>
    </div>
  </div>
</div>
</body>
</html>
