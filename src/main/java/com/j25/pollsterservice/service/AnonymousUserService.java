package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.AnonymousUser;
import com.j25.pollsterservice.repository.AnonymousUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AnonymousUserService {
    private final AnonymousUserRepository anonymousUserRepository;


    public AnonymousUser save(AnonymousUser anonymousUser) {
        return anonymousUserRepository.save(anonymousUser);
    }


    public Optional<AnonymousUser> findById(Long anonymousUserId) {
        return anonymousUserRepository.findById(anonymousUserId);
    }
}
