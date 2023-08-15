package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import java.io.IOException;


import static ru.skypro.homework.constant.error.USER_NOT_FOUND_EMAIL;
import static ru.skypro.homework.constant.error.WRONG_PASS_MSG;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Смена пароля
     * @param newPassword новый пароль
     * @param  authentication авторизированный пользователь
     */
    @Override
    public User setPassword(NewPassword newPassword,Authentication authentication) throws IOException{
        User user = findUserByUsername(authentication.getName());
        String encryptedPassword = user.getPassword();
        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), encryptedPassword)) {
            throw new IncorrectPasswordException(WRONG_PASS_MSG.formatted(authentication.getName()));
        }
        String newPasswordUser = newPassword.getNewPassword();
        String encodedPassword = passwordEncoder.encode(newPasswordUser);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }
    /**
     * Получение пользователя
     * @param  authentication авторизированный пользователь
     */
    @Override
    public User getUser(Authentication authentication) {
        return findUserByUsername(authentication.getName());
    }
    /**
     * Редактирование пользователя
     * @param userDto
     * @param  authentication авторизированный пользователь
     */
    @Override
    public User updateUser(UserDto userDto, Authentication authentication) {
        User user = findUserByUsername(authentication.getName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        return userRepository.save(user);
    }
    /**
     * Изменение аватарки пользователя
     * @param image выбор аватарки
     * @param  authentication авторизированный пользователь
     */
    @Override
    public void updateUserImage(MultipartFile image, Authentication authentication) throws IOException {

        User user = findUserByUsername(authentication.getName());
        if (user.getImage() != null) {
            imageService.removeImage(user.getImage());
        }
        user.setImage(imageService.uploadImageForUser(image, user));
        userRepository.save(user);

    }
    /**
     * Поиск пользователя по email
     * @param username email
     */
    private User findUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EMAIL.formatted(username)));
    }


}