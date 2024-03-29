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
        if (usernameIsEmpty(request)) {
            return registrationError(model, request, "Your username can't be empty.");
        }
        if (passwordIsEmpty(request)) {
            return registrationError(model, request, "Your password can't be empty.");
        }
        if (!passwordGrantStantard(request.getPassword())) {
            return registrationError(model, request, "Password must have at least one big letter and one number.");
        }
        if (emailIsEmpty(request)) {
            return registrationError(model, request, "You must type email.");


        }
        if (numberIsEmpty(request)) {
            return registrationError(model, request, "You must type phone number.");
        }
        if (nameIsEmpty(request)) {
            return registrationError(model, request, "You must type your name.");
        }
        if (surnameIsEmpty(request)) {
            return registrationError(model, request, "You must type your surname.");
        }
        if (stringContainsNumber(request.getSurname())) {
            return registrationError(model, request, "You cant type number in your surname.");
        }
        if (stringContainsNumber(request.getName())) {
            return registrationError(model, request, "You cant type number in your name.");
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


    private boolean passwordGrantStantard(String password) {
        boolean isGood = true;
        if (!stringContainsNumber(password)) {
            isGood = false;
        }
        if (!haveABigLetter(password)) {
            isGood = false;
        }
        return isGood;
    }

    private boolean haveABigLetter(String password) {
        boolean containsBigLetter = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsBigLetter = true;
            }
        }
        return containsBigLetter;
    }

    private boolean stringContainsNumber(String string) {
        boolean containsDigit = false;
        for (char c : string.toCharArray()) {
            if (containsDigit = Character.isDigit(c)) {
                break;
            }
        }
        return containsDigit;
    }

    private boolean usernameIsEmpty(CreateNewAccountRequest request) {
        return request.getUsername().length() < 1;
    }

    private boolean nameIsEmpty(CreateNewAccountRequest request) {
        return request.getName().length() < 1;
    }

    private boolean surnameIsEmpty(CreateNewAccountRequest request) {
        return request.getSurname().length() < 1;
    }

    private boolean emailIsEmpty(CreateNewAccountRequest request) {
        return request.getUserEmail().length() < 1;
    }

    private boolean passwordIsEmpty(CreateNewAccountRequest request) {
        return request.getPassword().length() < 1;
    }

    private boolean numberIsEmpty(CreateNewAccountRequest request) {
        return request.getPhone().length() < 1;
    }

    @GetMapping("/edit")
    public String editForm(Model model, EditAccountRequest request, Principal principal) {
        Optional<Account> accountOptional = accountService.findByUsername(principal.getName());
        if (accountOptional.isPresent()) {
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
            return editingError(model, request, principal, result.getFieldError().getDefaultMessage());
        }
        if (request.getUserEmail().isEmpty()) {
            return editingError(model, request, principal, "Please type email");
        }
        if (request.getPhone().isEmpty()) {
            return editingError(model, request, principal, "Please type phone number");
        }
        if (request.getName().isEmpty()) {
            return editingError(model, request, principal, "Please type name");
        }
        if (request.getSurname().isEmpty()) {
            return editingError(model, request, principal, "Please type surname");
        }
        if (!accountService.edit(request, principal)) {
            return editingError(model, request, principal, "Something went wrong");
        }

        return "redirect:/";
    }

    private String editingError(Model model, EditAccountRequest request, Principal principal, String message) {
        model.addAttribute("editAccount", request);
        model.addAttribute("principal", principal);
        model.addAttribute("errorMessage", message);

        return "account-edit-form";

    }

}

