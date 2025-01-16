package com.example.santa.register.service;

import com.example.santa.register.vo.RegisterVO;

public interface RegisterServiceInterface {
    boolean registerUser(RegisterVO registerVO);

    boolean isUserIdAvailable(String userId);

    // 사원 코드 유효성 검증
    boolean isEmployeeCodeValid(String employeeCode);
}
