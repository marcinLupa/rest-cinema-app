package com.cinema.domain.model.verification_token;

import com.cinema.domain.repository.generic.GenericRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends GenericRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
