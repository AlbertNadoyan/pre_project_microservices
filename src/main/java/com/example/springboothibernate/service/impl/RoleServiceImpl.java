package com.example.springboothibernate.service.impl;

import com.example.springboothibernate.model.Role;
import com.example.springboothibernate.repository.RoleRepository;
import com.example.springboothibernate.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

}
