package com.acorn.api.service.user.impl;


import com.acorn.api.dto.user.UserDeleteDTO;
import com.acorn.api.dto.user.UserResponseDTO;
import com.acorn.api.dto.user.UserRegisterDTO;
import com.acorn.api.dto.user.UserUpdateDTO;
import com.acorn.api.model.user.User;
import com.acorn.api.repository.user.UserRepository;
import com.acorn.api.role.UserRole;
import com.acorn.api.service.user.UserService;
import com.acorn.api.utils.UserSecurityUtil;
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
    
    // 이메일 중복 검증
    @Override
    public boolean isEmailDuplicate(String userEmail) {
        return userRepository.isEmailDuplicate(userEmail);
    }
    
    //  전화번호 중복 검증
    @Override
    public boolean isTelPhoneDuplicate(String userTel) {
        String currentUserTelData = UserSecurityUtil.getAuthenticatedTelNumber();
        if (currentUserTelData != null && StringUtils.equals(currentUserTelData, userTel)) {
            return false;
        }
        return userRepository.isTelDuplicate(userTel);
    }

    // 회원가입
    @Override
    @Transactional
    public void userRegister(UserRegisterDTO userRegisterData) {
        String encodedPassword = passwordEncoder.encode(userRegisterData.getUserPassword());

        User newUser = User.builder()
                .userUUId(UUID.randomUUID().toString())
                .userEmail(userRegisterData.getUserEmail())
                .userPassword(encodedPassword)
                .userDisplayName(userRegisterData.getUserDisplayName())
                .userAddr(userRegisterData.getUserAddr())
                .userTel(userRegisterData.getUserTel())
                .userRole(UserRole.USER)
                .build();

        userRepository.userRegister(newUser);
    }
    
    // 사용자 정보 가져오기
    @Override
    public UserResponseDTO getUserData() throws AuthenticationException {
        String userUUId = UserSecurityUtil.getAuthenticatedUUId();

        if (!StringUtils.isNotBlank(userUUId)) {
            throw new UsernameNotFoundException("User is not authenticated");
        }

        User userData = userRepository.selectAllUserData(userUUId);

        if (userData == null) {
            throw new UsernameNotFoundException("User data not found");
        }

        return UserResponseDTO.builder()
                .userUUId(userData.getUserUUId())
                .userEmail(userData.getUserEmail())
                .userDisplayName(userData.getUserDisplayName())
                .userTel(userData.getUserTel())
                .userAddr(userData.getUserAddr())
                .build();
    }

    // 회원 수정
    @Override
    @Transactional
    public void userDataUpdate(UserUpdateDTO userUpdateData) {
        String authenticatedUUId  = UserSecurityUtil.getAuthenticatedUUId();

        if (StringUtils.isBlank(authenticatedUUId) || !StringUtils.equals(authenticatedUUId, userUpdateData.getUserUUId())) {
            throw new AccessDeniedException("Unauthorized to update user data");
        }

        User existingUser = userRepository.selectAllUserData(authenticatedUUId);
        if (existingUser == null) {
            throw new UsernameNotFoundException("User not found with UUID: " + authenticatedUUId);
        }

        if (StringUtils.isNotBlank(userUpdateData.getUserPassword()) && !passwordEncoder.matches(userUpdateData.getUserPassword(), existingUser.getUserPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        User updateUser = User.builder()
                .userUUId(userUpdateData.getUserUUId())
                .userDisplayName(userUpdateData.getUserDisplayName())
                .userAddr(userUpdateData.getUserAddr())
                .userTel(userUpdateData.getUserTel())
                .build();

        // 최종 반환
        userRepository.userUpdate(updateUser);
    }

    // 회원탈퇴
    @Override
    @Transactional
    public void userDataDelete(UserDeleteDTO userDeleteData) {

        String authenticatedUUId  = UserSecurityUtil.getAuthenticatedUUId();

        if (StringUtils.isBlank(authenticatedUUId) || !StringUtils.equals(authenticatedUUId, userDeleteData.getUserUUId())) {
            throw new AccessDeniedException("Unauthorized to delete user data");
        }

        User existingUser = userRepository.selectAllUserData(authenticatedUUId);
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
