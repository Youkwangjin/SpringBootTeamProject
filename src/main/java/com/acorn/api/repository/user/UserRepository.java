package com.acorn.api.repository.user;

import com.acorn.api.model.user.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

    public void userUpdate(User updateUser) {
        sql.update("User.userUpdate", updateUser);
    }

    public void userDelete(User user) {
        sql.delete("User.userDelete", user);
    }
}
