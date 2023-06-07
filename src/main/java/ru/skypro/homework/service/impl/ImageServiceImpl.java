package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;

import static ru.skypro.homework.constant.error.IMAGE_BY_ID_NOT_FOUND;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    /**
     * Метод загрузки картинок/фото
     * * @param imageFile
     */
    @Override
    public Image uploadImage(MultipartFile imageFile) throws IOException {

            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setFileSize(imageFile.getSize());
            image.setFilePath(imageFile.getOriginalFilename());
            image.setMediaType(imageFile.getContentType());
            image.setData(imageFile.getBytes());

            return imageRepository.save(image);
        }
    /**
     * Метод загрузки аватарок
     * * @param imageFile -фото
     * * @param user - пользователь
     */
    @Override
    public Image uploadImageForUser(MultipartFile imageFile, User user) throws IOException {

        Image image = new Image();
        image.setData(imageFile.getBytes());
        image.setFileSize(imageFile.getSize());
        image.setFilePath(imageFile.getOriginalFilename());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        image.setUser(user);
        return imageRepository.save(image);
    }
    /**
     * Метод удаления фото
     * * @param imageFile
     */
    @Override
    public void removeImage (Image image) {
        imageRepository.delete(image);
    }
    /**
     * Метод получения картинкинки по id
     * * @param id
     */

    @Override
    public Image getImageById(Integer id) {
        return imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException(IMAGE_BY_ID_NOT_FOUND.formatted(id)));
    }
}



