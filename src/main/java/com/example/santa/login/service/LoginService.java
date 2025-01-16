package com.example.santa.login.service;

import com.example.santa.login.mapper.LoginMapper;
import com.example.santa.login.vo.LoginVO;
import com.example.santa.login.vo.UserDetailsVO;
import com.example.santa.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceInterface {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public LoginVO authenticateUser(String userId, String password) {
        LoginVO user = loginMapper.getUserByUserId(userId);

        // 비밀번호 검증
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user; // 인증 성공
        }

        return null; // 인증 실패
    }

    @Override
    public UserDetailsVO getUserDetails(String userId) {
        // 사용자 추가 정보를 가져오기 위해 Mapper 호출
        return loginMapper.getUserDetailsByUserId(userId);
    }
}

