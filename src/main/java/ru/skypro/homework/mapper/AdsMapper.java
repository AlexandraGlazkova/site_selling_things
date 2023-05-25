package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

import java.util.List;

@Service
public class AdsMapper {
//    private final UserRepository userRepository;
//    public AdsMapper(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    public Ads mapToAds(CreateAds dto, MultipartFile image) {
//
//
//        User author = userRepository.findById(1).orElse(null);
//
//        Ads entity = new Ads();
//        entity.setTitle(dto.getTitle());
//        entity.setDescription(dto.getDescription());
//        entity.setPrice(dto.getPrice());
//        //entity.setImage((Image) image);
//        entity.setAuthor(author);
//        return entity;
//    }
//
//
//    public AdsDto mapToAdsDto(Ads entity){
//        AdsDto dto = new AdsDto();
//        dto.setAuthor(entity.getAuthor().getId());
//        //dto.setImage(String.valueOf(entity.getImage()));
//        dto.setPrice(entity.getPrice());
//        dto.setTitle(entity.getTitle());
//        return dto;
//    }
//
//    public FullAds mapToFullAds(Ads entity){
//        FullAds dto = new FullAds();
//        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
//        dto.setAuthorLastName(entity.getAuthor().getLastName());
//        dto.setDescription(entity.getDescription());
//        dto.setEmail(entity.getAuthor().getEmail());
//        //dto.setImage
//        dto.setPhone(entity.getAuthor().getPhone());
//        dto.setPrice(entity.getPrice());
//        dto.setTitle(entity.getTitle());
//        return dto;
//    }

}

