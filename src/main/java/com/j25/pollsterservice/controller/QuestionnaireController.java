package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.model.dto.CreateQuestionnaireRequest;
import com.j25.pollsterservice.model.dto.EditQuestionnaireRequest;
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
import java.util.Optional;

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

    @GetMapping("/create")
    public String registrationForm(Model model, Questionnaire questionnaire) {
        model.addAttribute("questionarry", questionnaire);

        return "questionnarie-from";
    }

    @PostMapping("/create")
    public String register(Questionnaire questionnaire, Principal principal) {

        Long id = questionnarieService.add(questionnaire, principal);

        return "redirect:/question/list/" + id;
    }

    @GetMapping("/edit/{questionnaire_id}")
    public String editQuestionnaire(Model model,
                                    @PathVariable(name = "questionnaire_id") Long id,
                                    EditQuestionnaireRequest request){
        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(id);
        questionnaireOptional.ifPresent(questionnaire -> model.addAttribute("questionarry", questionnaire));

        return "questionnarie-from";
    }



    @GetMapping("/remove/{remove_quest_id}")
    public String delete(Model model,
                         @PathVariable(name = "remove_quest_id") Long questionnaireId, Principal principal) {
        questionnarieService.delete(questionnaireId, principal.getName());
        return "redirect:/questionnaire/list";
    }


}
