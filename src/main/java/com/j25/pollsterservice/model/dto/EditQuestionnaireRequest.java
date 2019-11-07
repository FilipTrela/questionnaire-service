package com.j25.pollsterservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditQuestionnaireRequest {
    @NotNull
    Long id;

    @NotNull
    String title;

    @NotNull
    String description;

    LocalDateTime endingDate;


    Boolean isPrivate;

}
