package pack.dao.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pack.dto.user.UserDTO;
import pack.mapper.user.UserMapper;

@Repository
@AllArgsConstructor
public class UserDAO {

    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public boolean userInsertData(UserDTO userDto) {
        try {
            int userInsertDataRe = userMapper.userInsertData(userDto);
            return userInsertDataRe > 0;
        } catch (Exception e) {
            logger.error("유저 회원 가입 에러", e);
            return false;
        }
    }

    public UserDTO userLoginProcess(String user_id, String user_pwd) {
        return userMapper.userLoginProcess(user_id, user_pwd);
    }

    public boolean userDataUpdate(UserDTO userDto) {
        try {
            int userDataUpdateRe = userMapper.userUpdate(userDto);
            if (userDataUpdateRe > 0) {
                logger.info("사용자 회원수정 성공! user_id: {}", userDto.getUser_id());
                return true;
            } else {
                logger.info("사용자 데이터 없음! user_id: {}", userDto.getUser_id());
                return false;
            }
        } catch (Exception e) {
            logger.error("사용자 회원수정 중 오류 발생! user_id: {}", userDto.getUser_id(), e);
            return false;
        }
    }

    public boolean userDataDelete(UserDTO userDto) {
        try {
            int userDataDeleteRe = userMapper.userDelete(userDto);
            if (userDataDeleteRe > 0) {
                logger.info("사용자 회원탈퇴 성공! user_id: {}", userDto.getUser_id());
                return true;
            } else {
                logger.info("사용자 데이터 없음! user_id: {}", userDto.getUser_id());
                return false;
            }
        } catch (Exception e) {
            logger.error("사용자 회원탈퇴 중 오류 발생! user_id: {}", userDto.getUser_id(), e);
            return false;
        }
    }

    public int userIdCheck(String user_id) {
        try {
            return userMapper.userIdCheck(user_id);
        } catch (Exception e) {
            return 0;
        }
    }

    public UserDTO userIdFind(String user_name, String user_email, String user_jumin) {
        try {
            return userMapper.userIdFind(user_name, user_email, user_jumin);
        } catch (Exception e) {
            return null;
        }
    }
}
