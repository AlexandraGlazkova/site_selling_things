package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapperInterface;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.time.Instant;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdsRepository adsRepository;

    @Override
    public Collection <Comment> getComments(Integer adsId) {
        return commentRepository.findAllByAdsId(adsId);
    }


    @Override
    public Comment addComment(Integer id, CreateComment createComment, Authentication authentication) {
        Comment comment = CommentMapperInterface.INSTANCE.toEntity(createComment);
        User user = userService.getUser(authentication);
        comment.setAuthor(user);
        comment.setAds(findAdsById(id));
        comment.setCreatedAt(Instant.now());
        return commentRepository.save(comment);
    }


    @Override
    public Comment deleteComment(Integer adId, Integer commentId, Authentication authentication) {
        Comment comment = commentRepository.findCommentByIdAndAdsId(commentId, adId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий с id "  + commentId + " не найден!"));
        checkPermissionsToWorkWithComment(comment,authentication);
        commentRepository.delete(comment);
        return comment;
    }


    @Override
    public Comment updateComment(Integer adId, Integer commentId, CommentDto commentDto, Authentication authentication) {
        Comment comment = commentRepository.findCommentByIdAndAdsId(commentId, adId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий с id "  + commentId + " не найден!"));
                checkPermissionsToWorkWithComment(comment,authentication);
        comment.setText(commentDto.getText());
        return commentRepository.save(comment);
    }


    public boolean checkPermissionsToWorkWithComment(Comment comment, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException("Пользователь с e-mail" + username + "не найден"));
        if (!authentication.getAuthorities().contains(Role.ADMIN) && user.getId() != comment.getAuthor().getId()) {
            throw new AccessDeniedException("Редактировать/удалять комментарии может только автор объявления или Админ!");
        }
        return true;
    };

    public Ads findAdsById(Integer id) {

        return adsRepository.findById(id).orElseThrow(
                () -> new AdsNotFoundException("Объявление с id " + id + " не найдено"));
    }


}
