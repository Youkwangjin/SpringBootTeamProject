package pack.dao.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import pack.dto.user.UserDTO;
import pack.repository.user.UserMapper;

@Repository
@AllArgsConstructor
public class UserDAO {

	private final UserMapper userMapper;
	

    private boolean isEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean joinUserData(UserDTO userDto) {
        boolean b;
        b = isEmpty(userDto.getUser_id()) &&
                isEmpty(userDto.getUser_pwd()) &&
                isEmpty(userDto.getUser_name()) &&
                isEmpty(userDto.getUser_tel()) &&
                isEmpty(userDto.getUser_email()) &&
                isEmpty(userDto.getUser_addr()) &&
                userDto.getUser_id().matches("^[a-zA-Z\\d]{4,}$") &&
                userDto.getUser_tel().matches("^[0-9-]+$") &&
                userDto.getUser_jumin().matches("^\\d{6}-\\d{7}$") &&
                userDto.getUser_pwd().equals(userDto.getUser_repwd()) &&
                userDto.getUser_name().matches("^[가-힣]{2,}$") &&
                userDto.getUser_pwd().matches("^.{4,}$") &&
                userDto.getUser_email().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        return b;
    }

    public boolean userInsertData(UserDTO userDto) {
		boolean b = false;
		try {
			if (joinUserData(userDto)) {
				int re = userMapper.userInsertData(userDto);
				if (re > 0) {
					b = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
    }

    public UserDTO userLoginProcess(String user_id, String user_pwd) {
        return userMapper.userLoginProcess(user_id, user_pwd);
    }

    public boolean userDataUpdate(UserDTO userDto) {
    	boolean b = false;
		int re = userMapper.userUpdate(userDto);
		if(re > 0) b = true;
		return b;  	
    }

    public boolean userDataDelete(UserDTO userDto) {
        boolean b = false;
        int re = userMapper.userDelete(userDto);
        if (re > 0) {
            b = true;
        }
        return b;
    }

    public int userIdCheck(String user_id) {
        return userMapper.userIdCheck(user_id);
    }

    public UserDTO userIdFind(String user_name, String user_email, String user_jumin) {
    	return userMapper.userIdFind(user_name, user_email, user_jumin);
    }
}
