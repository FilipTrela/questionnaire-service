package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.Question;
import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.repository.QuestionRepository;
import com.j25.pollsterservice.repository.QuestionnaireRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionnaireRepository questionnaireRepository;

    public Set<Question> getQuestion(Long id) {
        return questionnaireRepository.getOne(id).getQuestionSet();
//    public Question findFiresByQuestionnarieId(Long questionnaireId) {
//        return questionRepository.findFirstByQuestionnaireQuestionId(questionnaireId);
//    }
    }

    public Optional<Question> findFiresByQuestionnarieQuestId(Long id) {
        return questionRepository.findFirstByQuestionnaireQuestionId(id);

    }

    public Optional<Question> findById(Long last_question_id) {
        return questionRepository.findById(last_question_id);

    }


    public Optional<Question> findNextQuestion(Questionnaire questionnaire, Long last_question_id) {

        return questionRepository.findQuestionByQuestionnaireQuestionAndIdAfter(questionnaire, last_question_id);
    }


    public Page<Question> getPage(Long id, PageRequest of) {
//        Set<Question> questionSet = questionnaireRepository.getOne(id).getQuestionSet();
      return questionRepository.findAllByQuestionnaireQuestionId(id, of);
    }

    public Long[] findByQuestionnarieQuestionId(Long questionnaireId) {
        List<Question> questionList = questionRepository.findQuestionByQuestionnaireQuestionId(questionnaireId);
        Long[] questionIdTab = new Long[questionList.size()];
        for (int i = 0; i < questionList.size(); i++) {
            questionIdTab[i]=questionList.get(i).getId();
        }
        return questionIdTab;
    }
}
