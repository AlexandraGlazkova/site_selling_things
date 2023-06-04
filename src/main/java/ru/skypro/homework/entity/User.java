package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.Role;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


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
    @Column(name = "email",unique = true)
    @Email(message = "формат email не верный")
    @NotBlank(message = "email не может быть пустым")
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
    @Column(name = "password")
    @NotBlank(message = "не может быть пустым")
    private String password;



}
