package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;

@Mapper(componentModel = "spring", uses = UserMapperInterface.class)
public interface CommentMapperInterface extends WebMapper<CommentDto, Comment> {

    CommentMapperInterface INSTANCE = Mappers.getMapper(CommentMapperInterface.class);
     String USERS_IMAGES = "/users/image/";

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ads", ignore = true)
    //@Mapping(target = "author.image", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", source = "author.image", qualifiedByName = "imageMappingComment")
    CommentDto toDto(Comment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ads", ignore = true)
    //@Mapping(target = "image", source = "author.image", qualifiedByName = "imageMapping")
    Comment toEntity(CreateComment dto);

    @Named("imageMappingComment")
    default String imageMappingComment(Image image) {
        if (image == null) {
            return null;
        }
        return USERS_IMAGES + image.getId();
    }

}

