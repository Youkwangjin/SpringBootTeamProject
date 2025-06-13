package com.acorn.api.service.user;

import com.acorn.api.dto.user.request.UserDeleteResDTO;
import com.acorn.api.dto.user.response.UserResDTO;
import com.acorn.api.dto.user.request.UserRegisterResDTO;
import com.acorn.api.dto.user.request.UserUpdateResDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void isEmailDuplicate(String userEmail);

    void userRegister(UserRegisterResDTO userRegisterData);

    void isTelPhoneDuplicate(String userTel);

    UserResDTO getUserData();

    void userDataUpdate(UserUpdateResDTO userUpdateData);

    void userDataDelete(UserDeleteResDTO userDeleteData);
}