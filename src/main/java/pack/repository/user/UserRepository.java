package pack.repository.user;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import pack.dto.user.UserDTO;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sql;

    public void userRegister(UserDTO userDTO) {
        sql.insert("User.userRegister", userDTO);
    }
}
