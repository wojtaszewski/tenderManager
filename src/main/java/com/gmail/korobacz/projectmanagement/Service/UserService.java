package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.DTO.UserDTO;
import com.gmail.korobacz.projectmanagement.model.Role;
import com.gmail.korobacz.projectmanagement.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map( u ->  new org.springframework.security.core.userdetails.User(u.getEmail(),
                    u.getPassword(), mapRolesToAuthorities(u.getRoles())))
                .orElseThrow(() -> new UsernameNotFoundException("Błędny login lub hasło."));
    }

    public UserDTO getUserDetails(String email){
        return userRepository
                .findByEmail(email)
                .map(user -> new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoles()))
                .orElseThrow(() -> new UsernameNotFoundException("Nie ma takiego usera"));
    }



    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
