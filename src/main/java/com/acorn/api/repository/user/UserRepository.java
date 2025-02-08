package com.acorn.api.repository.user;

import com.acorn.api.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

    Integer selectUserIdKey();

    Integer isEmailDuplicate(@Param("userEmail") String userEmail);

    Integer isTelDuplicate(@Param("userTel") String userTel);

    void userRegister(User user);

    User findByUserEmail(@Param("userEmail") String userEmail);

    User selectAllUserData(@Param("userId") Integer userId);

    void userUpdate(User User);

    void userDelete(User user);
}