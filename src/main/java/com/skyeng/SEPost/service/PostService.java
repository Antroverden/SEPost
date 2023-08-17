package com.skyeng.SEPost.service;

import com.skyeng.SEPost.dto.EventDto;
import com.skyeng.SEPost.dto.PostItemDto;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.dto.PostOfficeDto;
import com.skyeng.SEPost.entity.Event;
import com.skyeng.SEPost.entity.Event.Status;
import com.skyeng.SEPost.entity.PostItem;
import com.skyeng.SEPost.entity.PostOffice;
import com.skyeng.SEPost.exception.NotFoundException;
import com.skyeng.SEPost.mapper.EventMapper;
import com.skyeng.SEPost.mapper.PostItemMapper;
import com.skyeng.SEPost.mapper.PostOfficeMapper;
import com.skyeng.SEPost.repository.EventRepository;
import com.skyeng.SEPost.repository.PostItemRepository;
import com.skyeng.SEPost.repository.PostOfficeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    EventRepository eventRepository;
    EventMapper eventMapper;
    PostItemRepository postItemRepository;
    PostItemMapper postItemMapper;
    PostOfficeRepository postOfficeRepository;
    PostOfficeMapper postOfficeMapper;

    public PostItemDto register(PostItemDto postItemDto) {
        PostItem postItem = postItemMapper.toPostItem(postItemDto);
        PostItem saved = postItemRepository.save(postItem);
        Event newEvent = Event.builder().status(Status.REGISTERED).postItem(postItem)
                .happenAt(LocalDateTime.now()).build();
        eventRepository.save(newEvent);
        return postItemMapper.toPostItemDto(saved);
    }

    public PostItemDto updateStatus(PostItemDto postItemDto, Status status, Long postOfficeIndex) {
        PostItem postItem = postItemRepository.findById(postItemDto.getId()).orElseThrow(
                () -> new NotFoundException("Почтовое отправление не найдено"));
        postItemMapper.updatePostItem(postItem, postItemDto);
        PostOffice postOffice = null;
        if (postOfficeIndex != null) {
            postOffice = postOfficeRepository.findById(postOfficeIndex).orElseThrow(
                    () -> new NotFoundException("Почтовое отделение не найдено"));
        }
        postItemRepository.save(postItem);
        Event newEvent = Event.builder().status(status).postItem(postItem).postOffice(postOffice)
                .happenAt(LocalDateTime.now()).build();
        eventRepository.save(newEvent);
        return postItemMapper.toPostItemDto(postItem);
    }

    public PostItemDtoWithStatus getPostItemStatus(Long id) {
        PostItem postItem = postItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Почтовое отправление не найдено"));
        PostItemDtoWithStatus postItemDtoWithStatus = postItemMapper.toPostItemDtoWithStatus(postItem);
        Event event = eventRepository.findFirstByPostItem_IdOrderByHappenAtDesc(id);
        postItemDtoWithStatus.setStatus(event.getStatus());
        PostOfficeDto postOfficeDto = postOfficeMapper.toPostofficeDto(event.getPostOffice());
        postItemDtoWithStatus.setPostOfficeDto(postOfficeDto);
        return postItemDtoWithStatus;
    }

    public List<EventDto> getAll(Long id) {
        List<Event> events = eventRepository.findAllByPostItem_Id(id);
        return eventMapper.toEventDtos(events);
    }
}
