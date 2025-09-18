package web_2808.services;

public interface PasswordResetService {
    /**
     * Tạo token reset cho email nếu tồn tại. Trả về token (để demo hiển thị) hoặc null nếu không có email.
     */
    String requestReset(String email);

    /**
     * Đặt mật khẩu mới bằng token hợp lệ. Trả true nếu thành công.
     */
    boolean resetPassword(String token, String newPassword);
}