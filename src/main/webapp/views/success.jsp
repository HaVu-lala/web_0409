<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Đăng nhập thành công</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap Icons (tuỳ chọn) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    body { background: #f6f7fb; }
    .card { border: 0; border-radius: 1rem; box-shadow: 0 10px 25px rgba(0,0,0,0.08); }
    .check-badge {
      width: 72px; height: 72px; border-radius: 50%;
      display: grid; place-items: center; margin: 0 auto 1rem;
      background: #e9f9ee; color: #22c55e; font-size: 36px;
    }
  </style>
</head>
<body>
  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-md-7 col-lg-6">
        <div class="card p-4 p-md-5 text-center">
          
          <div class="check-badge">
            <i class="bi bi-check-lg"></i>
          </div>

          <h1 class="h3 mb-2">Đăng nhập thành công!</h1>

          <!-- Lấy username: ưu tiên request attribute 'username', nếu không có thì lấy từ session.account.userName -->
          <c:choose>
            <c:when test="${not empty username}">
              <p class="text-muted mb-4">Xin chào, <strong>${username}</strong> 🎉</p>
            </c:when>
            <c:when test="${not empty sessionScope.account}">
              <p class="text-muted mb-4">Xin chào, <strong>${sessionScope.account.userName}</strong> 🎉</p>
            </c:when>
            <c:otherwise>
              <p class="text-muted mb-4">Xin chào! 🎉</p>
            </c:otherwise>
          </c:choose>

          <!-- Hiển thị vai trò nếu có -->
          <c:if test="${not empty sessionScope.account}">
            <div class="alert alert-light border d-flex align-items-center justify-content-center gap-2 mb-4" role="alert">
              <i class="bi bi-person-badge"></i>
              <span>Quyền:
                <strong>
                  <c:choose>
                    <c:when test="${sessionScope.account.roleid == 1}">USER</c:when>
                    <c:when test="${sessionScope.account.roleid == 2}">ADMIN</c:when>
                    <c:when test="${sessionScope.account.roleid == 3}">MANAGER</c:when>
                    <c:when test="${sessionScope.account.roleid == 4}">SELLER</c:when>
                    <c:when test="${sessionScope.account.roleid == 5}">SHIPPER</c:when>
                    <c:otherwise>UNKNOWN</c:otherwise>
                  </c:choose>
                </strong>
              </span>
            </div>
          </c:if>

          <!-- Actions -->
          <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4">
              <i class="bi bi-house-door"></i> Về trang chủ
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-secondary px-4">
              <i class="bi bi-box-arrow-right"></i> Đăng xuất
            </a>
          </div>

          <!-- Gợi ý tiếp -->
          <div class="mt-4 small text-muted">
            Cần chuyển hướng tự động? Bạn có thể đặt <code>resp.sendRedirect(...)</code> trong Controller.
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
