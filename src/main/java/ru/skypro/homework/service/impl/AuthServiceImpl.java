package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapperInterface;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import javax.validation.ValidationException;

import static ru.skypro.homework.constant.error.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder encoder;
  private final UserRepository userRepository;

  /**
   * Вход в личный кабинет авторизированного пользователя
   * * @param userName
   * * @param password
   */

  @Override
  public boolean login(String userName, String password) {

    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
      if (!encoder.matches(password, userDetails.getPassword())) {
        throw new IncorrectPasswordException(WRONG_PASS_MSG.formatted());
      }

    } catch (UsernameNotFoundException e) {
      throw new UserNotFoundException(USER_DOES_NOT_EXIST.formatted(userName));
    }  return true;

  }
  /**
   * Регистрация пользователя
   * * @param registerReq
   * * @param role
   */

  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    User user = UserMapperInterface.INSTANCE.toEntity(registerReq);
    if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
      throw new ValidationException(USER_ALREADY_REGISTERED.formatted(registerReq.getUsername()));
    }
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRole(role);
    userRepository.save(user);
    return true;
  }
}
