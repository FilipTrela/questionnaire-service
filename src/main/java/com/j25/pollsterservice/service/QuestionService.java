package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.repository.QuestionRepository;
import com.j25.pollsterservice.repository.QuestionnaireRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionnaireRepository questionnaireRepository;

    public Set<Question> getQuestion(Long id) {
        return questionnaireRepository.getOne(id).getQuestionSet();
    }
}
