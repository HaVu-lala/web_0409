package web_2808.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_2808.services.PasswordResetService;
import web_2808.services.impl.PasswordResetServiceImpl;

@WebServlet(urlPatterns = {"/forgot"})
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final PasswordResetService service = (PasswordResetService) new PasswordResetServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String token = service.requestReset(email);

        if (token != null) {
            // DEMO: hiển thị link reset ngay trên trang (thay vì gửi email)
            req.setAttribute("resetLink",
                req.getContextPath() + "/reset?token=" + token);
            req.getRequestDispatcher("/views/forgot_success.jsp").forward(req, resp);
        } else {
            req.setAttribute("alert", "Email không tồn tại trong hệ thống!");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
        }
    }
}
