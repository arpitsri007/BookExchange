package com.intuit.bookexchange.service;


import com.intuit.bookexchange.entity.UserEntity;
import com.intuit.bookexchange.model.User;
import com.intuit.bookexchange.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExchangeValidationService {

    @Autowired
    private IUserRepository userRepository;

    public Boolean isValidEmail(String emailId) {

        Optional<UserEntity> user = userRepository.findByUserEmail(emailId);
        return user.isPresent();
    }
}
