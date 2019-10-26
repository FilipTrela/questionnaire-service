package com.j25.pollsterservice.repository;

import com.j25.pollsterservice.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
