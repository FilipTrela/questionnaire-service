package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.model.dto.CreateQuestionnaireRequest;
import com.j25.pollsterservice.service.AccountService;
import com.j25.pollsterservice.service.QuestionnarieService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/questionnaire/")
@PreAuthorize(value = "hasRole('USER')")
public class QuestionnaireController {

    private final QuestionnarieService questionnarieService;
    private final AccountService accountService;


    @GetMapping("/list")
    public String listQuestionnaries(Model model, Principal principal) {
        List<Questionnaire> questionnaires = questionnarieService.findAllUserQuestionnaire(principal);
        model.addAttribute("questionnaires", questionnaires);
        if (principal != null) {
            model.addAttribute("account", accountService.findByUsername(principal.getName()).get());
        }
        return "questionnaire-list";

    }

    @GetMapping("/create")
    public String registrationForm(Model model, CreateQuestionnaireRequest request) {
        model.addAttribute("questionarry", request);

        return "questionnarie-from";
    }

    @PostMapping("/create")
    public String register(@Valid CreateQuestionnaireRequest request, Principal principal) {

        Long id = questionnarieService.add(request, principal);

        return "redirect:/question/list/" + id;
    }

    @GetMapping("/remove/{remove_quest_id}")
    public String delete(Model model,
                         @PathVariable(name = "remove_quest_id") Long questionnaireId, Principal principal) {
        questionnarieService.delete(questionnaireId, principal.getName());
        return "redirect:/questionnaire/list";
    }


}
