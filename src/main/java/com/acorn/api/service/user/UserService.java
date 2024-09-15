package com.acorn.api.service.user;

import com.acorn.api.dto.user.UserResponseDTO;
import com.acorn.api.dto.user.UserRegisterDTO;
import com.acorn.api.dto.user.UserUpdateDTO;
import com.acorn.api.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean isEmailDuplicate(String userEmail);

    void userRegister(UserRegisterDTO userRegisterData);

    boolean isTelPhoneDuplicate(String userTel);

    UserResponseDTO getUserData();

    void userDataUpdate(UserUpdateDTO userUpdateData);

    void userDataDelete(User user);
}