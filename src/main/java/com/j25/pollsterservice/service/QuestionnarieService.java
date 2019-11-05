package com.j25.pollsterservice.service;

import com.j25.pollsterservice.mapper.QuestionnaireMapper;
import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.model.Questionnaire;
import com.j25.pollsterservice.model.dto.CreateQuestionnaireRequest;
import com.j25.pollsterservice.repository.AccountRepository;
import com.j25.pollsterservice.repository.QuestionnaireRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionnarieService {
    private final QuestionnaireRepository questionnaireRepository;
    private final AccountRepository accountRepository;
    private final QuestionnaireMapper questionnaireMapper;


    public List<Questionnaire> findAllUserQuestionnaire(Principal principal) {
        List<Questionnaire> questionnaireList = new LinkedList<>();
        Optional<Account> accountOptional = accountRepository.findByUsername(principal.getName());
        if (!accountOptional.isPresent()) {
            Account account = accountOptional.get();
            questionnaireList = questionnaireRepository.findAllByAccount(account);
        }
        return questionnaireList;
    }

    public Long add(CreateQuestionnaireRequest request, Principal principal) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());
        if (optionalAccount.isPresent()) {
            Questionnaire newQuestionnaireFormDto = questionnaireMapper.createNewQuestionnaireFormDto(request);
            newQuestionnaireFormDto.setAccount(optionalAccount.get());
            return questionnaireRepository.save(newQuestionnaireFormDto).getId();
        }
        throw new EntityNotFoundException("no user");
    }

    public List<Questionnaire> findAllPublic() {
        return questionnaireRepository.findAllByIsPrivateFalse();
    }

    public void delete(Long questionnaireId, String name) {
        Optional<Account> accountOptional = accountRepository.findByUsername(name);
        if (!accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (questionnaireRepository.findById(questionnaireId).get().getAccount().equals(account)) {
                questionnaireRepository.deleteById(questionnaireId);
            }

        }
    }
//
//    public Questionnaire findById(Long quedtionnaireId) {
//        Optional<Questionnaire> questionnaireOptional =  questionnaireRepository.findById(quedtionnaireId);
//        if(questionnaireOptional.isPresent()){
//            return questionnaireOptional.get();
//        }
//        throw new EntityNotFoundException("no such entity");
//    }

    public Optional<Questionnaire> findById(Long questionnaireId) {

        return questionnaireRepository.findById(questionnaireId);
    }
}
