package com.j25.pollsterservice.mapper;

import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.model.dto.CreateQuestionnaireRequest;
import com.j25.pollsterservice.model.dto.EditQuestionnaireRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionnaireMapper {

    Questionnaire createNewQuestionnaireFormDto(CreateQuestionnaireRequest dto);

    Questionnaire mapQuestionnaireFromDto(EditQuestionnaireRequest dto);
}
