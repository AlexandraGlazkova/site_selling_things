package ru.skypro.homework.entity;

import javax.persistence.Entity;
import javax.persistence.*;
import java.awt.*;
import java.time.Instant;

@Entity

public class User {
    //Предварительный вариант


    /**
     * поле - id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * поле - email пользователя
     */
    private String email;

    /**
     * поле - имя пользователя
     */
    private String firstName;

    /**
     * поле - фамилия пользователя
     */
    private String lastName;


    /**
     * поле - телефон пользователя
     */
    private String phone;


    /**
     * поле - дата регистрации пользователя
     */
    private Instant regDate;

    /**
     * поле - город пользователя
     */
    private String city;


    /**
     * поле - ссылка на аватар пользователя
     */
    private String image;
    //String преварительно, нужен другой тип данных???



}
