package com.j25.pollsterservice.service;

import com.j25.pollsterservice.repository.QuestionnaireRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionnarieService {
    private final QuestionnaireRepository questionnaireRepository;

}
