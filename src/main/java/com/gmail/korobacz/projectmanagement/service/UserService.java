package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.dto.UserDTO;
import com.gmail.korobacz.projectmanagement.exception.AddUserException;
import com.gmail.korobacz.projectmanagement.exception.DeleteUserException;
import com.gmail.korobacz.projectmanagement.model.Role;
import com.gmail.korobacz.projectmanagement.model.User;
import com.gmail.korobacz.projectmanagement.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(u -> new org.springframework.security.core.userdetails.User(u.getEmail(),
                        u.getPassword(), mapRolesToAuthorities(u.getRoles())))
                .orElseThrow(() -> new UsernameNotFoundException("Błędny login lub hasło."));
    }

    public UserDTO getUserDetails(String email) {
        return userRepository
                .findByEmail(email)
                .map(user -> new UserDTO.UserDTOBuilder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .roles(mapRolesToDto(user.getRoles()))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Nie ma takiego usera"));
    }

    public void save(UserDTO userDTO) throws AddUserException {
        if (userDTO == null) {
            throw new AddUserException();
        }
        boolean isLoginAvailable = !userRepository.findByEmail(userDTO.getEmail()).isPresent();
        if (isUserDTOValid(userDTO) && isLoginAvailable) {
            userRepository
                    .save(new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), roleService.getRolesByNames(userDTO.getRoles())));
        } else {
            throw new AddUserException();
        }
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), null, mapRolesToDto(user.getRoles())))
                .collect(Collectors.toList());
    }

    public void deleteUser(UserDTO user) throws DeleteUserException {
        String loggedUserMail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (user == null || user.getEmail() == null || loggedUserMail.equals(user.getEmail())) {
            throw new DeleteUserException();
        } else {
            userRepository.deleteByEmail(user.getEmail());
        }
    }

    private List<RoleDTO> mapRolesToDto(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    private boolean isUserDTOValid(UserDTO userDTO) {
        boolean isRoleValid = userDTO
                .getRoles()
                .stream()
                .anyMatch(role -> role.getName() != null);
        if (userDTO.getEmail() == null || userDTO.getPassword() == null) {
            return false;
        }
        if (!userDTO.getEmail().isEmpty() && !userDTO.getPassword().isEmpty() && isRoleValid) {
            return true;
        }
        return false;
    }

}

