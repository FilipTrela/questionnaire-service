package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.service.AccountService;
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

    private AccountService accountService;

    @GetMapping("/")
    public String index(Model model,Principal principal) {
        if(principal != null) {
            model.addAttribute("jesteAdmine", accountService.isAdmin(principal.getName()));
        }else{
            model.addAttribute("jesteAdmine", false);
        }
        return "index";
    }



    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }
}
