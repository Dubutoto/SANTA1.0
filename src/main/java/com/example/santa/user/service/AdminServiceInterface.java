package com.example.santa.user.service;

import com.example.santa.user.vo.AdminVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminServiceInterface {
    List<AdminVO> getAllAdmins();

    @Transactional
    boolean updateAdmin(AdminVO adminVO);

    @Transactional
    boolean deleteAdmin(int adminId);
}
