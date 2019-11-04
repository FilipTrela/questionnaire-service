package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.model.dto.CreateNewAccountRequest;
import com.j25.pollsterservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping(path = "/user/")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String registrationForm(Model model, CreateNewAccountRequest request) {
        model.addAttribute("newAccount", request);

        return "registration-form";
    }

    @PostMapping("/register")
    public String register(@Valid CreateNewAccountRequest request,
                           BindingResult result,
                           Model model) {

        if(result.hasErrors()){
            return registrationError(model, request, result.getFieldError().getDefaultMessage());
        }

//        if (!request.getPassword().equals(passwordConfirm)) {
//            return registrationError(model, request, "Passwords do not match.");
//        }

        if(!accountService.register(request)){
            return registrationError(model, request, "User with given username already exists.");
        }

        return "redirect:/login";
    }

    private String registrationError(Model model, CreateNewAccountRequest account, String message) {
        model.addAttribute("newAccount", account);
        model.addAttribute("errorMessage", message);

        return "registration-form";
    }
}
