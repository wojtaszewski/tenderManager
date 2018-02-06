package com.gmail.korobacz.projectmanagement.controler;

import com.gmail.korobacz.projectmanagement.Greeting;
import com.gmail.korobacz.projectmanagement.model.User;
import com.gmail.korobacz.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    UserService userService;


    @RequestMapping(value="/login")
    public String loginGet () {
        return "login";
    }

    @RequestMapping(value={"/main", "/"})
    public String mainPage (Model model, @AuthenticationPrincipal UserDetails currentUser) {
        model.addAttribute("user", userService.getUserDetails(currentUser.getUsername()));
        return "main";
    }

    @RequestMapping(value="/greeting", method = RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @RequestMapping(value="/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "result";
    }

}
