package com.gmail.korobacz.projectmanagement.controler;


import com.gmail.korobacz.projectmanagement.dto.UserDTO;
import com.gmail.korobacz.projectmanagement.exception.AddUserException;
import com.gmail.korobacz.projectmanagement.service.RoleService;
import com.gmail.korobacz.projectmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    private RoleService roleService;
    private UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping(value="/addUser", method = RequestMethod.GET)
    public String addUserForm(Model model){
        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("roles", roleService.getAllPossibleRoles());
        return "admin/addUser";
    }

    @RequestMapping(value="/addUser", method = RequestMethod.POST)
    public String addUser(@Valid UserDTO user, BindingResult bindingResult, Model model){
        if(user == null){
            model.addAttribute("status", false);
        }else{
            try {
                userService.save(user);
                model.addAttribute("status", true);
            } catch (AddUserException e) {
                model.addAttribute("status", false);
            }
        }
        return "admin/addUser";
    }
}
