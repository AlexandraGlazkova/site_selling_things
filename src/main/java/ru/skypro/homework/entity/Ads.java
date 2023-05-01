package ru.skypro.homework.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ads")
public class Ads {

    /**
     * поле - id объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    /**
     * поле - цена объявления
     */
    private Integer price;

    /**
     * поле - заголовок объявления
     */
    private String title;

    /**
     * поле - описание объявления
     */
    private String description;

    /**
     * поле - id автора объявления
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * поле - id картинки объявления
     */
    @OneToOne()
    @JoinColumn(name = "image_id")
    private Image image;

}
