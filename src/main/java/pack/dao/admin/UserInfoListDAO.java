package pack.dao.admin;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pack.dto.form.FormDTO;
import pack.mapper.admin.UserInfoListMapper;
import pack.dto.user.UserDTO;

@Repository
@AllArgsConstructor
public class UserInfoListDAO {

    private final UserInfoListMapper userInfoListMapper;

    public List<UserDTO> getUserList() {
        return userInfoListMapper.selectUser();
    }

    public List<UserDTO> getUserSearch(FormDTO formDTO) {
        return userInfoListMapper.selectUserSearch(formDTO);
    }

    public int totalUser() {
        return userInfoListMapper.totalUser();
    }

    public boolean userDelete(String user_id) {
        boolean b = false;
        int re = userInfoListMapper.userDeleteData(user_id);
        if (re > 0)
            b = true;
        return b;
    }

    public int userCount() {
        return userInfoListMapper.userCount();
    }
}