package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.service.AdsService;

@Service

public class AdsServiceImpl implements AdsService {
    @Override
    public boolean addAd(CreateAds createAds, MultipartFile image) {
        return false;
    };


    @Override
    public boolean removeAd(Integer adId) {
        return false;
    }

    @Override
    public boolean getAllAds() {
        return false;
    };

    @Override
    public boolean getAds (Integer adId) {
        return false;
    }

    @Override
    public boolean updateAds (Integer adId) {
        return false;
    }

    @Override
    public boolean getAdsMe () {
        return false;
    }
}
