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

@Service
@RequiredArgsConstructor

public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;



    @Override
    public Ads addAd(CreateAds createAds, MultipartFile image, Authentication authentication) throws IOException {
        User user = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(() ->
                new UserNotFoundException("Пользователь с e-mail" + authentication.getName() + "не найден"));
        Ads ads = AdsMapperInterface.INSTANCE.toEntity(createAds);
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(image));
        return adsRepository.save(ads);
    }

    @Override
    public Ads removeAd(Integer id, Authentication authentication) {
        Ads ads = findAdsById(id);
        checkPermissionsToWorkWithAds (ads, authentication);
        imageRepository.deleteImageByAdsId(id);
        adsRepository.delete(ads);
        return ads;
    }


    @Override
    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
    }


    @Override
    public Ads getAds(Integer id, Authentication authentication) {
        Ads ads = findAdsById(id);
        return ads;
    }


    @Override
    public Ads updateAds(Integer id, CreateAds createAds, Authentication authentication) {
        Ads ads = findAdsById(id);
        checkPermissionsToWorkWithAds (ads, authentication);
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        ads.setTitle(createAds.getTitle());
        return adsRepository.save(ads);
    }

    @Override
    public ResponseWrapperAds getAdsMe(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException("Пользователь с e-mail" + username + "не найден"));

        List<AdsDto> adsDtoList = adsRepository.findAllByAuthorId(user.getId()).stream().map(x -> AdsMapperInterface.INSTANCE.toDto(x)).
                collect(Collectors.toList());
        ResponseWrapperAds responseWrapperAdsDto = new ResponseWrapperAds();
        responseWrapperAdsDto.setCount(adsDtoList.size());
        responseWrapperAdsDto.setResults(adsDtoList);
        return responseWrapperAdsDto;
    }


    @Override
    public void updateAdsImage(Integer id, MultipartFile image, Authentication authentication) throws IOException {
        if (image == null) {
            throw new ImageNotFoundException("Изображение не загружено");
        }
        Ads ads = findAdsById(id);
        checkPermissionsToWorkWithAds (ads, authentication);
        imageService.removeImage(ads.getImage());
        ads.setImage(imageService.uploadImage(image));
        adsRepository.save(ads);
    }

    @Override
    public ResponseWrapperAds getAdsByTitleLike(String title) {
        List<AdsDto> ads = adsRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(x -> AdsMapperInterface.INSTANCE.toDto(x)).collect(Collectors.toList());
        ResponseWrapperAds responseWrapperAdsDto = new ResponseWrapperAds();
        responseWrapperAdsDto.setCount(ads.size());
        responseWrapperAdsDto.setResults(ads);
        return responseWrapperAdsDto;
    }


    public Ads findAdsById(Integer id) {

        return adsRepository.findById(id).orElseThrow(
                () -> new AdsNotFoundException("Объявление с id " + id + " не найдено"));
    }

    public boolean checkPermissionsToWorkWithAds(Ads ads, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException("Пользователь с e-mail" + username + "не найден"));
        if (!authentication.getAuthorities().contains(Role.ADMIN) && user.getId() != ads.getAuthor().getId()) {
            throw new AccessDeniedException("Редактировать/удалять объявления может только автор объявления или Админ!");
        }
        return true;
    };



}
