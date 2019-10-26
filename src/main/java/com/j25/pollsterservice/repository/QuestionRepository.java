package com.j25.pollsterservice.repository;

import com.j25.pollsterservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
