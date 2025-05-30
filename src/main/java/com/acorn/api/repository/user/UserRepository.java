package com.acorn.api.repository.user;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRepository {

    Integer selectUserIdKey();

    Boolean isEmailDuplicate(@Param("userEmail") String userEmail);

    Boolean isTelDuplicate(@Param("userTel") String userTel);

    void userRegister(User user);

    User findByUserEmail(@Param("userEmail") String userEmail);

    User selectAllUserData(@Param("userId") Integer userId);

    void userUpdate(User User);

    void adminUserUpdate(User user);

    void userDelete(User user);

    Integer selectAdminUserListCountByRequest(PaginationRequest pagination);

    List<User> selectAdminUserListData(PaginationRequest pagination);
}