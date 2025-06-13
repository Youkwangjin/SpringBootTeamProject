package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.reservation.ReservationStatus;
import com.acorn.api.dto.admin.AdminUserDeleteRequestDTO;
import com.acorn.api.dto.admin.AdminUserListDTO;
import com.acorn.api.dto.admin.AdminUserUpdateRequestDTO;
import com.acorn.api.dto.user.response.UserResDTO;
import com.acorn.api.entity.admin.Admin;
import com.acorn.api.entity.reservation.Reservation;
import com.acorn.api.entity.user.User;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.repository.reservation.ReservationRepository;
import com.acorn.api.repository.user.UserRepository;
import com.acorn.api.service.admin.AdminUserService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<AdminUserListDTO> getUserList(AdminUserListDTO listData) {
        listData.setTotalCount(userRepository.selectAdminUserListCountByRequest(listData));
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
    public UserResDTO getUserData(Integer userId) {
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

        return UserResDTO.builder()
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
    @Transactional
    public void adminUpdateUser(AdminUserUpdateRequestDTO requestData) {
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

        List<Reservation> ReservationData = reservationRepository.selectReservationByUserId(userId);
        for (Reservation reservation : ReservationData) {
            final Integer reservationStatus = reservation.getReservationStatus();

            if (Objects.equals(reservationStatus, ReservationStatus.RESERVATION_STATUS_ACTIVE.getCode())) {
                throw new AcontainerException(ApiErrorCode.RESERVATION_STATUS_ACTIVE);
            }
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

    @Override
    @Transactional
    public void adminDeleteUser(AdminUserDeleteRequestDTO requestData) {
        final Integer userId = requestData.getUserId();
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

        List<Reservation> ReservationData = reservationRepository.selectReservationByUserId(userId);
        for (Reservation reservation : ReservationData) {
            final Integer reservationStatus = reservation.getReservationStatus();

            if (Objects.equals(reservationStatus, ReservationStatus.RESERVATION_STATUS_ACTIVE.getCode())) {
                throw new AcontainerException(ApiErrorCode.RESERVATION_STATUS_ACTIVE);
            }
        }

        User deleteUserData = User.builder()
                .userId(userId)
                .build();

        userRepository.userDelete(deleteUserData);
    }
}