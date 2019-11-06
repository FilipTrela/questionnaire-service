package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Answer;
import com.j25.pollsterservice.model.PossibleAnswer;
import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.dto.QuestionCreateRequest;
import com.j25.pollsterservice.service.AnswerService;
import com.j25.pollsterservice.service.QuestionService;
import lombok.AllArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    private QuestionService questionService;
    private AnswerService answerService;

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


    @GetMapping("/showStats/{question_id}")
    public String showStatistic(Model model, Question question, @PathVariable(name = "question_id") Long questionId) {

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
        }

        return "question-statistic";

    }


    @GetMapping("/showStats")
    public String showStatistic() {
        return "question-statistic";

    }

}
