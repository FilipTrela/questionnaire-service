package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.service.QuestionnarieService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/questionnaire/")
@PreAuthorize(value = "hasRole('USER')")
public class QuestionnaireController {

    private QuestionnarieService questionnarieService;


    @GetMapping("/list")
    public String listQuestionnaries(Model model, Principal principal) {
        List<Questionnaire> questionnaires = questionnarieService.findAllUserQuestionnaire(principal);
        model.addAttribute("questionnaires", questionnaires);
        return "questionnaire-list";

    }


}
