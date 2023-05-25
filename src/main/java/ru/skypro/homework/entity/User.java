package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.Role;

import javax.persistence.Entity;
import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Getter
public class User {

    /**
     * поле - id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * поле - логин пользователя
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
     * поле - ссылка на аватар пользователя
     */

    @OneToOne()
    @JoinColumn(name = "image_id")
    private Image image;
    /**
     * поле - тип пользователя - его роль
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * поле - пароль пользователя
     */
    private String password;



}
