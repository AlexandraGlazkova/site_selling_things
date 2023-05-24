package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    /**
     * поле - id комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * поле - дата и время создания комментария
     */
    private Instant createdAt;

    /**
     * поле - текст комментария
     */
    private String text;

    /**
     * поле - автор комментария
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * поле - объект Объявление
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_id")
    private Ads ads;

}
