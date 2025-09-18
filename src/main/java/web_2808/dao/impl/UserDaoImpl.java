package web_2808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import web_2808.configs.DBConnectMySQL;
import web_2808.dao.IUserDao;
import web_2808.models.UserModel;

public class UserDaoImpl extends DBConnectMySQL implements IUserDao {

    @Override
    public List<UserModel> findAll() {
        String sql = "SELECT * FROM users";
        List<UserModel> list = new ArrayList<>();
        try (Connection conn = super.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // trả list rỗng nếu lỗi
    }

    @Override
    public UserModel findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = super.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // LOGIN
    @Override
    public UserModel findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = super.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // REGISTER - CHECKS
    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
        try (Connection conn = super.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = super.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByPhone(String phone) {
        String sql = "SELECT 1 FROM users WHERE phone = ? LIMIT 1";
        try (Connection conn = super.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // REGISTER - INSERT
    @Override
    public boolean insert(UserModel user) {
        // Nếu DB đã DEFAULT roleid=1, bạn có thể bỏ roleid khỏi câu SQL.
        String sql = "INSERT INTO users (email, username, fullname, password, avatar, roleid, phone) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = super.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPassWord());

            // avatar
            if (user.getAvatar() == null || user.getAvatar().isBlank()) {
                ps.setNull(5, java.sql.Types.VARCHAR);
            } else {
                ps.setString(5, user.getAvatar());
            }

            // roleid: đảm bảo 1..5 (USER=1) nếu có FK
            int role = user.getRoleid() <= 0 ? 1 : user.getRoleid();
            ps.setInt(6, role);

            // phone
            if (user.getPhone() == null || user.getPhone().isBlank()) {
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else {
                ps.setString(7, user.getPhone());
            }

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (java.sql.SQLException e) {
            // Bắt bệnh nhanh:
            // 1062 = duplicate key (trùng email/username)
            // 1452 = cannot add or update child row (FK roleid sai)
            // 1364 = field doesn't have a default value (NOT NULL thiếu DEFAULT)
            // 1054 = unknown column (sai tên cột)
            System.err.println("[DAO][INSERT FAIL] sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper: map ResultSet -> UserModel (đồng bộ field & tên cột)
    private UserModel mapRow(ResultSet rs) throws Exception {
        return new UserModel(
            rs.getInt("id"),
            rs.getString("email"),
            rs.getString("username"),
            rs.getString("fullname"),
            rs.getString("password"),
            rs.getString("avatar"),
            rs.getInt("roleid"),
            rs.getString("phone"),
            rs.getDate("createddate") // java.sql.Date
        );
    }
}
