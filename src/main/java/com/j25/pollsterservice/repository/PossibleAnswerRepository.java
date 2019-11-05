package com.j25.pollsterservice.repository;


import com.j25.pollsterservice.model.PossibleAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossibleAnswerRepository extends JpaRepository<PossibleAnswer, Long> {

}
