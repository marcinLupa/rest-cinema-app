package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.verification_token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaVerifiactionTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
}
