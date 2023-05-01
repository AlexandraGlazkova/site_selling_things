package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {


    @Override
    public boolean getComments(Integer id) {
        return false;
    }

    @Override
    public boolean addComment(Integer id, CommentDto commentDto) {
        return false;
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        return false;
    }

    @Override
    public boolean updateComment(Integer adId, Integer commentId, CommentDto commentDto) {
        return false;
    }
}
