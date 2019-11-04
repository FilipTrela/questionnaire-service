package com.j25.pollsterservice.controller;

import com.j25.pollsterservice.model.AnonymousUser;
import com.j25.pollsterservice.model.Answer;
import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.model.dto.AnswerDataRequest;
import com.j25.pollsterservice.service.AnonymousUserService;
import com.j25.pollsterservice.service.AnswerService;
import com.j25.pollsterservice.service.QuestionService;
import com.j25.pollsterservice.service.QuestionnarieService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping("/listQuestionnaire")
    public String listQuestionnaries(Model model) {
        List<Questionnaire> questionnaires = questionnarieService.findAllPublic();
        model.addAttribute("questionnaires", questionnaires);
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
    public String fill(String yourName, Questionnaire questionnaireCheck) {
        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(questionnaireCheck.getId());
        AnonymousUser anonymousUser = new AnonymousUser();
        if (questionnaireOptional.isPresent()) {
            Questionnaire questionnaire = questionnaireOptional.get();
            anonymousUser.setName(yourName);
            anonymousUser.setQuestionnaireUser(questionnaire);
            anonymousUser = anonymousUserService.save(anonymousUser);
        }

        return "redirect:/public/answer/" + questionnaireCheck.getId() + "/" + anonymousUser.getId();
    }


    @GetMapping("/answer/{questionnaire_id}/{anonymous_user_id}")
    public String firstAnswer(Model model,
                              Question question,
                              Answer answer,
                              @PathVariable(name = "questionnaire_id") Long questionnaireId,
                              @PathVariable(name = "anonymous_user_id") Long anonymousUserId) {
        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(questionnaireId);
        Optional<AnonymousUser> anonymousUserOptional = anonymousUserService.findById(anonymousUserId);

        if (questionnaireOptional.isPresent() & anonymousUserOptional.isPresent()) {
            Questionnaire questionnaire = questionnaireOptional.get();
            Optional<Question> questionOptional = questionService.findFiresByQuestionnarieQuestId(questionnaire.getId());
            if(questionOptional.isPresent()){
                question=questionOptional.get();
                AnswerDataRequest answerDataRequest = new AnswerDataRequest();
                answerDataRequest.setAnonymousUserId(anonymousUserId);
                answerDataRequest.setQuestionnarieId(questionnaireId);
                answerDataRequest.setQuestion(question);
                answerDataRequest.setAnswer(answer);
                answerDataRequest.setCounter(1);
                model.addAttribute("data", answerDataRequest);
                return "question-form";
            }

        }
        return "redirect:/public/listQuestionnaire";

    }

    @PostMapping("/answer")
    public String firstAnswer(@ModelAttribute("data") AnswerDataRequest data, Model model ) {
        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(data.getQuestionnarieId());
        Optional<AnonymousUser> anonymousUserOptional = anonymousUserService.findById(data.getAnonymousUserId());

        if (questionnaireOptional.isPresent() & anonymousUserOptional.isPresent()) {
            Answer answer = data.getAnswer();
            answer.setAnonymousUser(anonymousUserOptional.get());
            answer.setContent(data.getChoosenAnswer());
            answer.setQuestion(data.getQuestion());

            answerService.save(answer);

        }

        return "redirect:/public/listQuestionnaire";
    }

    @GetMapping("/answer/{questionnaire_id}/{anonymous_user_id}/{last_question_id}")
    public String answer(Model model,
                              Question question,
                              Answer answer,
                              @PathVariable(name = "questionnaire_id") Long questionnaireId,
                              @PathVariable(name = "anonymous_user_id") Long anonymousUserId,
                              @PathVariable(name = "last_question_id") Long last_question_id) {
        Optional<Questionnaire> questionnaireOptional = questionnarieService.findById(questionnaireId);
        Optional<AnonymousUser> anonymousUserOptional = anonymousUserService.findById(anonymousUserId);
        Optional<Question> questionOptional = questionService.findById(last_question_id);

        if (questionnaireOptional.isPresent() & anonymousUserOptional.isPresent() & questionOptional.isPresent()) {
            Questionnaire questionnaire = questionnaireOptional.get();
            Optional<Question> nextQuestionOptional = questionService.findNextQuestion(questionnaire, last_question_id);
            if(nextQuestionOptional.isPresent()){
                question=nextQuestionOptional.get();
                AnswerDataRequest answerDataRequest = new AnswerDataRequest();
                answerDataRequest.setAnonymousUserId(anonymousUserId);
                answerDataRequest.setQuestionnarieId(questionnaireId);
                answerDataRequest.setQuestion(question);
                answerDataRequest.setAnswer(answer);
                answerDataRequest.setCounter(1);
                model.addAttribute("data", answerDataRequest);
                return "question-form";
            }

        }
        return "redirect:/public/listQuestionnaire";

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
