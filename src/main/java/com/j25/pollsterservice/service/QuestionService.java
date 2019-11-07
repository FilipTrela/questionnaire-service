package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.*;
import com.j25.pollsterservice.model.dto.QuestionCreateRequest;
import com.j25.pollsterservice.repository.AccountRepository;
import com.j25.pollsterservice.repository.PossibleAnswerRepository;
import com.j25.pollsterservice.repository.QuestionRepository;
import com.j25.pollsterservice.repository.QuestionnaireRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final PossibleAnswerRepository possibleAnswerRepository;
    private final AccountRepository accountRepository;

    public Set<Question> getQuestion(Long id) {
        return questionnaireRepository.getOne(id).getQuestionSet();
//    public Question findFiresByQuestionnarieId(Long questionnaireId) {
//        return questionRepository.findFirstByQuestionnaireQuestionId(questionnaireId);
//    }
    }

    public Optional<Question> findFiresByQuestionnarieQuestId(Long id) {
        return questionRepository.findFirstByQuestionnaireQuestionId(id);

    }

    public Optional<Question> findById(Long last_question_id) {
        return questionRepository.findById(last_question_id);

    }


    public Optional<Question> findNextQuestion(Questionnaire questionnaire, Long last_question_id) {

        return questionRepository.findQuestionByQuestionnaireQuestionAndIdAfter(questionnaire, last_question_id);
    }


    public Page<Question> getPage(Long id, PageRequest of) {
//        Set<Question> questionSet = questionnaireRepository.getOne(id).getQuestionSet();
        return questionRepository.findAllByQuestionnaireQuestionId(id, of);
    }

    public Long[] findByQuestionnarieQuestionId(Long questionnaireId) {
        List<Question> questionList = questionRepository.findQuestionByQuestionnaireQuestionId(questionnaireId);
        Long[] questionIdTab = new Long[questionList.size()];
        for (int i = 0; i < questionList.size(); i++) {
            questionIdTab[i] = questionList.get(i).getId();
        }
        return questionIdTab;
    }

    public String getQuestionnaryTitle(Long id) {
        return questionnaireRepository.getOne(id).getTitle();
    }

    public void addQuestion(QuestionCreateRequest request) {
        createQuestionFromRequest(request);
    }

    private void createQuestionFromRequest(QuestionCreateRequest request) {
        Questionnaire questionnaire = questionnaireRepository.getOne(request.getQuestionnarieID());
        Question question = questionRepository.save(new Question(request.getContent(), QuestionType.CLOSE, questionnaire));
        possibleAnswerRepository.save(new PossibleAnswer(request.getAnswer1Correct(), request.getAnswer1(), question));
        possibleAnswerRepository.save(new PossibleAnswer(request.getAnswer2Correct(), request.getAnswer2(), question));
        if (!request.getAnswer3().isEmpty()) {
            possibleAnswerRepository.save(new PossibleAnswer(request.getAnswer3Correct(), request.getAnswer3(), question));
        }
        if (!request.getAnswer4().isEmpty()) {
            possibleAnswerRepository.save(new PossibleAnswer(request.getAnswer4Correct(), request.getAnswer4(), question));
        }
    }

    public void delete(Long questionId, String name) {
        Optional<Account> accountOptional = accountRepository.findByUsername(name);
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(accountOptional.isPresent()& questionOptional.isPresent()){
            if(questionOptional.get().getQuestionnaireQuestion().getAccount().equals(accountOptional.get())){
                questionRepository.delete(questionOptional.get());
            }
        }
    }


}
