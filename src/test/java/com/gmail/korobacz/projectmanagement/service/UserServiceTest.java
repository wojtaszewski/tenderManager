package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.dto.UserDTO;
import com.gmail.korobacz.projectmanagement.exception.AddUserException;
import com.gmail.korobacz.projectmanagement.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    @Before
    public void setup() {
        userService = new UserService(userRepository, roleService, passwordEncoder);
    }

    @Test
    public void shouldAddUser() throws AddUserException {
        //given
        RoleDTO roleAdmin = new RoleDTO((long) 1, "ROLE_ADMIN");
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("w")
                .email("w@w.w")
                .roles(roles)
                .build();
        //when
        userService.save(userDTO);
        //then
        UserDTO createdUser = userService.getUserDetails("w@w.w");
        assertThat(createdUser).isEqualToComparingOnlyGivenFields(userDTO, "firstName", "lastName", "email" );
    }

    @Test
    public void shouldNotAddUserWhenPasswordIsNull(){
        //given
        RoleDTO roleAdmin = new RoleDTO((long) 1, "ROLE_ADMIN");
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .email("w@w.w")
                .roles(roles)
                .build();
        //when
        Throwable thrown = catchThrowable(() -> userService.save(userDTO));
        //then
        assertThat(thrown).isInstanceOf(AddUserException.class);
    }

    @Test
    public void shouldNotAddUserWhenLoginIsNull(){
        //given
        RoleDTO roleAdmin = new RoleDTO((long) 1, "ROLE_ADMIN");
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("w")
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
        RoleDTO roleAdmin = new RoleDTO((long) 1, "ROLE_ADMIN");
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("w")
                .email("w@w.w")
                .roles(roles)
                .build();
        UserDTO userDTOClone = new UserDTO
                .UserDTOBuilder()
                .firstName("w")
                .lastName("kor")
                .password("w")
                .email("w@w.w")
                .roles(roles)
                .build();
        //when
        userService.save(userDTO);
        Throwable thrown = catchThrowable(() -> userService.save(userDTOClone));
        //then
        assertThat(thrown).isInstanceOf(AddUserException.class);
    }

    @Test
    public void shouldNotGetUserDetailsWhenLoginIsInvalid() throws AddUserException {
        //given
        RoleDTO roleAdmin = new RoleDTO((long) 1, "ROLE_ADMIN");
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("w")
                .email("w@w.w")
                .roles(roles)
                .build();
        userService.save(userDTO);
        //when
        Throwable thrown = catchThrowable(() -> userService.getUserDetails("zzz"));
        //then
        assertThat(thrown).isInstanceOf(AddUserException.class);
    }

    @Test
    public void shouldLoadUserDetails() throws AddUserException {
        //given
        RoleDTO roleAdmin = new RoleDTO((long) 1, "ROLE_ADMIN");
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("w")
                .email("w@w.w")
                .roles(roles)
                .build();
        userService.save(userDTO);
        //when
        UserDetails userDetails = userService.loadUserByUsername("w@w.w");
        //then
        assertThat(userDetails.getUsername()).isEqualTo(userDTO.getEmail());
    }

    @Test
    public void shouldNotLoadUserDetails() throws AddUserException {
        //given
        RoleDTO roleAdmin = new RoleDTO((long) 1, "ROLE_ADMIN");
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO
                .UserDTOBuilder()
                .firstName("wojtek")
                .lastName("korobacz")
                .password("w")
                .email("w@w.w")
                .roles(roles)
                .build();
        userService.save(userDTO);
        //when
        Throwable thrown = catchThrowable(() -> userService.loadUserByUsername("w@w.w"));
        //then
        assertThat(thrown).isInstanceOf(AddUserException.class);
    }

}
