package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.AdminUserListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminUserService {

    List<AdminUserListDTO> getUserList(AdminUserListDTO listData);
}
