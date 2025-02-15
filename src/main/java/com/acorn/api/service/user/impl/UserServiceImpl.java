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
        Boolean exists = userRepository.isEmailDuplicate(userEmail);
        if (exists) {
            throw new AcontainerException(ApiValidationErrorCode.EMAIL_DUPLICATED_CONFLICT);
        }
    }

    @Override
    public void isTelPhoneDuplicate(String userTel) {
        Boolean exists = userRepository.isTelDuplicate(userTel);
        if (exists) {
            throw new AcontainerException(ApiValidationErrorCode.TELPHONE_DUPLICATED_CONFLICT);
        }
    }

    @Override
    @Transactional
    public void userRegister(UserRegisterDTO userRegisterData) {
        final Integer userId = userRepository.selectUserIdKey();
        final String userEmail = userRegisterData.getUserEmail();
        final String encodedPassword = passwordEncoder.encode(userRegisterData.getUserPassword());
        final String userNm = userRegisterData.getUserNm();
        final String userAddr = userRegisterData.getUserAddr();
        final String userTel = userRegisterData.getUserTel();

        User newUser = User.builder()
                .userId(userId)
                .userEmail(userEmail)
                .userPassword(encodedPassword)
                .userNm(userNm)
                .userAddr(userAddr)
                .userTel(userTel)
                .userRole(UserRole.USER)
                .build();

        userRepository.userRegister(newUser);
    }

    @Override
    public UserResponseDTO getUserData() {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        User userData = userRepository.selectAllUserData(currentUserId);
        if (userData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }
        final Integer userId = userData.getUserId();
        final String userEmail = userData.getUserEmail();
        final String userNm = userData.getUserNm();
        final String userTel = userData.getUserTel();
        final String userAddr = userData.getUserAddr();

        return UserResponseDTO.builder()
                .userId(userId)
                .userEmail(userEmail)
                .userNm(userNm)
                .userTel(userTel)
                .userAddr(userAddr)
                .build();
    }

    @Override
    @Transactional
    public void userDataUpdate(UserUpdateDTO userUpdateData) {
        final Integer currentUserId  = CommonSecurityUtil.getCurrentUserId();
        final Integer userId = userUpdateData.getUserId();
        final String userPassword = userUpdateData.getUserPassword();
        final String userNm = userUpdateData.getUserNm();
        final String userAddr = userUpdateData.getUserAddr();
        final String userTel = userUpdateData.getUserTel();

        if (Objects.isNull(currentUserId) || !Objects.equals(currentUserId, userId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        User existingUser = userRepository.selectAllUserData(userId);
        if (existingUser == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }
        final String existingUserPassword = existingUser.getUserPassword();

        if (StringUtils.isNotBlank(userPassword) && !passwordEncoder.matches(userPassword, existingUserPassword)) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        User updateUser = User.builder()
                .userId(userId)
                .userNm(userNm)
                .userAddr(userAddr)
                .userTel(userTel)
                .build();

        userRepository.userUpdate(updateUser);
    }

    @Override
    @Transactional
    public void userDataDelete(UserDeleteDTO userDeleteData) {
        final Integer currentUserId  = CommonSecurityUtil.getCurrentUserId();
        final Integer userId = userDeleteData.getUserId();
        final String userPassword = userDeleteData.getUserPassword();

        if (Objects.isNull(currentUserId) || !Objects.equals(currentUserId, userId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        User existingUser = userRepository.selectAllUserData(userId);
        if (existingUser == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }
        final String existingUserPassword = existingUser.getUserPassword();

        if (StringUtils.isNotBlank(userPassword) && !passwordEncoder.matches(userPassword, existingUserPassword)) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        User deleteUser = User.builder()
                .userId(userId)
                .build();

        userRepository.userDelete(deleteUser);
    }
}