package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;

import java.time.Instant;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", uses = UserMapperInterface.class)
public interface CommentMapperInterface extends WebMapper<CommentDto, Comment> {

    CommentMapperInterface INSTANCE = Mappers.getMapper(CommentMapperInterface.class);
    String ADS_IMAGES = "/ads/image/";

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ads", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", qualifiedByName = "instantToInteger")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", source = "author.image", qualifiedByName = "imageMappingUser")
    CommentDto toDto(Comment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ads", ignore = true)
    Comment toEntity(CreateComment dto);

    @Named("imageMappingUser")
    default String imageMappingUser(Image image) {
        if (image == null) {
            return null;
        }
        return ADS_IMAGES + image.getId();
    }
    @Named("instantToInteger")
    default long instantToInteger(Instant instant) {
        return instant.atZone(ZoneOffset.ofHours(3)).toInstant().toEpochMilli();
    }

}

