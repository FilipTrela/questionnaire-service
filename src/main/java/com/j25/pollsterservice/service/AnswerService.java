package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.Answer;
import com.j25.pollsterservice.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Answer> findByQuestionId(Long questionId) {

        return answerRepository.findByQuestionId(questionId);
    }
}
