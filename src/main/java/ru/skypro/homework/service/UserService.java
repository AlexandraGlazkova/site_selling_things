package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import java.io.IOException;

public interface UserService {
    void setPassword(String currentPassword, String newPassword, Authentication authentication);

    User getUser(Authentication authentication);

    User updateUser(UserDto userDto, Authentication authentication);

    void updateUserImage(MultipartFile image, Authentication authentication) throws IOException;
}
