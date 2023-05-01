package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateAds;

public interface AdsService {
    boolean addAd(CreateAds createAds, MultipartFile image);
    boolean removeAd(Integer adId);
    boolean getAllAds ();
    boolean getAds (Integer adId);
    boolean updateAds (Integer adId);
    boolean getAdsMe ();
}
