package com.challenge.users.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitialController {

    @GetMapping("/")
    public String documentation(){
        return "redirect:/swagger-ui.html";
    }
}
