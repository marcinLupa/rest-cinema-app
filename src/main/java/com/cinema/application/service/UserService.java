package com.cinema.application.service;

import com.cinema.application.dto.UserDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.model.User;
import com.cinema.domain.repository.UserRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<UserDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE USER EXCEPTION");
        }
        return Optional.of(Mapper.fromUserToUserDTO(userRepository
                .findOne(id)
                .orElseThrow()));
    }

    public List<UserDTO> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(Mapper::fromUserToUserDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> add(UserDTO userDTO) {
        if(userDTO==null){
            throw new AppException("USER DTO IS NULL");
        }
        return Optional.of(Mapper.fromUserToUserDTO(userRepository
                .save(Mapper.fromUserDTOtoUser(userDTO))
                .orElseThrow()));
    }

    public void delete(Long id) {
        if(id==null){
            throw new AppException("USER MOVIE ID IS NULL");
        }
        userRepository.delete(id);
    }
}
