package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.dto.QuestionCreateRequest;
import com.j25.pollsterservice.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

//    @GetMapping("/list/{id}")
//    public String listQuestionFromQuestionnary(Model model, @PathVariable(name = "id") Long id) {
//        model.addAttribute("questions", questionService.getQuestion(id));
//
//        return "question-list";
//    }

    @GetMapping("/list/{id}")
    public String listQuestionFromQuestionnaryPagination(Model model,
                                                         @PathVariable(name = "id") Long id,
                                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Question> questionPage = questionService.getPage(id, PageRequest.of(page, size));
        model.addAttribute("questions", questionPage);
        model.addAttribute("questionnaryID", id);
        model.addAttribute("title", questionService.getQuestionnaryTitle(id));
        return "question-list";
    }

    @GetMapping("/add/{id}")
    public String addQuestionToQuestionnary(Model model, QuestionCreateRequest request, @PathVariable(name = "id") Long id) {
//        model.addAttribute("questionnaryID", id);
        request.setQuestionnarieID(id);
        model.addAttribute("request", request);

        return "question-add";
    }

    @PostMapping("/add")
    public String add(@Valid QuestionCreateRequest request) {
        questionService.addQuestion(request);
        return "redirect:/question/list/" + request.getQuestionnarieID();
    }
}
