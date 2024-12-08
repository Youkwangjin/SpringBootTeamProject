package com.acorn.api.service.user.impl;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.dto.user.UserDeleteDTO;
import com.acorn.api.dto.user.UserResponseDTO;
import com.acorn.api.dto.user.UserRegisterDTO;
import com.acorn.api.dto.user.UserUpdateDTO;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.model.user.User;
import com.acorn.api.repository.user.UserRepository;
import com.acorn.api.role.UserRole;
import com.acorn.api.service.user.UserService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void isEmailDuplicate(String userEmail) {
        int emailCount = userRepository.isEmailDuplicate(userEmail);

        if (emailCount > 0) {
            throw new AcontainerException(ApiValidationErrorCode.EMAIL_DUPLICATED);
        }
    }

    @Override
    public void isTelPhoneDuplicate(String userTel) {
        int telCount = userRepository.isTelDuplicate(userTel);

        if (telCount > 0) {
            throw new AcontainerException(ApiValidationErrorCode.TELEPHONE_DUPLICATED);
        }
    }

    @Override
    @Transactional
    public void userRegister(UserRegisterDTO userRegisterData) {
        final Integer userId = userRepository.selectUserIdKey();
        final String encodedPassword = passwordEncoder.encode(userRegisterData.getUserPassword());

        User newUser = User.builder()
                .userId(userId)
                .userUUId(UUID.randomUUID().toString())
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
    public UserResponseDTO getUserData() throws AuthenticationException {
        final Integer userId = CommonSecurityUtil.getCurrentUserId();

        if (userId == null) {
            throw new AccessDeniedException("Unauthorized to update user data");
        }

        User userData = userRepository.selectAllUserData(userId);

        if (userData == null) {
            throw new UsernameNotFoundException("User data not found");
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

        if (userId == null || !userId.equals(userUpdateData.getUserId())) {
            throw new AccessDeniedException("Unauthorized to update user data");
        }

        User existingUser = userRepository.selectAllUserData(userId);

        if (existingUser == null) {
            throw new UsernameNotFoundException("User data not found");
        }

        if (StringUtils.isNotBlank(userUpdateData.getUserPassword()) && !passwordEncoder.matches(userUpdateData.getUserPassword(), existingUser.getUserPassword())) {
            throw new IllegalArgumentException("Invalid current password");
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
        final Integer userId = CommonSecurityUtil.getCurrentUserId();
        String authenticatedUUId  = CommonSecurityUtil.getAuthenticatedUUId();

        if (StringUtils.isBlank(authenticatedUUId) || !StringUtils.equals(authenticatedUUId, userDeleteData.getUserUUId())) {
            throw new AccessDeniedException("Unauthorized to delete user data");
        }

        User existingUser = userRepository.selectAllUserData(userId);
        if (existingUser == null) {
            throw new UsernameNotFoundException("User not found : " + authenticatedUUId);
        }

        if (StringUtils.isNotBlank(userDeleteData.getUserPassword()) && !passwordEncoder.matches(userDeleteData.getUserPassword(), existingUser.getUserPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        User deleteUser = User.builder()
                .userUUId(userDeleteData.getUserUUId())
                .build();

        userRepository.userDelete(deleteUser);
    }
}