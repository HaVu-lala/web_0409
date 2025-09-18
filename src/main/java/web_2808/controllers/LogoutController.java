package web_2808.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_2808.constant.Constant;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // huỷ session
        if (req.getSession(false) != null) {
            req.getSession(false).invalidate();
        }

        // xoá cookie remember (nếu có dùng)
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (Constant.COOKIE_REMEMBER.equals(c.getName())) {
                    c.setValue("");
                    c.setMaxAge(0);
                    c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                    resp.addCookie(c);
                }
            }
        }

        // về trang login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
