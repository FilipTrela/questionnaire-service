package com.j25.pollsterservice.repository;

import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findFirstByQuestionnaireQuestionId(Long id);


    Optional<Question> findQuestionByQuestionnaireQuestionAndIdAfter(Questionnaire questionnaire, Long last_question_id);


    Page<Question> findAllByQuestionnaireQuestionId(Long questionnaire, Pageable pageable);
    List<Question> findQuestionByQuestionnaireQuestionId(Long questionnaireId);

    //Question findFirstByQuestionnaireQuestionId (Long questionnaireId);
}
