package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
import ru.skypro.homework.userDetailsManager.UserDetailsServiceImpl;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserDetailsServiceImpl userDetailsServiceImpl;
  private final PasswordEncoder encoder;
  private final UserRepository userRepository;



  @Override
  public boolean login(String userName, String password) {

    try {
      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
      if (!encoder.matches(password, userDetails.getPassword())) {
        throw new IncorrectPasswordException("Неверный пароль!");
      }

    } catch (UsernameNotFoundException e) {
      throw new UserNotFoundException("Пользователь " + userName + " не существует!");
    }  return true;

  }


  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    User user = UserMapperInterface.INSTANCE.toEntity(registerReq);
    if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
      throw new ValidationException("Пользователь " + registerReq.getUsername() + " уже зарегистрирован!");
    }
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRole(role);
    userRepository.save(user);
    return true;
  }




}
