package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/question/")
@PreAuthorize(value = "hasRole('USER')")
public class QuestionController {

    private QuestionService questionService;

//    @GetMapping("/list")
//    public String listQuestions(Model model, Principal principal) {
//        List<Question> questionnaires = questionService.findAllUserQuestion(principal);
//        model.addAttribute("questionnaires", questionnaires);
//        return "questionnaire-list";
//
//    }


    @GetMapping("/list/{id}")
    public String listQuestionFromQuestionnary(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("questions", questionService.getQuestion(id));
        return "question-list";
    }
}
