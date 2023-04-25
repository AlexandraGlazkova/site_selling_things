package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
