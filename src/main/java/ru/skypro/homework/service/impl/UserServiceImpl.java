package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public boolean setPassword(String currentPassword, String newPassword) {
        return false;
    }

    @Override
    public User getUser() {
        return null;

    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean updateUserImage(MultipartFile image) {
        return false;
    }
}
