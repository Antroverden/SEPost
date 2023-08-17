package com.skyeng.SEPost.controller;

import com.skyeng.SEPost.dto.EventDto;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер управления почтовыми отправлениями")
public class PostController {

    PostService postService;

    @PostMapping
    @Operation(summary = "Регистрация почтового отправления")
    @ResponseStatus(HttpStatus.CREATED)
    public PostItemDtoWithStatus register(@RequestBody PostItemDtoWithStatus postItemDtoWithStatus) {
        return postService.register(postItemDtoWithStatus);
    }

    @PutMapping
    @Operation(summary = "Изменение статуса почтового отправления")
    public PostItemDtoWithStatus updateStatus(@RequestBody PostItemDtoWithStatus postItemDtoWithStatus) {
        return postService.updateStatus(postItemDtoWithStatus);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Просмотр статуса почтового отправления")
    public PostItemDtoWithStatus getPostItemStatus(@PathVariable Long id) {
        return postService.getPostItemStatus(id);
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Просмотр полной истории движения почтового отправления")
    public List<EventDto> getHistory(@PathVariable Long id) {
        return postService.getHistory(id);
    }
}
