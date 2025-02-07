package com.acorn.api.service.user.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.dto.user.UserDeleteDTO;
import com.acorn.api.dto.user.UserResponseDTO;
import com.acorn.api.dto.user.UserRegisterDTO;
import com.acorn.api.dto.user.UserUpdateDTO;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.entity.user.User;
import com.acorn.api.repository.user.UserRepository;
import com.acorn.api.role.UserRole;
import com.acorn.api.service.user.UserService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void isEmailDuplicate(String userEmail) {
        Integer emailCount = userRepository.isEmailDuplicate(userEmail);
        if (emailCount != null) {
            throw new AcontainerException(ApiValidationErrorCode.EMAIL_DUPLICATED_CONFLICT);
        }
    }

    @Override
    public void isTelPhoneDuplicate(String userTel) {
        Integer telCount = userRepository.isTelDuplicate(userTel);
        if (telCount != null) {
            throw new AcontainerException(ApiValidationErrorCode.TELPHONE_DUPLICATED_CONFLICT);
        }
    }

    @Override
    @Transactional
    public void userRegister(UserRegisterDTO userRegisterData) {
        final Integer userId = userRepository.selectUserIdKey();
        final String encodedPassword = passwordEncoder.encode(userRegisterData.getUserPassword());

        User newUser = User.builder()
                .userId(userId)
                .userEmail(userRegisterData.getUserEmail())
                .userPassword(encodedPassword)
                .userNm(userRegisterData.getUserNm())
                .userAddr(userRegisterData.getUserAddr())
                .userTel(userRegisterData.getUserTel())
                .userRole(UserRole.USER)
                .build();

        userRepository.userRegister(newUser);
    }

    @Override
    public UserResponseDTO getUserData() {
        final Integer userId = CommonSecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        User userData = userRepository.selectAllUserData(userId);
        if (userData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        return UserResponseDTO.builder()
                .userId(userData.getUserId())
                .userEmail(userData.getUserEmail())
                .userNm(userData.getUserNm())
                .userTel(userData.getUserTel())
                .userAddr(userData.getUserAddr())
                .build();
    }

    @Override
    @Transactional
    public void userDataUpdate(UserUpdateDTO userUpdateData) {
        final Integer userId  = CommonSecurityUtil.getCurrentUserId();
        if (Objects.isNull(userId) || !Objects.equals(userId, userUpdateData.getUserId())) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        User existingUser = userRepository.selectAllUserData(userId);
        if (existingUser == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        if (StringUtils.isNotBlank(userUpdateData.getUserPassword()) && !passwordEncoder.matches(userUpdateData.getUserPassword(), existingUser.getUserPassword())) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        User updateUser = User.builder()
                .userId(userUpdateData.getUserId())
                .userNm(userUpdateData.getUserNm())
                .userAddr(userUpdateData.getUserAddr())
                .userTel(userUpdateData.getUserTel())
                .build();

        userRepository.userUpdate(updateUser);
    }

    @Override
    @Transactional
    public void userDataDelete(UserDeleteDTO userDeleteData) {
        final Integer userId  = CommonSecurityUtil.getCurrentUserId();
        if (Objects.isNull(userId) || !Objects.equals(userId, userDeleteData.getUserId())) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        User existingUser = userRepository.selectAllUserData(userId);
        if (existingUser == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        if (StringUtils.isNotBlank(userDeleteData.getUserPassword()) && !passwordEncoder.matches(userDeleteData.getUserPassword(), existingUser.getUserPassword())) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        User deleteUser = User.builder()
                .userId(userDeleteData.getUserId())
                .build();

        userRepository.userDelete(deleteUser);
    }
}