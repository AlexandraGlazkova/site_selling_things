package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.Ads;

import java.io.IOException;
import java.util.Collection;

public interface AdsService {
    Ads addAd (CreateAds createAds, MultipartFile image, Authentication authentication) throws IOException;
    Ads removeAd (Integer id, Authentication authentication);
    Collection<Ads> getAllAds();
    Ads getAds(Integer id);
    Ads updateAds(Integer id, CreateAds createAds, Authentication authentication);
    ResponseWrapperAds getAdsMe(String username);
    void updateAdsImage(Integer id, MultipartFile image, Authentication authentication) throws IOException;
}
