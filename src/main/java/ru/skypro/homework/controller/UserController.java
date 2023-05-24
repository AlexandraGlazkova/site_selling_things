package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapperInterface;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthService authService;

    @Operation(
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пароль изменен"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Пользователи"
    )
    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword password, Authentication authentication) {
        printLogInfo("POST", "setPassword", "/set_password");
        userService.setPassword(password.getCurrentPassword(), password.getNewPassword(), authentication);
        return ResponseEntity.ok(password);
    }

    @Operation(
            summary = "Получить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация получена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Пользователи"
    )
    @GetMapping("/me")
    public UserDto getUser(Authentication authentication) {
        printLogInfo("GET", "/me", "getUser");
        return UserMapperInterface.INSTANCE.toDto((userService.getUser(authentication)));

    }

    @Operation(
            summary = "Обновить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь изменен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Пользователи"
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        printLogInfo("PATCH", "updateUser", "/me");
        return ResponseEntity.ok(UserMapperInterface.INSTANCE.toDto(userService.updateUser(userDto, authentication)));
    }

    @Operation(
            summary = "Обновить аватар авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фото изменено"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Пользователи"
    )
    @PatchMapping(value = "/me/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateUserImage(@RequestPart(name = "image") MultipartFile image, Authentication authentication) throws IOException {
        printLogInfo("PATCH", "updateUserImage", "/me/image");
        userService.updateUserImage(image, authentication);
        return ResponseEntity.ok().build();
    }

    private void printLogInfo(String request, String name, String path) {
        LOGGER.info("Вызван метод: " + name + ", тип запроса: "
                + request + ", адрес: /users" + path);
    }
}