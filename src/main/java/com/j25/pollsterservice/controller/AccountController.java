package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.model.dto.CreateNewAccountRequest;
import com.j25.pollsterservice.model.dto.EditAccountRequest;
import com.j25.pollsterservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;


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

        if (result.hasErrors()) {
            return registrationError(model, request, result.getFieldError().getDefaultMessage());
        }

//        if (!request.getPassword().equals(passwordConfirm)) {
//            return registrationError(model, request, "Passwords do not match.");
//        }

        if (!accountService.register(request)) {
            return registrationError(model, request, "User with given username already exists.");
        }

        return "redirect:/login";
    }

    private String registrationError(Model model, CreateNewAccountRequest account, String message) {
        model.addAttribute("newAccount", account);
        model.addAttribute("errorMessage", message);

        return "registration-form";
    }

    @GetMapping("/edit")
    public String editForm(Model model, EditAccountRequest request, Principal principal) {
        Optional<Account> accountOptional = accountService.findByUsername(principal.getName());
        if(accountOptional.isPresent()) {
            Account account = accountOptional.get();
            request.setName(account.getName());
            request.setPhone(account.getPhone());
            request.setSurname((account.getSurname()));
            request.setUserEmail(account.getUserEmail());

            model.addAttribute("editAccount", request);
            model.addAttribute("principal", principal);
        }

        return "account-edit-form";
    }

    @PostMapping("/edit")
    public String editAccount(@Valid EditAccountRequest request,
                           BindingResult result,
                           Principal principal,
                           Model model) {

        if (result.hasErrors()) {
            return editingError(model, request, result.getFieldError().getDefaultMessage());
        }


        if (!accountService.edit(request, principal)) {
            return editingError(model, request, "Something went wrong");
        }

        return "redirect:/";
    }
    private String editingError(Model model, EditAccountRequest account, String message) {
        model.addAttribute("newAccount", account);
        model.addAttribute("errorMessage", message);

        return "registration-form";
    }

}

