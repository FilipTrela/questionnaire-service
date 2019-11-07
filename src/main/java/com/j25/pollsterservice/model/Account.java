package com.j25.pollsterservice.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    //Account part

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4)
    private String username;

    @NotEmpty
    @Size(min = 4)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<AccountRole> accountRoles;

    private boolean locked;

    //User part
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String phone;

    @Lob
    private Lob avatar;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private Set<Questionnaire> questionnaireSet;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "accountAnonymous", fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<AnonymousUser> anonymousUserSet;

    public boolean isAdmin() {
        return accountRoles.stream()
                .anyMatch(accountRole -> accountRole.getName().equals("ADMIN"));
    }

    public boolean canOpen(Questionnaire questionnaire){
        return questionnaireSet.contains(questionnaire);
    }
}
