package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;

public interface CommentService {
    boolean getComments(Integer id);

    boolean addComment(Integer id, CommentDto commentDto);

    boolean deleteComment(Integer adId, Integer commentId);

    boolean updateComment(Integer adId, Integer commentId, CommentDto commentDto);

}