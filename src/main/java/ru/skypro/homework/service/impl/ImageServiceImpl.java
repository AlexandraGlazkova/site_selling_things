package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public boolean updateImage (Integer id, MultipartFile image) {
        return false;
    }

}
