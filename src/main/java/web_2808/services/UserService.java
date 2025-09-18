package web_2808.services;

import web_2808.models.UserModel;

public interface UserService {

    // ===== LOGIN =====
    UserModel login(String username, String password);

    // ===== REGISTER =====
    /**
     * Đăng ký tài khoản mới.
     * @return null nếu đăng ký thành công; trả về thông báo lỗi nếu thất bại.
     */
    String register(String email, String password, String username,
                    String fullname, String phone);

    // ===== CHECK EXISTENCE =====
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    void insert(UserModel user);
}
