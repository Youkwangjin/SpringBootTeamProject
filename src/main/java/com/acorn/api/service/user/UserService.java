package com.acorn.api.service.user;

import com.acorn.api.dto.user.UserDeleteDTO;
import com.acorn.api.dto.user.UserResponseDTO;
import com.acorn.api.dto.user.UserRegisterDTO;
import com.acorn.api.dto.user.UserUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void isEmailDuplicate(String userEmail);

    void userRegister(UserRegisterDTO userRegisterData);

    void isTelPhoneDuplicate(String userTel);

    UserResponseDTO getUserData();

    void userDataUpdate(UserUpdateDTO userUpdateData);

    void userDataDelete(UserDeleteDTO userDeleteData);
}