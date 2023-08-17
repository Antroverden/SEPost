package com.skyeng.SEPost.service;

import com.skyeng.SEPost.dto.PostItemDto;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.dto.PostOfficeDto;
import com.skyeng.SEPost.entity.PostItem;
import com.skyeng.SEPost.entity.PostOffice;
import com.skyeng.SEPost.mapper.EventMapper;
import com.skyeng.SEPost.mapper.PostItemMapper;
import com.skyeng.SEPost.mapper.PostOfficeMapper;
import com.skyeng.SEPost.repository.EventRepository;
import com.skyeng.SEPost.repository.PostItemRepository;
import com.skyeng.SEPost.repository.PostOfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static com.skyeng.SEPost.entity.Event.Status.ARRIVED;
import static com.skyeng.SEPost.entity.PostItem.Type.LETTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    EventRepository eventRepository;
    @Mock
    EventMapper eventMapper;
    @Mock
    PostItemRepository postItemRepository;
    @Mock
    PostItemMapper postItemMapper;
    @Mock
    PostOfficeRepository postOfficeRepository;
    @Mock
    PostOfficeMapper postOfficeMapper;
    @InjectMocks
    PostService postService;
    @Test
    void registerTest() {
        PostOfficeDto postOfficeDto = new PostOfficeDto(111111, "Post Office 111111", "Moscow");
        PostOffice postOffice = new PostOffice(111111, "Post Office 111111", "Moscow");
        PostItemDtoWithStatus postItemDtoWithStatus = new PostItemDtoWithStatus(null, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto);
        PostItemDtoWithStatus registeredPostItemDtoWithStatus = new PostItemDtoWithStatus(1L, LETTER,
                111222, "address", "name", ARRIVED, postOfficeDto);
        PostItem postItem = new PostItem(null, LETTER,
                111222, "address", "name");
        when(postItemMapper.toPostItem(postItemDtoWithStatus)).thenReturn(postItem);
        when(postOfficeMapper.toPostoffice(postOfficeDto)).thenReturn(postOffice);
        postService.register(postItemDtoWithStatus);


        ArgumentCaptor<PostItem> customerArgumentCaptor = ArgumentCaptor.forClass(PostItem.class);

        verify(postItemRepository).save(customerArgumentCaptor.capture());

        PostItem capturedPostItem = customerArgumentCaptor.getValue();

        assertThat(capturedPostItem.getId()).isNull();
        assertThat(capturedPostItem.getType()).isEqualTo(postItemDtoWithStatus.getType());
        assertThat(capturedPostItem.getRecipientAddress()).isEqualTo(postItemDtoWithStatus.getRecipientAddress());
        assertThat(capturedPostItem.getRecipientName()).isEqualTo(postItemDtoWithStatus.getRecipientName());
        assertThat(capturedPostItem.getRecipientIndex()).isEqualTo(postItemDtoWithStatus.getRecipientIndex());
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
