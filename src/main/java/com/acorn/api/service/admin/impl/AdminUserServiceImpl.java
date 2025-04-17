package com.acorn.api.service.admin.impl;

import com.acorn.api.dto.admin.AdminUserListDTO;
import com.acorn.api.entity.user.User;
import com.acorn.api.repository.user.UserRepository;
import com.acorn.api.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

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
}