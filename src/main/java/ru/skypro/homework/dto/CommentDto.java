package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {


    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private Integer pk;
    private String text;

}
