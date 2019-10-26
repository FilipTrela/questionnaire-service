package com.j25.pollsterservice.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QuestionType questionType;

    @Lob
    private Lob image;


    private String correctAnswers;


    private String incorrectAnswers;


    private String passibleAnswers;


    @ManyToOne(fetch = FetchType.LAZY)
    private Questionnaire questionnaireQuestion;




}
