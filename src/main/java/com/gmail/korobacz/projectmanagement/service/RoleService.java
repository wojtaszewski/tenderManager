package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.exception.AddRoleException;
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

    public Role getRoleByName(RoleDTO role) {
        if(role.getName().startsWith("ROLE_")){
            return roleRepository.findByName(role.getName());
        }else{
            String properRoleName = "ROLE_"+role.getName();
            return roleRepository.findByName(properRoleName);
        }

    }

    public void save(RoleDTO role) throws AddRoleException {
        if (role == null || role.getName() == null || role.getName().isEmpty()) {
            throw new AddRoleException();
        } else {
            roleRepository.save(new Role("ROLE_" + role.getName().toUpperCase()));
        }
    }
}
