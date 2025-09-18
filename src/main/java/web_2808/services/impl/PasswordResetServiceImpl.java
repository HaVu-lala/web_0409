package web_2808.services.impl;

import web_2808.dao.IUserDao;
import web_2808.dao.PasswordResetDao;
import web_2808.dao.impl.PasswordResetDaoImpl;
import web_2808.dao.impl.UserDaoImpl;
import web_2808.models.UserModel;
import web_2808.services.PasswordResetService;

public class PasswordResetServiceImpl implements PasswordResetService {

	private final IUserDao userDao = new UserDaoImpl();
	private final PasswordResetDao tokenDao = new PasswordResetDaoImpl();

	@Override
	public String requestReset(String email) {
		if (email == null || email.trim().isEmpty())
			return null;

		// tìm user theo email (tận dụng exists + findAll, hoặc bổ sung hàm findByEmail
		// nếu bạn có)
		UserModel found = null;
		for (UserModel u : userDao.findAll()) {
			if (email.trim().equalsIgnoreCase(u.getEmail())) {
				found = u;
				break;
			}
		}
		if (found == null)
			return null;

		// tạo token sống 15 phút
		return tokenDao.createTokenFor(found, 15);
	}

	@Override
	public boolean resetPassword(String token, String newPassword) {
		if (token == null || token.isBlank() || newPassword == null || newPassword.isBlank())
			return false;

		Integer userId = tokenDao.findUserIdByValidToken(token);
		if (userId == null)
			return false;

		// cập nhật mật khẩu (plain text theo project hiện tại)
		// ở đây mình dùng lại UserDaoImpl.insert/update kiểu đơn giản
		boolean ok = updatePassword(userId, newPassword.trim());
		if (ok)
			tokenDao.deleteByToken(token);
		return ok;
	}

	// cập nhật mật khẩu đơn giản bằng SQL trực tiếp (không đụng interface cũ của
	// bạn)
	private boolean updatePassword(int userId, String newPassword) {
		String sql = "UPDATE users SET password = ? WHERE id = ?";
		try (var conn = new web_2808.configs.DBConnectMySQL().getDatabaseConnection();
				var ps = conn.prepareStatement(sql)) {
			ps.setString(1, newPassword);
			ps.setInt(2, userId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
