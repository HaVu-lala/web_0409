<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký tài khoản</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center vh-100">
  <div class="card shadow p-4" style="min-width: 400px;">
    <h2 class="text-center mb-4">Tạo tài khoản mới</h2>

    <c:if test="${alert != null}">
      <div class="alert alert-danger text-center">${alert}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
      <div class="mb-3">
        <label class="form-label">Tài khoản *</label>
        <input type="text" name="username" class="form-control" placeholder="Nhập tài khoản"
               required value="${param.username}">
      </div>

      <div class="mb-3">
        <label class="form-label">Mật khẩu *</label>
        <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Email *</label>
        <input type="email" name="email" class="form-control" placeholder="Nhập email"
               required value="${param.email}">
      </div>

      <div class="mb-3">
        <label class="form-label">Họ và tên</label>
        <input type="text" name="fullname" class="form-control" placeholder="Nhập họ và tên"
               value="${param.fullname}">
      </div>

      <div class="mb-3">
        <label class="form-label">Số điện thoại</label>
        <input type="tel" name="phone" class="form-control" placeholder="Nhập số điện thoại"
               value="${param.phone}">
      </div>

      <button type="submit" class="btn btn-success w-100">Đăng ký</button>
    </form>

    <hr>
    <div class="text-center">
      <span>Đã có tài khoản? </span>
      <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
