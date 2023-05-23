package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapperInterface extends WebMapper<CommentDto, Comment> {

    CommentMapperInterface INSTANCE = Mappers.getMapper(CommentMapperInterface.class);

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ads", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", source = "author.id")
    CommentDto toDto(Comment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ads", ignore = true)
    Comment toEntity(CreateComment dto);


}

