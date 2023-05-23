package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.mapper.AdsMapperInterface;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.mapper.UserMapperInterface;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManager manager;
  private final PasswordEncoder encoder;
  private final UserRepository userRepository;

  @Override
  public boolean login(String userName, String password) {
   if (!manager.userExists(userName)) {
       throw new BadCredentialsException ("Пользователь " + userName + " не существует!");
     }
    UserDetails userDetails = manager.loadUserByUsername(userName);
    if (!encoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Неверный пароль!");
    } return true;
  }

  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    if (manager.userExists(registerReq.getUsername())) {
      throw new ValidationException("Пользователь " + registerReq.getUsername() + " уже зарегистрирован!");
    }
    manager.createUser(
        User.builder()
            .passwordEncoder(this.encoder::encode)
            .password(registerReq.getPassword())
            .username(registerReq.getUsername())
            .roles(role.name())
            .build());
      ru.skypro.homework.entity.User user = UserMapperInterface.INSTANCE.toEntity(registerReq);
      user.setPassword(encoder.encode(user.getPassword()));
      user.setRole(role);
      userRepository.save(user);
    return true;
  }




}
