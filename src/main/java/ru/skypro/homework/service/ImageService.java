package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile image) throws IOException;

    void removeImage (Image image);
    Image getImageById(Integer id);

}
