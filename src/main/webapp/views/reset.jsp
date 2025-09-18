<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đặt lại mật khẩu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container d-flex justify-content-center align-items-center vh-100">
  <div class="card shadow p-4" style="min-width: 400px;">
    <h2 class="text-center mb-4">Đặt lại mật khẩu</h2>

    <c:if test="${not empty alert}">
      <div class="alert alert-danger text-center">${alert}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/reset">
      <input type="hidden" name="token" value="${token}"/>

      <div class="mb-3">
        <label class="form-label">Mật khẩu mới</label>
        <input type="password" name="password" class="form-control" required minlength="4">
      </div>

      <div class="mb-3">
        <label class="form-label">Xác nhận mật khẩu</label>
        <input type="password" name="confirm" class="form-control" required minlength="4">
      </div>

      <button type="submit" class="btn btn-success w-100">Đổi mật khẩu</button>
    </form>

    <hr>
    <div class="text-center">
      <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
    </div>
  </div>
</div>
</body>
</html>
