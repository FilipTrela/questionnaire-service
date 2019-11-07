package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.*;
import com.j25.pollsterservice.model.dto.AnswerDataRequest;
import com.j25.pollsterservice.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/public/")
public class PublicQuestionnaireController {

    private QuestionnarieService questionnarieService;
    private QuestionService questionService;
    private AnonymousUserService anonymousUserService;
    private AnswerService answerService;
    private PossibleAnswerService possibleAnswerService;
    private AccountService accountService;


//    @GetMapping("/listQuestionnaire")
//    public String listQuestionnaries(Model model, Principal principal) {
//        List<Questionnaire> questionnaires = questionnarieService.findAllPublic();
//        model.addAttribute("questionnaires", questionnaires);
//        if (principal != null) {
//            model.addAttribute("account", accountService.findByUsername(principal.getName()).get());
//        }
//        return "questionnaire-list";
//    }

    @GetMapping("/listQuestionnaire")
    public String listQuestionnariesPage(Model model,
                                     Principal principal,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Questionnaire> questionnairePage = questionnarieService.getAllPage( PageRequest.of(page, size));
        model.addAttribute("questionnaires", questionnairePage);
        if (principal != null) {
            model.addAttribute("account", accountService.findByUsername(principal.getName()).get());
        }
        model.addAttribute("publicMode", "publicMode");
        return "questionnaire-list";
    }

    @GetMapping("/fill/{questionnaire_id}")
    public String fill(Model model,
                       AnonymousUser anonymousUser,
                       @PathVariable(name = "questionnaire_id") Long questionnaireId) {
        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(questionnaireId);
        if (questionnaireOptional.isPresent()) {
            Questionnaire questionnaire = questionnaireOptional.get();
            model.addAttribute("questionnaire", questionnaire);
            return "start-filling-form";
        }
        return "redirect:/public/listQuestionnaire";

    }

    @PostMapping("/fill")
    public String fill(Model model, String yourName, Questionnaire questionnaireCheck) {
        if (nameIsEmpty(yourName)) {
            return startFillingError(model, questionnaireCheck, "Please type your name");
        }
        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(questionnaireCheck.getId());
        AnonymousUser anonymousUser = new AnonymousUser();
        if (questionnaireOptional.isPresent()) {
            Questionnaire questionnaire = questionnaireOptional.get();
            anonymousUser.setName(yourName);
            anonymousUser.setQuestionnaireUser(questionnaire);
            anonymousUser = anonymousUserService.save(anonymousUser);
        }

        return "redirect:/public/answer/" + questionnaireCheck.getId() + "/" + anonymousUser.getId() + "/1";
    }

    private String startFillingError(Model model, Questionnaire questionnaireCheck, String message) {
        model.addAttribute("questionnaire", questionnaireCheck);
        model.addAttribute("errorMessage", message);
        return "start-filling-form";
    }

    private boolean nameIsEmpty(String yourName) {
        return yourName.length() < 1;
    }


    @PostMapping("/answer")
    public String firstAnswer(@ModelAttribute AnswerDataRequest answerDataRequest) {


        Optional<Question> questionOptional = questionService.findById(answerDataRequest.getQuestionnaireQuestionIdTab()[answerDataRequest.getCounter() - 2]);
        Optional<AnonymousUser> anonymousUserOptional = anonymousUserService.findById(answerDataRequest.getAnonymousUserId());
        Optional<PossibleAnswer> possibleAnswerOptional = possibleAnswerService.findById(answerDataRequest.getAnswerId());


        if (questionOptional.isPresent() & anonymousUserOptional.isPresent() & possibleAnswerOptional.isPresent()) {
            Answer answer = new Answer();
            Question question = questionOptional.get();

            answer.setAnonymousUser(anonymousUserOptional.get());
            answer.setAnswer(possibleAnswerOptional.get());
            answer.setQuestion(question);

            answerService.save(answer);
            if (answerDataRequest.getQuestionnaireQuestionIdTab().length < answerDataRequest.getCounter()) {
                return "redirect:/public/endOfQuestionnaire/" + answerDataRequest.getAnonymousUserId();
                //return "redirect:/public/listQuestionnaire";
            }

            return "redirect:/public/answer/" +
                    answerDataRequest.getQuestionnarieId() +
                    "/" + answerDataRequest.getAnonymousUserId() +
                    "/" + answerDataRequest.getCounter();
        }

        return "redirect:/public/listQuestionnaire";

    }

    @GetMapping("/answer/{questionnaire_id}/{anonymous_user_id}/{counter}")
    public String answer(Model model,
                         Question question,
                         Answer answer,
                         @PathVariable(name = "questionnaire_id") Long questionnaireId,
                         @PathVariable(name = "anonymous_user_id") Long anonymousUserId,
                         @PathVariable(name = "counter") Integer last_counter) {

        AnswerDataRequest answerDataRequest = new AnswerDataRequest();
        answerDataRequest.setQuestionnaireQuestionIdTab(questionService.findByQuestionnarieQuestionId(questionnaireId));

        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(questionnaireId);
        Optional<AnonymousUser> anonymousUserOptional = anonymousUserService.findById(anonymousUserId);
        Optional<Question> questionOptional = questionService.findById(answerDataRequest.getQuestionnaireQuestionIdTab()[last_counter - 1]);

        if (questionnaireOptional.isPresent() & anonymousUserOptional.isPresent() & questionOptional.isPresent()) {

            question = questionOptional.get();

            answerDataRequest.setAnonymousUserId(anonymousUserId);
            answerDataRequest.setQuestionnarieId(questionnaireId);
            answerDataRequest.setQuestionId(question.getId());
            answerDataRequest.setAnswerId(answer.getId());

            answerDataRequest.setCounter(last_counter + 1);
            model.addAttribute("question", question);
            model.addAttribute("data", answerDataRequest);
            return "question-form";


        }
        return "redirect:/public/listQuestionnaire";

    }


    @GetMapping("/endOfQuestionnaire/{id}")
    public String endOfQuestionnaire(Model model, @PathVariable("id") Long id) {
        Optional<AnonymousUser> byId = anonymousUserService.findById(id);
        byId.ifPresent(anonymousUser -> model.addAttribute("name", anonymousUser.getName()));
        return "end-view";
    }


    /*

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Author> authorPage = authorService.getPage(PageRequest.of(page, size));

        model.addAttribute("authors", authorPage);
        return "author-list";
    }

    @GetMapping("/books/{id}")
    public String addAuthorsToBooks(Model model,
                                    @PathVariable("id") Long authorId) {
        Optional<Author> authorOptional = authorService.getAuthor(authorId);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();

            List<Book> books = bookService.getAll();

            model.addAttribute("author", author);
            model.addAttribute("books", books);

            return "author-bookform";
        }

        return "redirect:/author/list";
    }

    @GetMapping("/book/remove/{bookid}/{authorid}")
    public String removeBookFromAuthor(@PathVariable("bookid") Long bookid,
                                       @PathVariable("authorid") Long authorid,
                                       HttpServletRequest request){
        authorService.removeBookFromAuthor(authorid, bookid);

        return "redirect:" + request.getHeader("referer");
    }
    */
}
