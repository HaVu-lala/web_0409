package web_2808.services.impl;

import java.util.List;

import web_2808.dao.CategoryDao;
import web_2808.dao.impl.CategoryDaoImpl;
import web_2808.models.Category;
import web_2808.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao dao = new CategoryDaoImpl();

    @Override public void insert(Category c) { if (c != null) dao.insert(c); }
    @Override public void edit(Category c)   { if (c != null) dao.edit(c); }
    @Override public void delete(int id)     { dao.delete(id); }
    @Override public Category get(int id)    { return dao.get(id); }
    @Override public Category get(String n)  { return dao.get(n); }
    @Override public List<Category> getAll() { return dao.getAll(); }
    @Override public List<Category> search(String k) { return dao.search(k); }
}
