package web_2808.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import web_2808.models.Category;
import web_2808.services.CategoryService;
import web_2808.services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {"/admin/categories"})
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CategoryService service = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String q = req.getParameter("q");
        List<Category> list = (q == null || q.isBlank())
                ? service.getAll()
                : service.search(q);

        // nếu có ?edit=ID → load category để fill form edit
        String editId = req.getParameter("edit");
        if (editId != null && !editId.isBlank()) {
            try {
                int id = Integer.parseInt(editId);
                Category editing = service.get(id);
                req.setAttribute("editing", editing);
            } catch (NumberFormatException ignored) {}
        }

        req.setAttribute("list", list);
        req.getRequestDispatcher("/views/admin/category/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if ("create".equals(action)) {
            Category c = new Category();
            c.setCatename(req.getParameter("catename"));
            c.setIcon(req.getParameter("icon"));
            service.insert(c);
        } else if ("update".equals(action)) {
            try {
                Category c = new Category();
                c.setCateid(Integer.parseInt(req.getParameter("cateid")));
                c.setCatename(req.getParameter("catename"));
                c.setIcon(req.getParameter("icon"));
                service.edit(c);
            } catch (NumberFormatException ignored) {}
        } else if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("cateid"));
                service.delete(id);
            } catch (NumberFormatException ignored) {}
        }
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}
