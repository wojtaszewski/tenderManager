package com.gmail.korobacz.projectmanagement.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value="/login")
    public String login () {
        return "login";
    }


    @RequestMapping(value={"/main", "/"})
    public String main(Model model){
        //na razie na sztywno
        model.addAttribute("user","Dominika - wpisane na sztywno!");
        return "main";
    }

}
