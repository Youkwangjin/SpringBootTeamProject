package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.request.AdminUserDeleteReqDTO;
import com.acorn.api.dto.admin.request.AdminUserUpdateReqDTO;
import com.acorn.api.dto.admin.response.AdminUserListResDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.user.response.UserResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminUserService {

    List<AdminUserListResDTO> getUserList(CommonListReqDTO listData);

    UserResDTO getUserData(Integer userId);

    void adminUpdateUser(AdminUserUpdateReqDTO requestData);

    void adminDeleteUser(AdminUserDeleteReqDTO requestData);
}