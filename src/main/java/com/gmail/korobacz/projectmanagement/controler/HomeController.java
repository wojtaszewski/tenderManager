package com.gmail.korobacz.projectmanagement.controler;

import com.gmail.korobacz.projectmanagement.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {


    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/login")
    public String loginGet () {
        return "login";
    }

    @RequestMapping(value={"/main", "/"})
    public String mainPage(HttpSession session, @AuthenticationPrincipal UserDetails currentUser){
        session.setAttribute("user", userService.getUserDetails(currentUser.getUsername()));
        return "main";
    }

}
