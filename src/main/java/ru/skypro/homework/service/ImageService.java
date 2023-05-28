package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile image) throws IOException;
    Image uploadImageForUser(MultipartFile image, User user) throws IOException;
    void removeImage (Image image);
    Image getImageById(Integer id);

}
