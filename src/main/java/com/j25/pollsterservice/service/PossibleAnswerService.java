package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.Answer;
import com.j25.pollsterservice.model.PossibleAnswer;
import com.j25.pollsterservice.repository.PossibleAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PossibleAnswerService {
    private final PossibleAnswerRepository passibleAnswerRepository;


    public Optional<PossibleAnswer> findById(Long answerId) {
        return passibleAnswerRepository.findById(answerId);

    }
}
