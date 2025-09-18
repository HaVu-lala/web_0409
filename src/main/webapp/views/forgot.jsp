<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Quên mật khẩu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container d-flex justify-content-center align-items-center vh-100">
  <div class="card shadow p-4" style="min-width: 400px;">
    <h2 class="text-center mb-4">Quên mật khẩu</h2>

    <c:if test="${not empty alert}">
      <div class="alert alert-danger text-center">${alert}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/forgot">
      <div class="mb-3">
        <label class="form-label">Email đã đăng ký</label>
        <input type="email" name="email" class="form-control" placeholder="Nhập email của bạn" required>
      </div>
      <button type="submit" class="btn btn-primary w-100">Gửi yêu cầu</button>
    </form>

    <hr>
    <div class="text-center">
      <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
    </div>
  </div>
</div>
</body>
</html>
