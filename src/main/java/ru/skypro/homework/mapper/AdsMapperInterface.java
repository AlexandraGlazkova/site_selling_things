package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;

@Mapper(componentModel = "spring")
public interface AdsMapperInterface extends WebMapper<AdsDto, Ads> {

    AdsMapperInterface INSTANCE = Mappers.getMapper(AdsMapperInterface.class);
    String ADS_IMAGES = "/ads/image/";

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
//    @Mapping(target = "price", source = "price")
//    @Mapping(target = "title", source = "title")
    Ads toEntity(AdsDto dto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    AdsDto toDto(Ads entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(CreateAds dto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    @Mapping(target = "phone", source = "author.phone")
    FullAds toFullAdsDto(Ads entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return null;
        }
        return ADS_IMAGES + image.getId();
    }
}
