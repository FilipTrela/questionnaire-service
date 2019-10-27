package com.j25.pollsterservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("user", principal.getName());
        return "index";
    }

    @GetMapping("/tylkodlakozakow")
    public String kozaki() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }
}
