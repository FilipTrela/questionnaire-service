package com.j25.pollsterservice.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;


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


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private Set<PossibleAnswer> possibleAnswers;


    @ManyToOne(fetch = FetchType.LAZY)
    private Questionnaire questionnaireQuestion;

    public Set<String> getCorrectAnswers() {
        return possibleAnswers.stream()
                .filter(PossibleAnswer::getIsCorrect)
                .map(PossibleAnswer::getContent)
                .collect(Collectors.toSet());
    }

    public Set<String> getAllAnswers() {
        return possibleAnswers.stream()
                .map(PossibleAnswer::getContent)
                .collect(Collectors.toSet());
    }

    public Question(String content, QuestionType questionType, Questionnaire questionnaireQuestion) {
        this.content = content;
        this.questionType = questionType;
        this.questionnaireQuestion = questionnaireQuestion;
    }
}
