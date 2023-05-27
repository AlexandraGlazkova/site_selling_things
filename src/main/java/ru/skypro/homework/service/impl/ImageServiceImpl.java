package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile imageFile) throws IOException {

            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setFileSize(imageFile.getSize());
            image.setMediaType(imageFile.getContentType());
            image.setData(imageFile.getBytes());
            return imageRepository.save(image);
        }

    @Override
    public void removeImage (Image image) {
        imageRepository.delete(image);
    }


    @Override
    public Image getImageById(Integer id) {
        return imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException("Картинка с id " + id + " не найдена!"));
    }
}



