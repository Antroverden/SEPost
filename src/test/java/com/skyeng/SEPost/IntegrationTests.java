package com.skyeng.SEPost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.dto.PostOfficeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.skyeng.SEPost.entity.Event.Status.ARRIVED;
import static com.skyeng.SEPost.entity.Event.Status.REGISTERED;
import static com.skyeng.SEPost.entity.PostItem.Type.LETTER;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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

        mockMvc.perform(patch("/api/v1/post/1")
                        .param("status", String.valueOf(ARRIVED))
                        .param("postOfficeIndex", "222222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(String.valueOf(ARRIVED)));
    }

    @Test
    void getPostItemStatusTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postItemDtoWithStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recipientIndex").value(111222));

        mockMvc.perform(get("/api/v1/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(String.valueOf(ARRIVED)));
    }

    @Test
    void getHistoryTest() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(null, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postItemDtoWithStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recipientIndex").value(111222));

        mockMvc.perform(get("/api/v1/post/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    //    @Test
//    void addUserThrowException() {
//        User user = new User(1, "Name", "email@rambler.ru");
//        when(userRepository.save(user)).thenThrow(new DataAccessException("") {
//        });
//
//        assertThatThrownBy(() -> userService.addUser(user)).isInstanceOf(ConflictException.class).hasMessage("Юзер с имейлом " + user.getEmail() + " уже существует");
//    }
//
//    @Test
//    void updateUser() {
//        User user = new User(5, "Name", "email@rambler.ru");
//
//        when(userRepository.findById(5)).thenReturn(Optional.of(user));
//
//        User updatedUser = new User(5, "NameUpdate", "email@update.ru");
//
//        userService.updateUser(updatedUser);
//
//
//        ArgumentCaptor<User> customerArgumentCaptor = ArgumentCaptor.forClass(User.class);
//
//        verify(userRepository).save(customerArgumentCaptor.capture());
//
//        User capturedUser = customerArgumentCaptor.getValue();
//
//        assertThat(capturedUser.getId()).isEqualTo(updatedUser.getId());
//        assertThat(capturedUser.getName()).isEqualTo(updatedUser.getName());
//        assertThat(capturedUser.getEmail()).isEqualTo(updatedUser.getEmail());
//    }
//
//    @Test
//    void updateUserNameNull() {
//        User user = new User(5, "Name", "email@rambler.ru");
//
//        when(userRepository.findById(5)).thenReturn(Optional.of(user));
//
//        User updatedUser = new User(5, null, "email@update.ru");
//
//        userService.updateUser(updatedUser);
//
//        ArgumentCaptor<User> customerArgumentCaptor = ArgumentCaptor.forClass(User.class);
//
//        verify(userRepository).save(customerArgumentCaptor.capture());
//
//        User capturedUser = customerArgumentCaptor.getValue();
//
//        assertThat(capturedUser.getId()).isEqualTo(updatedUser.getId());
//        assertThat(capturedUser.getName()).isEqualTo(user.getName());
//        assertThat(capturedUser.getEmail()).isEqualTo(updatedUser.getEmail());
//    }
//
//    @Test
//    void updateUserEmailNull() {
//        User user = new User(5, "Name", "email@rambler.ru");
//
//        when(userRepository.findById(5)).thenReturn(Optional.of(user));
//
//        User updatedUser = new User(5, "NameUpdate", null);
//
//        userService.updateUser(updatedUser);
//
//
//        ArgumentCaptor<User> customerArgumentCaptor = ArgumentCaptor.forClass(User.class);
//
//        verify(userRepository).save(customerArgumentCaptor.capture());
//
//        User capturedUser = customerArgumentCaptor.getValue();
//
//        assertThat(capturedUser.getId()).isEqualTo(updatedUser.getId());
//        assertThat(capturedUser.getName()).isEqualTo(updatedUser.getName());
//        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
//    }
//
//    @Test
//    void updateUserEmailNullAndNameNull() {
//        User user = new User(5, "Name", "email@rambler.ru");
//
//        when(userRepository.findById(5)).thenReturn(Optional.of(user));
//
//        User updatedUser = new User(5, null, null);
//
//        userService.updateUser(updatedUser);
//
//
//        ArgumentCaptor<User> customerArgumentCaptor = ArgumentCaptor.forClass(User.class);
//
//        verify(userRepository).save(customerArgumentCaptor.capture());
//
//        User capturedUser = customerArgumentCaptor.getValue();
//
//        assertThat(capturedUser.getId()).isEqualTo(updatedUser.getId());
//        assertThat(capturedUser.getName()).isEqualTo(user.getName());
//        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
//    }
//
//    @Test
//    void getUserById() {
//        int id = 9;
//        User user = new User(9, "Name", "email@rambler.ru");
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        assertThat(userService.getUserById(id)).isEqualTo(user);
//    }
//
//    @Test
//    void getUserByIdThrowNotFound() {
//        int id = 9;
//        when(userRepository.findById(id)).thenThrow(new NotFoundException(
//                "Юзера с айди " + id + "не существует"));
//
//        assertThatThrownBy(() -> userService.getUserById(id)).isInstanceOf(NotFoundException.class).hasMessage("Юзера с айди " + id + "не существует");
//    }
//
//    @Test
//    void getUsers() {
//        userService.getUsers();
//        verify(userRepository).findAll();
//    }
}
