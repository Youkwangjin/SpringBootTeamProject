package pack.repository.user;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import pack.dto.user.UserDTO;

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

    public void userRegister(UserDTO userDTO) {
        sql.insert("User.userRegister", userDTO);
    }

    public UserDTO findByUserEmail(String username) {
        return sql.selectOne("User.findByUserEmail", username);
    }
}
