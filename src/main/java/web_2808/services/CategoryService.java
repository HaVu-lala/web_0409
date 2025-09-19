package web_2808.services;

import java.util.List;
import web_2808.models.Category;

public interface CategoryService {
    void insert(Category c);
    void edit(Category c);
    void delete(int id);
    Category get(int id);
    Category get(String name);
    List<Category> getAll();
    List<Category> search(String keyword);
}
