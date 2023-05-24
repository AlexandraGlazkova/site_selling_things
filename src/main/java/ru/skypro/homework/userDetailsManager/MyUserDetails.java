package ru.skypro.homework.userDetailsManager;

import lombok.Getter;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.List;

import static ru.skypro.homework.dto.Role.ADMIN;
import static ru.skypro.homework.dto.Role.USER;

@Getter
public class MyUserDetails extends org.springframework.security.core.userdetails.User {



    private final Integer id;

    public MyUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id = user.getId();
    }

    @Override
    public void eraseCredentials() {
    }
}
