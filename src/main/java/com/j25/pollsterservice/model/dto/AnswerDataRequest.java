package com.j25.pollsterservice.model.dto;

import com.j25.pollsterservice.model.Answer;
import com.j25.pollsterservice.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDataRequest {
    private Question question;
    private Answer answer;
    private Long questionnarieId;
    private Long anonymousUserId;
    private Integer counter;




}
