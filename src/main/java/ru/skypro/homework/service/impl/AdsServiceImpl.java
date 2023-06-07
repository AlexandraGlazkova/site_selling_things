package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdsMapperInterface;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.skypro.homework.constant.error.*;

@Service
@RequiredArgsConstructor

public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    /**
     * Добавление объявления авторизированным пользователем
     * * @param createAds
     * * @param image
     * * @param authentication
     */

    @Override
    public Ads addAd(CreateAds createAds, MultipartFile image, Authentication authentication) throws IOException {
        User user = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EMAIL.formatted(authentication.getName())));

        Ads ads = AdsMapperInterface.INSTANCE.toEntity(createAds);
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(image));
        return adsRepository.save(ads);
    }
    /**
     * Удаление объявления авторизированным пользователем
     * * @param id
     * * @param authentication
     */
    @Override
    public Ads removeAd(Integer id, Authentication authentication) {
        Ads ads = findAdsById(id);
        checkPermissionsToWorkWithAds (ads, authentication);
        imageRepository.deleteImageByAdsId(id);
        adsRepository.delete(ads);

        return ads;
    }

    /**
     * Получение всех объявлений
     */
    @Override
    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
    }
    /**
     * Получение объявления по id
     * * @param id
     * * @param authentication
     */

    @Override
    public Ads getAds(Integer id, Authentication authentication) {
        Ads ads = findAdsById(id);
        return ads;
    }

    /**
     * Изменение объявления авторизированным пользователем
     * * @param id
     * * @param createAds
     * * @param authentication
     */
    @Override
    public Ads updateAds(Integer id, CreateAds createAds, Authentication authentication) {
        Ads ads = findAdsById(id);
        checkPermissionsToWorkWithAds (ads, authentication);
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        ads.setTitle(createAds.getTitle());
        return adsRepository.save(ads);
    }
    /**
     * Получение объявлений по email пользователя
     * * @param id
     * * @param authentication
     */
    @Override
    public ResponseWrapperAds getAdsMe(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EMAIL.formatted(username)));

        List<AdsDto> adsDtoList = adsRepository.findAllByAuthorId(user.getId()).stream().map(x ->
                AdsMapperInterface.INSTANCE.toDto(x)).collect(Collectors.toList());
        ResponseWrapperAds responseWrapperAdsDto = new ResponseWrapperAds();
        responseWrapperAdsDto.setCount(adsDtoList.size());
        responseWrapperAdsDto.setResults(adsDtoList);
        return responseWrapperAdsDto;
    }
    /**
     * Изменение картинки объявления пользователя
     * * @param id
     * * @param image
     * * @param authentication
     */

    @Override
    public void updateAdsImage(Integer id, MultipartFile image, Authentication authentication) throws IOException {
        if (image == null) {
            throw new ImageNotFoundException(IMAGE_NOT_LOADED.formatted());
        }
        Ads ads = findAdsById(id);
        checkPermissionsToWorkWithAds (ads, authentication);
        imageService.removeImage(ads.getImage());
        ads.setImage(imageService.uploadImage(image));
        adsRepository.save(ads);
    }
    /**
     * Получение объявления по названию
     * * @param title
     */
    @Override
    public ResponseWrapperAds getAdsByTitleLike(String title) {
        List<AdsDto> ads = adsRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(x -> AdsMapperInterface.INSTANCE.toDto(x)).collect(Collectors.toList());
        ResponseWrapperAds responseWrapperAdsDto = new ResponseWrapperAds();
        responseWrapperAdsDto.setCount(ads.size());
        responseWrapperAdsDto.setResults(ads);
        return responseWrapperAdsDto;
    }
    /**
     * Поиск объявления по id
     * * @param id
     */

    public Ads findAdsById(Integer id) {

        return adsRepository.findById(id).orElseThrow(
                () -> new AdsNotFoundException(AD_NOT_FOUND_ID.formatted(id)));
    }
    /**
     * Проверка роли на работу с объявлениями
     * * @param ads
     * * @param authentication
     */
    public boolean checkPermissionsToWorkWithAds(Ads ads, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EMAIL.formatted(username)));
        if (!authentication.getAuthorities().contains(Role.ADMIN) && user.getId() != ads.getAuthor().getId()) {
            throw new AccessDeniedException(ACCESS_DENIED_MSG.formatted());
        }
        return true;
    };

}
