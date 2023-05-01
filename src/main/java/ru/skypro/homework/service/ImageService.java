package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    boolean updateImage(Integer id, MultipartFile image);
}
