package com.acorn.api.service.user;

import com.acorn.api.dto.user.request.UserDeleteReqDTO;
import com.acorn.api.dto.user.response.UserResDTO;
import com.acorn.api.dto.user.request.UserRegisterReqDTO;
import com.acorn.api.dto.user.request.UserUpdateReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void isEmailDuplicate(String userEmail);

    void userRegister(UserRegisterReqDTO userRegisterData);

    void isTelPhoneDuplicate(String userTel);

    UserResDTO getUserData();

    void userDataUpdate(UserUpdateReqDTO userUpdateData);

    void userDataDelete(UserDeleteReqDTO userDeleteData);
}