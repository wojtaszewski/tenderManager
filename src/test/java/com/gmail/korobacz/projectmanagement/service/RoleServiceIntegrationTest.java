package com.gmail.korobacz.projectmanagement.service;

import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.exception.AddRoleException;
import com.gmail.korobacz.projectmanagement.exception.DeleteRoleException;
import com.gmail.korobacz.projectmanagement.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RoleServiceIntegrationTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void shouldGetAllPossibleRoles() {
        //given
        //ze skryptu wynika, że są dwie role i te role sprawdzam
        //when
        List<RoleDTO> roles = roleService.getAllPossibleRoles();
        //then
        assertThat(roles).isNotEmpty()
                .hasSize(2)
                .doesNotHaveDuplicates();
        assertThat(roles
                .stream()
                .map(role -> new String(role.getName()))
                .collect(Collectors.toList()))
                .containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_USER");
    }

    @Test
    public void shouldGetRolesByNames() {
        //given
        List<RoleDTO> rolesDTO = new ArrayList<>();
        rolesDTO.add(new RoleDTO((long) 1, "ROLE_ADMIN"));
        //when
        Collection<Role> roles = roleService.getRolesByNames(rolesDTO);
        //then
        assertThat(roles).isNotEmpty()
                .hasSize(1);
        assertThat(roles
                .stream()
                .map(role -> new String(role.getName()))
                .collect(Collectors.toList()))
                .containsExactly("ROLE_ADMIN");
    }

    @Test
    public void shouldNotGetRolesByEmptyList() {
        //given
        List<RoleDTO> rolesDTO = new ArrayList<>();
        //when
        Collection<Role> roles = roleService.getRolesByNames(rolesDTO);
        //then
        assertThat(roles).isNotNull().isEmpty();
    }

    @Test
    public void shouldNotGetRolesByNotValidName() {
        //given
        List<RoleDTO> rolesDTO = new ArrayList<>();
        rolesDTO.add(new RoleDTO((long) 1, "ROLE_EEEE"));
        //when
        Collection<Role> roles = roleService.getRolesByNames(rolesDTO);
        //then
        assertThat(roles).isNotNull().isEmpty();
    }

    @Test
    public void shouldSaveRole() throws AddRoleException {
        //given
        RoleDTO roleDTO = new RoleDTO((long) 1, "elo");
        //when
        roleService.save(roleDTO);
        //then
        assertThat(roleService.getRoleByName(roleDTO).isPresent());
        assertThat(roleService.getRoleByName(roleDTO).get().getName().equals("ROLE_"+roleDTO));
    }

    @Test
    public void shouldNotSaveRoleWhenRoleIsNull(){
        //given
        //when
        Throwable thrown = catchThrowable(() -> roleService.save(null));
        //then
        assertThat(thrown).isInstanceOf(AddRoleException.class);
    }

    @Test
    public void shouldNotSaveRoleWhenRoleNameIsNull(){
        //given
        RoleDTO roleDTO = new RoleDTO((long) 1, null);
        //when
        Throwable thrown = catchThrowable(() -> roleService.save(roleDTO));
        //then
        assertThat(thrown).isInstanceOf(AddRoleException.class);
    }

    @Test
    public void shouldNotSaveRoleWhenRoleNameIsEmpty(){
        //given
        RoleDTO roleDTO = new RoleDTO((long) 1, "");
        //when
        Throwable thrown = catchThrowable(() -> roleService.save(roleDTO));
        //then
        assertThat(thrown).isInstanceOf(AddRoleException.class);
    }

    @Test
    public void shouldNotSaveRoleWhenRoleNameIsAlreadyExists(){
        //given
        RoleDTO roleDTO = new RoleDTO((long) 1, "ROLE_ADMIN");
        //when
        Throwable thrown = catchThrowable(() -> roleService.save(roleDTO));
        //then
        assertThat(thrown).isInstanceOf(AddRoleException.class);
    }

    @Test
    public void shouldDeleteRoleWhenRoleIsNotUsed() throws AddRoleException, DeleteRoleException {
        //given
        RoleDTO roleDTO = new RoleDTO((long) 1, "ROLE_NEW");
        roleService.save(roleDTO);
        int numberOfRolesBeforeDelete = roleService.getAllPossibleRoles().size();
        //when
        roleService.deleteRole(roleDTO);
        //then
        int numberOfRolesAfterDelete = roleService.getAllPossibleRoles().size();
        assertThat(!roleService.getRoleByName(roleDTO).isPresent());
        assertThat(numberOfRolesBeforeDelete).isEqualTo(numberOfRolesAfterDelete+1);
    }

}
