package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;

public interface CommentService {
    Collection<Comment> getComments(Integer id);

    Comment addComment(Integer id, CreateComment createComment, Authentication authentication);

    Comment deleteComment(Integer adId, Integer commentId, Authentication authentication);


    Comment updateComment(Integer adId, Integer commentId, CommentDto commentDto, Authentication authentication);

}