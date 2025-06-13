package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.AdminUserDeleteRequestDTO;
import com.acorn.api.dto.admin.AdminUserListDTO;
import com.acorn.api.dto.admin.AdminUserUpdateRequestDTO;
import com.acorn.api.dto.user.response.UserResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminUserService {

    List<AdminUserListDTO> getUserList(AdminUserListDTO listData);

    UserResDTO getUserData(Integer userId);

    void adminUpdateUser(AdminUserUpdateRequestDTO requestData);

    void adminDeleteUser(AdminUserDeleteRequestDTO requestData);
}