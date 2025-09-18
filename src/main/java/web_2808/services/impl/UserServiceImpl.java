package web_2808.services.impl;

import web_2808.dao.IUserDao;
import web_2808.dao.impl.UserDaoImpl;
import web_2808.models.UserModel;
import web_2808.services.UserService;

public class UserServiceImpl implements UserService {

    private final IUserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    // ===== LOGIN =====
    @Override
    public UserModel login(String username, String password) {
        if (username == null || password == null) return null;
        return userDao.findByUsernameAndPassword(username.trim(), password.trim());
    }

    // ===== REGISTER (trả về null nếu OK, còn lại là thông báo lỗi) =====
    @Override
    public String register(String email, String password, String username,
                           String fullname, String phone) {

        // sanitize
        username = safe(username);
        email    = safe(email);
        password = safe(password);
        fullname = safe(fullname);
        phone    = safe(phone);

        // validate cơ bản
        if (isBlank(username) || isBlank(email) || isBlank(password)) {
            return "Vui lòng nhập đầy đủ Username, Email và Password!";
        }

        // kiểm tra trùng
        if (userDao.existsByUsername(username)) return "Tài khoản đã tồn tại!";
        if (userDao.existsByEmail(email))       return "Email đã tồn tại!";
        if (!isBlank(phone) && userDao.existsByPhone(phone)) return "Số điện thoại đã tồn tại!";

        // tạo model để insert
        UserModel u = new UserModel();
        u.setUserName(username);
        u.setEmail(email);
        u.setPassWord(password);   // TODO: sau này dùng BCrypt
        u.setFullName(isBlank(fullname) ? null : fullname);
        u.setPhone(isBlank(phone) ? null : phone);
        u.setAvatar(null);         // nếu có input avatar thì set vào
        u.setRoleid(1);            // MẶC ĐỊNH USER = 1 (khớp bảng roles)
        // KHÔNG set createdDate ở đây, để DB tự DEFAULT CURRENT_TIMESTAMP

        boolean ok = userDao.insert(u);
        return ok ? null : "Đăng ký thất bại! Vui lòng thử lại.";
    }

    // ===== CHECKS =====
    @Override
    public boolean checkExistEmail(String email) {
        return !isBlank(email) && userDao.existsByEmail(email.trim());
    }

    @Override
    public boolean checkExistUsername(String username) {
        return !isBlank(username) && userDao.existsByUsername(username.trim());
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return !isBlank(phone) && userDao.existsByPhone(phone.trim());
    }

    // ===== INSERT thô (nếu interface có) =====
    @Override
    public void insert(UserModel user) {
        if (user != null) userDao.insert(user);
    }

    // ===== helpers =====
    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private String safe(String s) { return s == null ? null : s.trim(); }
}
