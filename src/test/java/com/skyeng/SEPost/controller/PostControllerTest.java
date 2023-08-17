package com.skyeng.SEPost.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyeng.SEPost.dto.EventDto;
import com.skyeng.SEPost.dto.PostItemDto;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.dto.PostOfficeDto;
import com.skyeng.SEPost.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.skyeng.SEPost.entity.Event.Status.*;
import static com.skyeng.SEPost.entity.PostItem.Type.LETTER;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;

    @Test
    void registerTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(null, LETTER,
                111222, "address", "name", null, postOfficeDto);
        PostItemDtoWithStatus registeredPostItemDtoWithStatus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", REGISTERED, postOfficeDto);
        when(postService.register(any(PostItemDtoWithStatus.class))).thenReturn(registeredPostItemDtoWithStatus);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postItemDtoWithStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recipientIndex").value(111222));
    }

    @Test
    void updateStatusTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus updatedPostItemDtoWithStastus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto);
        when(postService.updateStatus(1L, ARRIVED, 111111))
                .thenReturn(updatedPostItemDtoWithStastus);

        mockMvc.perform(patch("/api/v1/post/1")
                .param("status", String.valueOf(ARRIVED))
                .param("postOfficeIndex", "111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(String.valueOf(ARRIVED)));
    }

    @Test
    void getPostItemStatusTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto);
        when(postService.getPostItemStatus(1L))
                .thenReturn(postItemDtoWithStatus);

        mockMvc.perform(get("/api/v1/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(String.valueOf(ARRIVED)));
    }

    @Test
    void getHistoryTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDto postItemDto = new PostItemDto(1L, LETTER, 111222,
                "address", "name");
        EventDto eventDto = new EventDto(postItemDto, postOfficeDto, ARRIVED, LocalDateTime.now());
        EventDto eventDto2 = new EventDto(postItemDto, postOfficeDto, DEPARTED, LocalDateTime.now().plusHours(1));
        List<EventDto> history = List.of(eventDto2, eventDto);
        when(postService.getHistory(1L)).thenReturn(history);

        mockMvc.perform(get("/api/v1/post/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(history)));
    }
}
