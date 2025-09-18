package web_2808.dao;

import web_2808.models.UserModel;

public interface PasswordResetDao {
	String createTokenFor(UserModel user, long ttlMinutes);

	Integer findUserIdByValidToken(String token);

	void deleteByToken(String token);

	void deleteAllForUser(int userId);
}
