package com.j25.pollsterservice.repository;

import com.j25.pollsterservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findFirstByQuestionnaireQuestionId(Long id);

    //Question findFirstByQuestionnaireQuestionId (Long questionnaireId);
}
