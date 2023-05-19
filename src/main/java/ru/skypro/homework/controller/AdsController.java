package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("ads")
public class AdsController {
    private final AdsService adsService;
    private final ImageService imageService;
    private final AdsMapper adsMapper;

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
        List<AdsDto> adsDtoList = adsService.getAllAds().stream().map(x -> adsMapper.mapToAdsDto(x)).
                collect(Collectors.toList());
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
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

        return ResponseEntity.ok(adsMapper.mapToAdsDto(adsService.addAd(createAdsDto, multipartFile, authentication)));

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
    @GetMapping("//ads/{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adsMapper.mapToFullAds(adsService.getAds(id)));
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
    @DeleteMapping("/ads/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id, Authentication authentication) {
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
    @PatchMapping("/ads/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Integer id,
                                            @RequestBody CreateAds createAds,  Authentication authentication) {
        return ResponseEntity.ok(adsMapper.mapToAdsDto(adsService.updateAds(id, createAds, authentication)));
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
               return ResponseEntity.ok(adsService.getAdsMe(authentication.getName()));
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
    @PatchMapping(value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable Integer id, @Parameter(required = true)
    @RequestPart(name = "image") @Valid MultipartFile image, Authentication authentication) throws IOException {
        adsService.updateAdsImage(id, image, authentication);
        return ResponseEntity.ok().build();
        }


    }



