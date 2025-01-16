package com.example.santa.register.service;

import com.example.santa.register.mapper.RegisterMapper;
import com.example.santa.register.vo.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class RegisterService implements RegisterServiceInterface {

    @Autowired
    private RegisterMapper registerMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean registerUser(RegisterVO registerVO) {
        try {
            // 1. Employee Code 유효성 검증
            String role = registerMapper.getRoleByEmployeeCode(registerVO.getEmployeeCode());
            if (role == null) { // 유효하지 않은 Employee Code
                throw new IllegalArgumentException("Invalid Employee Code.");
            }
            registerVO.setRole(role); // 역할 설정

            // 2. 비밀번호 암호화
            registerVO.setPassword(passwordEncoder.encode(registerVO.getPassword()));

            // 3. 데이터베이스에 사용자 저장
            registerMapper.insertUser(registerVO);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage()); // 유효하지 않은 Employee Code 처리
            return false;
        } catch (Exception e) {
            e.printStackTrace(); // 기타 예외 처리
            return false;
        }
    }

    @Override
    public boolean isUserIdAvailable(String userId) {
        return registerMapper.getUserIdCount(userId) == 0;
    }

    // 사원 코드 유효성 검증
    @Override
    public boolean isEmployeeCodeValid(String employeeCode) {
        return registerMapper.getEmployeeCodeCount(employeeCode) > 0;
    }
}
