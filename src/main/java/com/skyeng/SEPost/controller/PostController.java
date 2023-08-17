package com.skyeng.SEPost.controller;

import com.skyeng.SEPost.dto.EventDto;
import com.skyeng.SEPost.dto.PostItemDto;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.entity.Event.Status;
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
    public PostItemDto register(@RequestBody PostItemDto postItemDto) {
        return postService.register(postItemDto);
    }

    @PatchMapping
    @Operation(summary = "Изменение статуса почтового отправления")
    public PostItemDto updateStatus(@RequestBody PostItemDto postItemDto,
                              @RequestParam Status status,
                              @RequestParam(required = false) Long postOfficeIndex) {
        return postService.updateStatus(postItemDto, status, postOfficeIndex);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Просмотр статуса почтового отправления")
    public PostItemDtoWithStatus getPostItemStatus(@PathVariable Long id) {
        return postService.getPostItemStatus(id);
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Просмотр полной истории движения почтового отправления")
    public List<EventDto> getHistory(@PathVariable Long id) {
        return postService.getAll(id);
    }
}
