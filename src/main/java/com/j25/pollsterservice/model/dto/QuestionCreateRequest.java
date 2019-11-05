package com.j25.pollsterservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateRequest {
    @NotNull
    String content;
    @NotNull
    Long questionnarieID;

    String answer1;
    Boolean answer1Correct;
    String answer2;
    Boolean answer2Correct;
    String answer3;
    Boolean answer3Correct;
    String answer4;
    Boolean answer4Correct;

}
