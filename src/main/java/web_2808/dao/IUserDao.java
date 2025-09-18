package web_2808.dao;

import web_2808.models.UserModel;
import java.util.List;

public interface IUserDao {
    List<UserModel> findAll();
    UserModel findById(int id);

    // login
    UserModel findByUsernameAndPassword(String username, String password);

    // register + checks
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean insert(UserModel user);
}
