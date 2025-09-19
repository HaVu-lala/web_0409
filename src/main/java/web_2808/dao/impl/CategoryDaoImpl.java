package web_2808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import web_2808.configs.DBConnectMySQL;
import web_2808.dao.CategoryDao;
import web_2808.models.Category;

public class CategoryDaoImpl extends DBConnectMySQL implements CategoryDao {

    // Bảng & cột: dùng taxonomy site-wide: categories(cateid, catename, icon)
    private static final String TBL = "categories";

    @Override
    public void insert(Category category) {
        String sql = "INSERT INTO " + TBL + " (catename, icon) VALUES (?, ?)";
        try (Connection con = super.getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCatename());
            ps.setString(2, category.getIcon());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Category category) {
        String sql = "UPDATE " + TBL + " SET catename = ?, icon = ? WHERE cateid = ?";
        try (Connection con = super.getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCatename());
            ps.setString(2, category.getIcon());
            ps.setInt(3, category.getCateid());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM " + TBL + " WHERE cateid = ?";
        try (Connection con = super.getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category get(int id) {
        String sql = "SELECT cateid, catename, icon FROM " + TBL + " WHERE cateid = ? LIMIT 1";
        try (Connection con = super.getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category get(String name) {
        String sql = "SELECT cateid, catename, icon FROM " + TBL + " WHERE catename = ? LIMIT 1";
        try (Connection con = super.getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT cateid, catename, icon FROM " + TBL + " ORDER BY catename";
        try (Connection con = super.getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Category> search(String keyword) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT cateid, catename, icon FROM " + TBL + " WHERE catename LIKE ? ORDER BY catename";
        try (Connection con = super.getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (keyword == null ? "" : keyword.trim()) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Map 1 row -> Category model (đúng trường cateid/catename/icon)
    private Category map(ResultSet rs) throws Exception {
        Category c = new Category();
        c.setCateid(rs.getInt("cateid"));
        c.setCatename(rs.getString("catename"));
        c.setIcon(rs.getString("icon"));
        return c;
    }
}
