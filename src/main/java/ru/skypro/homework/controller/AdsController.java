package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.mapper.AdsMapperInterface;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdsController.class);
    private final AdsService adsService;
    private final ImageService imageService;


    @Operation(summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные объявления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
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
            }, tags = "Объявления"
    )
    @GetMapping
    public ResponseWrapperAds getAllAds() {
        List<AdsDto> adsDtoList = adsService.getAllAds().stream().map(x -> AdsMapperInterface.INSTANCE.toDto(x)).
                collect(Collectors.toList());
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        printLogInfo("GET", "getAllAds", "");
        return responseWrapperAds;
    }



    @Operation(summary = "Добавить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление добавлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
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
            }, tags = "Объявления"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(required = true)
                                        @RequestPart(name = "properties") @Valid CreateAds createAdsDto,
                                      @RequestPart(name = "image") @Valid MultipartFile multipartFile, Authentication authentication) throws IOException {
        printLogInfo("POST", "addAds", "");
        return ResponseEntity.ok(AdsMapperInterface.INSTANCE.toDto(adsService.addAd(createAdsDto, multipartFile, authentication)));

    }


    @Operation(summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вся информация об объявлении",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "Объявления"
    )
    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable("id") Integer id) {
        printLogInfo("GET", "getAds", "/" +  + id);
        return ResponseEntity.ok(AdsMapperInterface.INSTANCE.toFullAdsDto(adsService.getAds(id)));
    }


    @Operation(summary = "Удалить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленное объявление"
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
                    )


            }, tags = "Объявления"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id, Authentication authentication) {
        printLogInfo("DELETE", "removeAd", "/" + id);
        adsService.removeAd(id, authentication);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Обновить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
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

            }, tags = "Объявления"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Integer id,
                                            @RequestBody CreateAds createAds,  Authentication authentication) {
        printLogInfo("PATCH", "updateAds", "/" + id);
        return ResponseEntity.ok(AdsMapperInterface.INSTANCE.toDto(adsService.updateAds(id, createAds, authentication)));
    }



    @Operation(summary = "Получить объявления авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все объявления авторизованного пользователя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )

            }, tags = "Объявления"
    )
    @GetMapping("/me")
    public  ResponseEntity<ResponseWrapperAds> getAdsMe(Authentication authentication) {
        printLogInfo("GET", "getAdsMe", "/me");
        return ResponseEntity.ok(adsService.getAdsMe(authentication));
    }


    @Operation(summary = "Обновить картинку объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое фото",
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "Объявления"
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable Integer id, @Parameter(required = true)
    @RequestPart(name = "image") @Valid MultipartFile image, Authentication authentication) throws IOException {
        printLogInfo("PATCH", "updateImage", "/" + id);
        adsService.updateAdsImage(id, image, authentication);
        return ResponseEntity.ok().build();
        }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") Integer id) {
        printLogInfo("GET", "getAdsImage", "/image/id");
        return ResponseEntity.ok(imageService.getImageById(id).getData());
    }


    private void printLogInfo(String request, String name, String path) {
        LOGGER.info("Вызван метод: " + name + ", тип запроса: "
                + request + ", адрес: /ads" + path);
    }


}



