package com.hello_world_sprinboot.scholify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value={"","/","/index"})
    public String displayHomePage(Model model){
        model.addAttribute("username","Vina");
        return "index.html";
    }


}
