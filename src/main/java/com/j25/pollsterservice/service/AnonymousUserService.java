package com.j25.pollsterservice.service;

import com.j25.pollsterservice.repository.AnonymousUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnonymousUserService {
    private final AnonymousUserRepository anonymousUserRepository;

}
