package com.j25.pollsterservice.repository;

import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.model.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {




    List<Questionnaire> findAllByIsPrivateFalse();

    Page<Questionnaire> findByAccount(Account account, PageRequest of);

    Page<Questionnaire> findAllByAccount(Account account, Pageable of);


//    List<Questionnaire> findAllByIsPrivateFalseaAndQuestionSetGreaterThan1();
}


