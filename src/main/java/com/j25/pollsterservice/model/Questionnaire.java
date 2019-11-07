package com.j25.pollsterservice.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime addingDate;

    private LocalDateTime endingDate;

    @NotNull
    private Boolean isPrivate;



    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "questionnaireKey", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<QuestionnaireKey> questionnaireKeySet;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "questionnaireQuestion", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<Question> questionSet;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "questionnaireUser", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<AnonymousUser> anonymousUserSet;

}
