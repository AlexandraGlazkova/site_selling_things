package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.UserServiceImpl;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

  /*
      @Operation(summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый пароль",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ?????????.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка авторизации"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден"
                    )
            }
    )

    @PostMapping("/set_password")
    //метод для смены пароля
*/

   @Operation(
           summary = "Получить информацию об авторизованном пользователее",
                    responses = {
                   @ApiResponse(
                           responseCode = "200",
                           description = "Информация о пользователе",
                           content = @Content(
                                   mediaType = MediaType.APPLICATION_JSON_VALUE,
                                   schema = @Schema(implementation = UserDto.class)
                           )
                   ),
                   @ApiResponse(
                           responseCode = "401",
                           description = "Ошибка авторизации"
                   ),
                   @ApiResponse(
                           responseCode = "403",
                           description = "Доступ запрещен"
                   ),
                   @ApiResponse(
                           responseCode = "404",
                           description = "Пользователь не найден"
                   )
           }
   )

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        return null;
        //ошибка. что должен возвращать данный метод????????
    }

    @Operation(
            summary = "Обновить информацию об авторизованном пользователе",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Информация о пользователе",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о пользователе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка авторизации"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден"
                    )
            }
    )


    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return new UserDto();

    }

    @Operation(
            summary = "Обновить аватар авторизованного пользователя",
                        responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Аватар пользователя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден"
                    )
            }
    )

    @PatchMapping("/me/image")
    public UserDto updateUserImage(@RequestBody UserDto userDto) {
        return new UserDto ();
        //????????????
    }



}
