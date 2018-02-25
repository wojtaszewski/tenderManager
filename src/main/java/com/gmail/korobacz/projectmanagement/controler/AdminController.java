package com.gmail.korobacz.projectmanagement.controler;


import com.gmail.korobacz.projectmanagement.dto.RoleDTO;
import com.gmail.korobacz.projectmanagement.dto.UserDTO;
import com.gmail.korobacz.projectmanagement.exception.AddRoleException;
import com.gmail.korobacz.projectmanagement.exception.AddUserException;
import com.gmail.korobacz.projectmanagement.exception.DeleteUserException;
import com.gmail.korobacz.projectmanagement.service.RoleService;
import com.gmail.korobacz.projectmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private RoleService roleService;
    private UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUserForm(Model model) {
        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("roles", roleService.getAllPossibleRoles());
        return "admin/addUser";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@Valid UserDTO user, BindingResult bindingResult, Model model) {
        try {
            userService.save(user);
            model.addAttribute("status", true);
        } catch (AddUserException e) {
            model.addAttribute("status", false);
        }
        return "admin/addUser";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("selectedUser", new UserDTO());
        return "admin/deleteUser";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@Valid UserDTO user, BindingResult bindingResult, Model model) {
        try {
            userService.deleteUser(user);
            model.addAttribute("status", true);
        } catch (DeleteUserException e) {
            model.addAttribute("status", false);
        }
        return "admin/deleteUser";
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.GET)
    public String addRoleForm(Model model) {
        model.addAttribute("newRole", new RoleDTO());
        return "admin/addRole";
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addRole(@Valid RoleDTO role, BindingResult bindingResult, Model model) {
        try {
            roleService.save(role);
            model.addAttribute("status", true);
        } catch (AddRoleException e) {
            model.addAttribute("status", false);
        }
        return "admin/addRole";
    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
    public String deleteRoleList(Model model) {
        model.addAttribute("roles", roleService.getAllPossibleRoles());
        model.addAttribute("selectedRole", new RoleDTO());
        return "admin/deleteRole";
    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public String deleteRole(@Valid RoleDTO role, BindingResult bindingResult, Model model) {
        try {
            roleService.save(role);
            model.addAttribute("status", true);
        } catch (AddRoleException e) {
            model.addAttribute("status", false);
        }
        return "admin/deleteRole";
    }
}
