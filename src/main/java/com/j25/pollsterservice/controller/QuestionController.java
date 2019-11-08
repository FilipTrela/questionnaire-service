package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Answer;
import com.j25.pollsterservice.model.PossibleAnswer;
import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.model.dto.QuestionCreateRequest;
import com.j25.pollsterservice.service.AnswerService;
import com.j25.pollsterservice.service.QuestionService;
import com.j25.pollsterservice.service.QuestionnarieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@RequestMapping("/question/")
@PreAuthorize(value = "hasRole('USER')")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final QuestionnarieService questionnarieService;

    @GetMapping("/list/{id}")
    public String listQuestionFromQuestionnaryPagination(Model model,
                                                         @PathVariable(name = "id") Long id,
                                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "4") int size) {
        Page<Question> questionPage = questionService.getPage(id, PageRequest.of(page, size));
        Optional<Questionnaire> byId = questionnarieService.findById(id);
        byId.ifPresent(questionnaire -> model.addAttribute("questionnary", questionnaire));
        model.addAttribute("questions", questionPage);
        model.addAttribute("questionnaryID", id);
        model.addAttribute("title", questionService.getQuestionnaryTitle(id));
        return "question-list";
    }

    @GetMapping("/add/{id}")
    public String addQuestionToQuestionnary(Model model, QuestionCreateRequest request, @PathVariable(name = "id") Long id) {
        request.setQuestionnarieID(id);
        model.addAttribute("request", request);

        return "question-add";
    }

    @PostMapping("/add")
    public String add(@Valid QuestionCreateRequest request, Model model) {
        if (request.getContent().isEmpty()) {
            return createQuestionError(model, request, "Please fill question.");
        }
        if (request.getAnswer1().isEmpty()) {
            return createQuestionError(model, request, "Please type at least 2 answer");
        }
        if (request.getAnswer2().isEmpty()) {
            return createQuestionError(model, request, "Please type at least 2 answer");
        }
        questionService.addQuestion(request);
        return "redirect:/question/list/" + request.getQuestionnarieID();
    }


    private String createQuestionError(Model model, QuestionCreateRequest request, String message) {
        model.addAttribute("request", request);
        model.addAttribute("errorMessage", message);

        return "question-add";
    }


    @GetMapping("/showStats/{question_id}")
    public String showStatistic(Model model,
                                Question question,
                                @PathVariable(name = "question_id") Long questionId,
                                HttpServletRequest request) {

        Optional<Question> optionalQuestion = questionService.findById(questionId);
        List<Answer> answerList = answerService.findByQuestionId(questionId);
        if (optionalQuestion.isPresent()) {
            question = optionalQuestion.get();

            Map<String, Integer> answerCountMap = new HashMap<>();
            for (PossibleAnswer possibleAnswer : question.getPossibleAnswers()) {
                Integer countOfAnswer = answerList.stream().filter(answer -> answer.getAnswer().equals(possibleAnswer)).collect(Collectors.toList()).size();
                answerCountMap.put(possibleAnswer.getContent(), countOfAnswer);
            }

            model.addAttribute("question", question);
            model.addAttribute("answerList", answerList);
            model.addAttribute("statMap", answerCountMap);
            model.addAttribute("referer", request.getHeader("referer"));

        }

        return "question-statistic";

    }


    @GetMapping("/delete/{deleted_question_id}")
    public String deleteQuestion(Model model,
                                 @PathVariable(name = "deleted_question_id") Long questionId,
                                 Principal principal,
                                 HttpServletRequest request) {
        questionService.delete(questionId, principal.getName());

        return "redirect:" + request.getHeader("referer");
    }

}
