package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void setPassword(String currentPassword, String newPassword, Authentication authentication) {
        User user = findUserByUsername(authentication.getName());
        if (!passwordEncoder.matches(currentPassword, (String) authentication.getCredentials())) {
            throw new IncorrectPasswordException("Введен неверный пароль");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    @Override
    public User getUser(Authentication authentication) {
        return findUserByUsername(authentication.getName());
    }

    @Override
    public User updateUser(UserDto userDto, Authentication authentication) {
        User user = findUserByUsername(authentication.getName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        return userRepository.save(user);
    }

    @Override
    public void updateUserImage(MultipartFile image, Authentication authentication) throws IOException {

        User user = findUserByUsername(authentication.getName());
        if (user.getImage() != null) {
            imageService.removeImage(user.getImage());
        }
        user.setImage(imageService.uploadImage(image));

    }

    private User findUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException("Пользователь с e-mail" + username + "не найден"));
    }

}

