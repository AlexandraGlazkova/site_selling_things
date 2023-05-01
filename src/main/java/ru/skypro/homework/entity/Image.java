package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {

        /**
         * поле - id фото
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
         * поле - размер фото
         */
        private long fileSize;

        /**
         * поле - путь к фото
         */
        private String filePath;

        /**
         * поле - тип фото
         */
        private String mediaType;
        /**
         * поле - тип данных y фото
         */
        @Lob
        @Type(type = "binary")
        private byte[] data;
        /**
         * поле - объект Объявление
         */
        @OneToOne
        private Ads ads;

    }


