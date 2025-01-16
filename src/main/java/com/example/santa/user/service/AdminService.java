package com.example.santa.user.service;

import com.example.santa.user.mapper.AdminMapper;
import com.example.santa.user.vo.AdminVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService implements AdminServiceInterface {
    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public List<AdminVO> getAllAdmins() {
        return adminMapper.getAllAdmins();
    }

    @Transactional
    @Override
    public boolean updateAdmin(AdminVO adminVO) {
        int updatedRoles = adminMapper.updateEmployeeRole(adminVO.getEmployeeCode(), adminVO.getRole());
        int updatedAdmins = adminMapper.updateAdminDetails(adminVO);
        return updatedRoles > 0 && updatedAdmins > 0;
    }

    @Transactional
    @Override
    public boolean deleteAdmin(int adminId) {
        return adminMapper.deleteAdmin(adminId) > 0;
    }
}

