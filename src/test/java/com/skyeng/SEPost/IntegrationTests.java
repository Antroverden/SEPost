package com.skyeng.SEPost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.dto.PostOfficeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.skyeng.SEPost.entity.Event.Status.ARRIVED;
import static com.skyeng.SEPost.entity.Event.Status.REGISTERED;
import static com.skyeng.SEPost.entity.PostItem.Type.LETTER;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(null, LETTER,
                111222, "address", "name", null, postOfficeDto);

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
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(null, LETTER,
                111222, "address", "name", null, postOfficeDto);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postItemDtoWithStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recipientIndex").value(111222));

        PostOfficeDto postOfficeDto1 = new PostOfficeDto(222222, "Post Office 222222", "St.Petersburg");
        PostItemDtoWithStatus updatedPostItemDtoWithStatus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto1);
        mockMvc.perform(put("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPostItemDtoWithStatus)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(String.valueOf(ARRIVED)));
    }

    @Test
    void getPostItemStatusTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", null, postOfficeDto);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postItemDtoWithStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recipientIndex").value(111222));

        mockMvc.perform(get("/api/v1/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(String.valueOf(REGISTERED)));
    }

    @Test
    void getHistoryAfterPostAndUpdateTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(null, LETTER,
                111222, "address", "name", null, postOfficeDto);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postItemDtoWithStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recipientIndex").value(111222));

        PostOfficeDto postOfficeDto1 = new PostOfficeDto(222222, "Post Office 222222", "St.Petersburg");
        PostItemDtoWithStatus updatedPostItemDtoWithStatus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto1);
        mockMvc.perform(put("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPostItemDtoWithStatus)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(String.valueOf(ARRIVED)));

        mockMvc.perform(get("/api/v1/post/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[1].status").value(String.valueOf(ARRIVED)))
                .andExpect(jsonPath("$.[0].postItemDto").isNotEmpty())
                .andExpect(jsonPath("$.[0].postOfficeDto").isNotEmpty());
    }

    @Test
    void getHistoryAfterPostTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(null, LETTER,
                111222, "address", "name", null, postOfficeDto);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postItemDtoWithStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recipientIndex").value(111222));

        mockMvc.perform(get("/api/v1/post/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].status").value(String.valueOf(REGISTERED)))
                .andExpect(jsonPath("$.[0].postItemDto").isNotEmpty())
                .andExpect(jsonPath("$.[0].postOfficeDto").isNotEmpty());
    }
}
