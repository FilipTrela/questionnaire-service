package com.j25.pollsterservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PossibleAnswer answer ;


    @ManyToOne(fetch = FetchType.LAZY)
    private AnonymousUser anonymousUser;


    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;


}
