package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.admin.AdminUserListDTO;
import com.acorn.api.dto.admin.UserManagementRequestDTO;
import com.acorn.api.dto.user.UserResponseDTO;
import com.acorn.api.entity.admin.Admin;
import com.acorn.api.entity.user.User;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.repository.user.UserRepository;
import com.acorn.api.service.admin.AdminUserService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    @Override
    public List<AdminUserListDTO> getUserList(AdminUserListDTO listData) {
        listData.setTotalCount(userRepository.selectUserListCountByRequest(listData));
        List<User> userListData = userRepository.selectAdminUserListData(listData);
        return userListData.stream()
                .map(userList -> {
                    final Integer rowNum = userList.getRowNum();
                    final Integer userId = userList.getUserId();
                    final String userEmail = userList.getUserEmail();
                    final String userNm = userList.getUserNm();
                    final String userTel = userList.getUserTel();
                    final LocalDateTime userCreated = userList.getUserCreated();

                    return AdminUserListDTO.builder()
                            .rowNum(rowNum)
                            .userId(userId)
                            .userEmail(userEmail)
                            .userNm(userNm)
                            .userTel(userTel)
                            .userCreated(userCreated)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserData(Integer userId) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        User userData = userRepository.selectAllUserData(userId);
        if (userData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        final String userEmail = userData.getUserEmail();
        final String userNm = userData.getUserNm();
        final String userTel = userData.getUserTel();
        final String userAddr = userData.getUserAddr();
        final LocalDateTime userCreated = userData.getUserCreated();
        final LocalDateTime userUpdated = userData.getUserUpdated();

        return UserResponseDTO.builder()
                .userId(userId)
                .userEmail(userEmail)
                .userNm(userNm)
                .userTel(userTel)
                .userAddr(userAddr)
                .userCreated(userCreated)
                .userUpdated(userUpdated)
                .build();
    }

    @Override
    public void adminUpdateUser(UserManagementRequestDTO requestData) {
        final Integer userId = requestData.getUserId();
        final String userEmail = requestData.getUserEmail();
        final String userNm = requestData.getUserNm();
        final String userTel = requestData.getUserTel();
        final String userAddr = requestData.getUserAddr();

        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        User userData = userRepository.selectAllUserData(userId);
        if (userData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        User updateUserData = User.builder()
                .userId(userId)
                .userEmail(userEmail)
                .userNm(userNm)
                .userTel(userTel)
                .userAddr(userAddr)
                .build();

        userRepository.adminUserUpdate(updateUserData);
    }
}