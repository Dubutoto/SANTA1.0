package com.example.santa.login.service;

import com.example.santa.login.vo.LoginVO;
import com.example.santa.login.vo.UserDetailsVO;

public interface LoginServiceInterface {
    LoginVO authenticateUser(String userId, String password);

    UserDetailsVO getUserDetails(String userId);
}
