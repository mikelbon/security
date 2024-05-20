package com.uncledave.springsecurity.repositories;

import com.uncledave.springsecurity.model.Authority;
import com.uncledave.springsecurity.utils.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityName name);
}
