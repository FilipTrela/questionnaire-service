package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.service.QuestionnarieService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/public/")
public class PublicQuestionnaireController {

    private QuestionnarieService questionnarieService;


    @GetMapping("/listQuestionnaire")
    public String listQuestionnaries(Model model) {
        List<Questionnaire> questionnaires = questionnarieService.findAllPublic();
        model.addAttribute("questionnaires", questionnaires);
        return "questionnaire-list";

    }





}
