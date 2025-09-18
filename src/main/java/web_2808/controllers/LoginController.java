package web_2808.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import web_2808.constant.Constant;
import web_2808.models.UserModel;
import web_2808.services.UserService;
import web_2808.services.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // trim để tránh "truong " ≠ "truong"
        username = (username == null) ? null : username.trim();
        password = (password == null) ? null : password.trim();

        System.out.println("[LOGIN] input u=" + username + ", p=" + password); // debug

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đủ tài khoản và mật khẩu!");
            req.getRequestDispatcher("/views/error.jsp").forward(req, resp);
            return;
        }

        UserModel user = userService.login(username, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("account", user);
            // Đồng bộ key session để RegisterController nhận ra đã đăng nhập
            session.setAttribute(Constant.SESSION_USERNAME, user.getUserName());

            req.setAttribute("username", user.getUserName());
            req.getRequestDispatcher("/views/success.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            req.getRequestDispatcher("/views/error.jsp").forward(req, resp);
        }
    }
}
