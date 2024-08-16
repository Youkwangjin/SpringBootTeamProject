package pack.repository.user;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import pack.model.user.User;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sql;

    public boolean isEmailDuplicate(String userEmail) {
        int emailCount = sql.selectOne("User.emailDuplicates", userEmail);
        return emailCount > 0;
    }

    public boolean isTelDuplicate(String userTel) {
        int telCount = sql.selectOne("User.telDuplicates", userTel);
        return telCount > 0;
    }

    public void userRegister(User user) {
        sql.insert("User.userRegister", user);
    }

    public User findByUserEmail(String username) {
        return sql.selectOne("User.findByUserEmail", username);
    }

    public User selectAllUserData(String userUUId) {
        return sql.selectOne("User.selectAllUserData", userUUId);
    }
}
