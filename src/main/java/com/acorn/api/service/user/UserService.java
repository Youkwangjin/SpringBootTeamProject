package com.acorn.api.service.user;

import com.acorn.api.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean isEmailDuplicate(String userEmail);

    void userRegister(User user);

    boolean isTelPhoneDuplicate(String userTel);

    User getUserData();

    void userDataUpdate(User user);

    void userDataDelete(User user);
}