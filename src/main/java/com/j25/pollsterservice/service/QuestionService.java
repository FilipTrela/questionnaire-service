package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.Question;
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
}
