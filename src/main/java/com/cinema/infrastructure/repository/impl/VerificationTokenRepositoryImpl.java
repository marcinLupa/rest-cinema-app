package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.verification_token.VerificationToken;
import com.cinema.domain.model.verification_token.VerificationTokenRepository;
import com.cinema.infrastructure.repository.jpa.JpaVerifiactionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {
    private final JpaVerifiactionTokenRepository jpaVerifiactionTokenRepository;

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return jpaVerifiactionTokenRepository.findByToken(token);
    }

    @Override
    public Optional<VerificationToken> findOne(Long id) {
        return jpaVerifiactionTokenRepository.findById(id);
    }

    @Override
    public List<VerificationToken> findAll() {
        return jpaVerifiactionTokenRepository.findAll();
    }

    @Override
    public Optional<VerificationToken> save(VerificationToken verificationToken) {
        return Optional.of(jpaVerifiactionTokenRepository.save(verificationToken));
    }

    @Override
    public void delete(Long id) {
        jpaVerifiactionTokenRepository.deleteById(id);
    }
}
