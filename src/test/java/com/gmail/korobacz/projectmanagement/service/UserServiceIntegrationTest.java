package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.dto.UserDTO;
import com.gmail.korobacz.projectmanagement.exception.AddUserException;
import com.gmail.korobacz.projectmanagement.exception.DeleteUserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        userService.saveUser(userDTO);
        //then
        UserDTO createdUser = userService.getUserDetails("shouldAddUser@test.pl");
        assertThat(createdUser).isEqualToComparingOnlyGivenFields(userDTO, "firstName", "lastName", "email");
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
        Throwable thrown = catchThrowable(() -> userService.saveUser(userDTO));
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
        Throwable thrown = catchThrowable(() -> userService.saveUser(userDTO));
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
        userService.saveUser(userDTO);
        UserDTO userDTOClone = new UserDTO
                .UserDTOBuilder()
                .firstName("w")
                .lastName("kor")
                .password("w")
                .email("shouldNotAddUserWhenLoginIsNotAvailable@w.w")
                .roles(roles)
                .build();
        //when
        Throwable thrown = catchThrowable(() -> userService.saveUser(userDTOClone));
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
        userService.saveUser(userDTO);
        //when
        UserDetails userDetails = userService.loadUserByUsername("shouldLoadUserDetails@test.pl");
        //then
        assertThat(userDetails.getUsername()).isEqualTo(userDTO.getEmail());
    }

    @Test
    public void shouldNotLoadUserDetailsWhenUserDoesntExists() throws AddUserException {
        //given
        //when
        Throwable thrown = catchThrowable(() -> userService.loadUserByUsername("zzz"));
        //then
        assertThat(thrown).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    @WithMockUser("customUsername")
    public void shouldDeleteUser() throws AddUserException, DeleteUserException {
        //given
        int counterBeforeSave = userService.getAllUsers().size();
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");
        roles.add(roleDTO);
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("test")
                .email("shouldDeleteUser@test.pl")
                .roles(roles)
                .build();
        userService.saveUser(userDTO);
        int counterAfterSave = userService.getAllUsers().size();
        //when
        userService.deleteUser(userDTO);
        int counterAfterDelete = userService.getAllUsers().size();
        //then
        assertThat(counterBeforeSave).isEqualTo(counterAfterSave - 1).isEqualTo(counterAfterDelete);
        assertThat(userService.getAllUsers().stream().map(user -> new String(user.getEmail())).collect(Collectors.toList())).doesNotContain("shouldDeleteUser@test.pl");
    }

    @Test
    @WithMockUser("customUsername")
    public void shouldNotDeleteUserWhenUserIsNull() {
        //given
        //when
        Throwable thrown = catchThrowable(() -> userService.deleteUser(null));
        //then
        assertThat(thrown).isInstanceOf(DeleteUserException.class);;
    }

    @Test
    @WithMockUser("shouldNotDeleteUserWhenUserIsLogged@test.pl")
    public void shouldNotDeleteUserWhenUserIsLogged() throws AddUserException {
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
                .email("shouldNotDeleteUserWhenUserIsLogged@test.pl")
                .roles(roles)
                .build();
        userService.saveUser(userDTO);
        //when
        Throwable thrown = catchThrowable(() -> userService.deleteUser(userDTO));
        //then
        assertThat(thrown).isInstanceOf(DeleteUserException.class);;
    }


}
