package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.service.QuestionService;
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
@RequestMapping("/question/")
@PreAuthorize(value = "hasRole('USER')")
public class QuestionController {

    private QuestionService questionService;
//
//    @GetMapping("/list")
//    public String listQuestions(Model model, Principal principal) {
//        List<Question> questionnaires = questionService.findAllUserQuestion(principal);
//        model.addAttribute("questionnaires", questionnaires);
//        return "questionnaire-list";
//
//    }


}
