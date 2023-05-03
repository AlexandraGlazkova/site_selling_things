package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;

@Service
public class CommentMapper {
    private final UserRepository userRepository;
    private final AdsRepository adsRepository;

    public CommentMapper(UserRepository userRepository, AdsRepository adsRepository) {
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
    }


    public Comment mapToComment(Integer adsId, Comment dto) {
        User author = userRepository.findById(dto.getAuthor().getId()).orElse(null);
        if (author == null) {
            throw new UserNotFoundException("Author not found");
        }
        Ads ads = adsRepository.findById(adsId).orElse(null);
        if (ads == null) {
            throw new AdsNotFoundException("Ad not found");
        }
        Comment entity = new Comment();
        entity.setAuthor(author);
        entity.setAd(ads);
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setText(dto.getText());
        return entity;
    }


    public CommentDto mapToCommentDto(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.setAuthor(entity.getAuthor().getId());
        dto.setAuthorImage(String.valueOf(entity.getAuthor().getImage()));
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setCreatedAt(entity.getCreatedAt().getNano());
        dto.setPk(entity.getId());
        dto.setText(entity.getText());
        return dto;
    }
}

