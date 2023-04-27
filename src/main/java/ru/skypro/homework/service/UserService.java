package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    boolean setPassword(String currentPassword, String newPassword);
    User getUser();
    boolean updateUser(User user);
    boolean updateUserImage(MultipartFile image);
}
