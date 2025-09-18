package web_2808.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_2808.services.PasswordResetService;
import web_2808.services.impl.PasswordResetServiceImpl;

@WebServlet(urlPatterns = {"/reset"})
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final PasswordResetService service = (PasswordResetService) new PasswordResetServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // chỉ hiển thị form nếu có token
        String token = req.getParameter("token");
        if (token == null || token.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/forgot");
            return;
        }
        req.setAttribute("token", token);
        req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String token = req.getParameter("token");
        String password = req.getParameter("password");
        String confirm  = req.getParameter("confirm");

        if (password == null || !password.equals(confirm)) {
            req.setAttribute("token", token);
            req.setAttribute("alert", "Mật khẩu xác nhận không khớp!");
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
            return;
        }

        boolean ok = service.resetPassword(token, password);
        if (ok) {
            // về trang login
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("token", token);
            req.setAttribute("alert", "Token không hợp lệ hoặc đã hết hạn!");
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
        }
    }
}