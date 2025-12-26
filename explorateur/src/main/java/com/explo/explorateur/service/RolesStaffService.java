package com.explo.explorateur.service;

import org.springframework.stereotype.Service;

import com.explo.explorateur.entite.constant.RoleStaff;
import com.explo.explorateur.repository.constant.RoleStaffRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RolesStaffService {
    private final RoleStaffRepository roleStaffRepository;
    
    @Autowired
    public RolesStaffService(RoleStaffRepository roleStaffRepository) {
        this.roleStaffRepository = roleStaffRepository;
    }
    
    public List<RoleStaff> getAllRoles() {
        return roleStaffRepository.findAll();
    }
}
