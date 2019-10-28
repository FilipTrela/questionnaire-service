package com.j25.pollsterservice.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AnonymousUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime answerDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Questionnaire questionnaireUser;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "anonymousUser", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<Answer> answerSet;


    @ManyToOne(fetch = FetchType.LAZY)
    private Account accountAnonymous;
}
