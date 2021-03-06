package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.exception.AddRoleException;
import com.gmail.korobacz.projectmanagement.exception.DeleteRoleException;
import com.gmail.korobacz.projectmanagement.model.Role;
import com.gmail.korobacz.projectmanagement.repository.CustomRepository;
import com.gmail.korobacz.projectmanagement.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private CustomRepository customRepository;

    public RoleService(RoleRepository roleRepository, CustomRepository customRepository) {
        this.roleRepository = roleRepository;
        this.customRepository = customRepository;
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

    //zmienić z name na id jako klucz
    public Optional<Role> getRoleByName(RoleDTO role) {
        String roleName = role.getName().toUpperCase();
        if (roleName.startsWith("ROLE_")) {
            return roleRepository.findByName(roleName);
        } else {
            String properRoleName = "ROLE_" + roleName;
            return roleRepository.findByName(properRoleName);
        }

    }

    public void save(RoleDTO role) throws AddRoleException {
        if (role == null || role.getName() == null || role.getName().isEmpty() || getRoleByName(role).isPresent()) {
            throw new AddRoleException();
        } else if (role.getName().toUpperCase().startsWith("ROLE_") && role.getName().length() > 5) {
            roleRepository.save(new Role(role.getName().toUpperCase()));
        } else {
            roleRepository.save(new Role("ROLE_" + role.getName().toUpperCase()));
        }
    }

    public void deleteRole(RoleDTO roleDTO) throws DeleteRoleException {
        boolean condition = customRepository.isRoleActive(getRoleByName(roleDTO).get());
        if(condition){
            throw new DeleteRoleException();
        }else{
            roleRepository.deleteByName(roleDTO.getName());
        }
    }
}

