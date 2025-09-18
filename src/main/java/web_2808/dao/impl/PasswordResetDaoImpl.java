package web_2808.dao.impl;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import web_2808.configs.DBConnectMySQL;
import web_2808.dao.PasswordResetDao;
import web_2808.models.UserModel;

public class PasswordResetDaoImpl extends DBConnectMySQL implements PasswordResetDao {

	private static final SecureRandom RNG = new SecureRandom();

	private String randomHex(int bytes) {
		byte[] buf = new byte[bytes];
		RNG.nextBytes(buf);
		StringBuilder sb = new StringBuilder(bytes * 2);
		for (byte b : buf)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}

	@Override
	public String createTokenFor(UserModel user, long ttlMinutes) {
		String token = randomHex(32); // 64 hex chars
		Instant expires = Instant.now().plusSeconds(ttlMinutes * 60);

		String sqlDelete = "DELETE FROM password_reset_tokens WHERE user_id = ?";
		String sqlInsert = "INSERT INTO password_reset_tokens (user_id, token, expires_at) VALUES (?, ?, ?)";

		try (Connection conn = getDatabaseConnection()) {
			// xóa token cũ của user (nếu có)
			try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
				ps.setInt(1, user.getId());
				ps.executeUpdate();
			}
			// chèn token mới
			try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
				ps.setInt(1, user.getId());
				ps.setString(2, token);
				ps.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.systemDefault())
						.format(expires));
				ps.executeUpdate();
			}
			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer findUserIdByValidToken(String token) {
		String sql = "SELECT user_id FROM password_reset_tokens " + "WHERE token = ? AND expires_at > NOW() LIMIT 1";
		try (Connection conn = getDatabaseConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, token);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt("user_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteByToken(String token) {
		String sql = "DELETE FROM password_reset_tokens WHERE token = ?";
		try (Connection conn = getDatabaseConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, token);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAllForUser(int userId) {
		String sql = "DELETE FROM password_reset_tokens WHERE user_id = ?";
		try (Connection conn = getDatabaseConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
