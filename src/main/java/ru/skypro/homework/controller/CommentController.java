package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.CommentMapperInterface;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("ads")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final AdsService adsService;

    @Operation(
            summary = "Получить комментарии объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperComment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Комментарии"
    )
    @GetMapping("{id}/comments")
    public ResponseWrapperComment getComments(@PathVariable Integer id) {
//        return ResponseWrapper.of(adsCommentMapper.toDto(adsService.getComments(id)));
//    }
        printLogInfo("GET", "getComments", "/" + id + "comments");
        List<CommentDto> commentDtoList = commentService.getComments(id).stream().map(x -> CommentMapperInterface.INSTANCE.toDto(x)).
                collect(Collectors.toList());
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setCount(commentDtoList.size());
        responseWrapperComment.setResults(commentDtoList);

        return responseWrapperComment;
    }


    @Operation(
            summary = "Добавить комментарий к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
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
            tags = "Комментарии"
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id, @RequestBody CommentDto commentDto, Authentication authentication) {
        printLogInfo("POST", "addComment" ,  "/" + id + "/comments");
        return ResponseEntity.ok(CommentMapperInterface.INSTANCE.toDto(commentService.addComment(id, commentDto, authentication)));
    }




    @Operation(
            summary = "Удалить комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
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
            tags = "Комментарии"
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId, Authentication authentication) {
        printLogInfo("DELETE", "deleteComment" , "/" + adId + "/comments/" + commentId);
        deleteComment(adId, commentId, authentication);
        return ResponseEntity.ok().build();
    }



    @Operation(
            summary = "Обновить комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
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
            tags = "Комментарии"
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId,
            @RequestBody CommentDto commentDto, Authentication authentication) {
        printLogInfo("PATCH", "updateComment", "/" + adId + "/comments/" + commentId);
        return ResponseEntity.ok(CommentMapperInterface.INSTANCE.toDto(commentService.updateComment(
                adId, commentId, commentDto, authentication)));
    }


    private void printLogInfo(String request, String name, String path) {
        logger.info("Вызван метод: " + name + ", тип запроса: "
                + request + ", адрес: /ads" + path);
    }


}
