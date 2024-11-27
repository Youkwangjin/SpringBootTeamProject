package com.acorn.api.repository.user;

import com.acorn.api.model.user.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sql;

    public int isEmailDuplicate(String userEmail) {
        return sql.selectOne("User.emailDuplicates", userEmail);
    }

    public int isTelDuplicate(String userTel) {
        return sql.selectOne("User.telDuplicates", userTel);
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
