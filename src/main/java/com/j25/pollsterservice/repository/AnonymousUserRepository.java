package com.j25.pollsterservice.repository;

import com.j25.pollsterservice.model.AnonymousUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousUserRepository extends JpaRepository<AnonymousUser, Long> {

}
