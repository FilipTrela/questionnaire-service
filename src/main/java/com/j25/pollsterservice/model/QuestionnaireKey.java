package com.j25.pollsterservice.model;

import lombok.Data;

import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;

@Data
@Entity
//@NoArgsConstructor
public class QuestionnaireKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyQuestionnaire;

    @Column(nullable = false)
    private Integer counterOfUse;

    public QuestionnaireKey(Integer counter) {
        this.keyQuestionnaire = RandomStringUtils.randomAlphanumeric(8);
        this.counterOfUse = counter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Questionnaire questionnaireKey;



}
