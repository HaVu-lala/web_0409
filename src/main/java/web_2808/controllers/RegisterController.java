package web_2808.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import web_2808.services.UserService;
import web_2808.services.impl.UserServiceImpl;
import web_2808.constant.Constant;

@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // Nếu đã có session đăng nhập → chuyển sang /admin (hoặc /home tuỳ bạn)
        if (session != null && session.getAttribute(Constant.SESSION_USERNAME) != null) {
            resp.sendRedirect(req.getContextPath() + "/admin");
            return;
        }

        // Kiểm tra cookie "remember me" → tự đăng nhập
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant.COOKIE_REMEMBER.equals(cookie.getName())) {
                    session = req.getSession(true);
                    session.setAttribute(Constant.SESSION_USERNAME, cookie.getValue());
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    return;
                }
            }
        }

        // Hiển thị form đăng ký
        req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email    = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone    = req.getParameter("phone");
        //String avatar	= req.getParameter("avatar");

        // Validate & kiểm tra trùng
        if (userService.checkExistEmail(email)) {
            req.setAttribute("alert", "Email đã tồn tại!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        if (userService.checkExistUsername(username)) {
            req.setAttribute("alert", "Tài khoản đã tồn tại!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        if (phone != null && !phone.isBlank() && userService.checkExistPhone(phone)) {
            req.setAttribute("alert", "Số điện thoại đã tồn tại!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        // Gọi service đăng ký
        // LƯU Ý: thứ tự tham số: email, password, username, fullname, phone
        String err = userService.register(email, password, username, fullname, phone);

        if (err == null) {
            // Có thể gửi mail kích hoạt ở đây (nếu muốn)
            // Redirect về trang login
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("alert", err);
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
        }
    }
}
