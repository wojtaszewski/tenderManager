package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.dto.UserDTO;
import com.gmail.korobacz.projectmanagement.exception.AddUserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceIntegrationTest {


    @Autowired
    private UserService userService;


    @Test
    public void shouldAddUser() throws AddUserException {
        //given
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");
        roles.add(roleDTO);
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("test")
                .email("shouldAddUser@test.pl")
                .roles(roles)
                .build();
        //when
        userService.save(userDTO);
        //then
        UserDTO createdUser = userService.getUserDetails("shouldAddUser@test.pl");
        assertThat(createdUser).isEqualToComparingOnlyGivenFields(userDTO, "firstName", "lastName", "email" );
    }

    @Test
    public void shouldNotAddUserWhenPasswordIsEmpty() {
        //given
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");
        roles.add(roleDTO);
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .email("shouldNotAddUserWhenPasswordIsEmpty@test.pl")
                .roles(roles)
                .build();
        //when
        Throwable thrown = catchThrowable(() -> userService.save(userDTO));
        //then
        assertThat(thrown).isInstanceOf(AddUserException.class);
    }

    @Test
    public void shouldNotAddUserWhenLoginIsEmpty() {
        //given
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");
        roles.add(roleDTO);
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("test")
                .roles(roles)
                .build();
        //when
        Throwable thrown = catchThrowable(() -> userService.save(userDTO));
        //then
        assertThat(thrown).isInstanceOf(AddUserException.class);
    }

    @Test
    public void shouldNotAddUserWhenLoginIsNotAvailable() throws AddUserException {
        //given
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");
        roles.add(roleDTO);
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("w")
                .email("shouldNotAddUserWhenLoginIsNotAvailable@w.w")
                .roles(roles)
                .build();
        userService.save(userDTO);
        UserDTO userDTOClone = new UserDTO
                .UserDTOBuilder()
                .firstName("w")
                .lastName("kor")
                .password("w")
                .email("shouldNotAddUserWhenLoginIsNotAvailable@w.w")
                .roles(roles)
                .build();
        //when
        Throwable thrown = catchThrowable(() -> userService.save(userDTOClone));
        //then
        assertThat(thrown).isInstanceOf(AddUserException.class);
    }

    @Test
    public void shouldNotGetUserDetailsWhenLoginIsInvalid() throws AddUserException {
        //given
        //when
        Throwable thrown = catchThrowable(() -> userService.getUserDetails("zzz"));
        //then
        assertThat(thrown).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    public void shouldLoadUserDetails() throws AddUserException {
        //given
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");
        roles.add(roleDTO);
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("test")
                .email("shouldLoadUserDetails@test.pl")
                .roles(roles)
                .build();
        userService.save(userDTO);
        //when
        UserDetails userDetails = userService.loadUserByUsername("shouldLoadUserDetails@test.pl");
        //then
        assertThat(userDetails.getUsername()).isEqualTo(userDTO.getEmail());
    }

    @Test
    public void shouldNotLoadUserDetails() throws AddUserException {
        //given
        //when
        Throwable thrown = catchThrowable(() -> userService.loadUserByUsername("zzz"));
        //then
        assertThat(thrown).isInstanceOf(UsernameNotFoundException.class);
    }

}
