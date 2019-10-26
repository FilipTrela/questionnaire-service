package com.j25.pollsterservice.service;

import com.j25.pollsterservice.repository.QuestionnaireKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuesitonnaireKeyService {
    private final QuestionnaireKeyRepository questionnaireKeyRepository;

}
