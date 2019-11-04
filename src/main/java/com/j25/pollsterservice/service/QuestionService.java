package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

//    public Question findFiresByQuestionnarieId(Long questionnaireId) {
//        return questionRepository.findFirstByQuestionnaireQuestionId(questionnaireId);
//    }

    public Optional<Question> findFiresByQuestionnarieQuestId(Long id) {
        return questionRepository.findFirstByQuestionnaireQuestionId(id);

    }

    public Optional<Question> findById(Long last_question_id) {
        return questionRepository.findById(last_question_id);

    }



    public Optional<Question> findNextQuestion(Questionnaire questionnaire, Long last_question_id) {

        return questionRepository.findQuestionByQuestionnaireQuestionAndIdAfter(questionnaire, last_question_id);
    }
}
