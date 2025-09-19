<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản lý danh mục sách</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container py-4">

		<div class="d-flex align-items-center justify-content-between mb-3">
			<h3 class="mb-0">Danh mục sách</h3>
			<form class="d-flex" method="get"
				action="${pageContext.request.contextPath}/admin/categories">
				<input class="form-control me-2" type="search" name="q"
					placeholder="Tìm theo tên..." value="${param.q}">
				<button class="btn btn-outline-primary" type="submit">Tìm</button>
			</form>
		</div>

		<div class="row g-4">
			<!-- Form thêm/sửa -->
			<div class="col-md-4">
				<div class="card">
					<div class="card-header">
						<c:choose>
							<c:when test="${not empty editing}">Sửa danh mục</c:when>
							<c:otherwise>Thêm danh mục</c:otherwise>
						</c:choose>
					</div>
					<div class="card-body">
						<form method="post"
							action="${pageContext.request.contextPath}/admin/categories">
							<c:choose>
								<c:when test="${not empty editing}">
									<input type="hidden" name="action" value="update" />
									<input type="hidden" name="cateid" value="${editing.cateid}" />
								</c:when>
								<c:otherwise>
									<input type="hidden" name="action" value="create" />
								</c:otherwise>
							</c:choose>

							<div class="mb-3">
								<label class="form-label">Tên danh mục</label> <input
									type="text" name="catename" class="form-control" required
									value="${editing.catename}">
							</div>

							<div class="mb-3">
								<label class="form-label">Icon (tùy chọn)</label> <input
									type="text" name="icon" class="form-control"
									placeholder="vd: bi-book" value="${editing.icon}">
							</div>

							<button class="btn btn-primary w-100" type="submit">
								<c:choose>
									<c:when test="${not empty editing}">Cập nhật</c:when>
									<c:otherwise>Thêm mới</c:otherwise>
								</c:choose>
							</button>
						</form>
					</div>
				</div>
			</div>

			<!-- Bảng danh mục -->
			<div class="col-md-8">
				<div class="card">
					<div class="card-body p-0">
						<table class="table table-striped mb-0 align-middle">
							<thead class="table-light">
								<tr>
									<th style="width: 80px;">ID</th>
									<th>Tên danh mục</th>
									<th>Icon</th>
									<th style="width: 160px;">Hành động</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="c" items="${list}">
									<tr>
										<td>${c.cateid}</td>
										<td>${c.catename}</td>
										<td><c:choose>
												<c:when test="${not empty c.icon}">
													<!-- c.icon phải nhập dạng: bi-book, bi-journal-bookmark, ... -->
													<i class="bi ${c.icon}"></i>
													<span class="text-muted ms-1"><code>${c.icon}</code></span>
												</c:when>
												<c:otherwise>
													<span class="text-muted">—</span>
												</c:otherwise>
											</c:choose></td>
										<td><a class="btn btn-sm btn-outline-secondary"
											href="${pageContext.request.contextPath}/admin/categories?edit=${c.cateid}">
												Sửa </a>
											<form method="post"
												action="${pageContext.request.contextPath}/admin/categories"
												class="d-inline"
												onsubmit="return confirm('Xoá danh mục này?');">
												<input type="hidden" name="action" value="delete" /> <input
													type="hidden" name="cateid" value="${c.cateid}" />
												<button type="submit" class="btn btn-sm btn-outline-danger">Xoá</button>
											</form></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
