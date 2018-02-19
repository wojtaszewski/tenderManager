package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.model.Role;
import com.gmail.korobacz.projectmanagement.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> getAllPossibleRoles() {
        return roleRepository
                .findAllDistinct()
                .stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    public Collection<Role> getRolesByNames(List<RoleDTO> roles) {
        List<String> names = roles
                .stream()
                .filter(role -> role.getName() != null)
                .map(role -> new String(role.getName()))
                .collect(Collectors.toList());
        return roleRepository.findByNameIn(names);
    }
}
