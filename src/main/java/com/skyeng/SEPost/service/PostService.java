package com.skyeng.SEPost.service;

import com.skyeng.SEPost.dto.EventDto;
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

    public PostItemDtoWithStatus register(PostItemDtoWithStatus postItemDtoWithStatus) {
        PostItem postItem = postItemMapper.toPostItem(postItemDtoWithStatus);
        PostItem saved = postItemRepository.save(postItem);
        PostOffice postOffice = postOfficeMapper.toPostoffice(postItemDtoWithStatus.getPostOfficeDto());
        Event newEvent = Event.builder().status(Status.REGISTERED).postItem(postItem).postOffice(postOffice)
                .happenAt(LocalDateTime.now()).build();
        eventRepository.save(newEvent);
        postItemDtoWithStatus.setId(saved.getId());
        return postItemDtoWithStatus;
    }

    public PostItemDtoWithStatus updateStatus(Long id, Status status, Integer postOfficeIndex) {
        PostItem postItem = postItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Почтовое отправление не найдено"));
        PostOffice postOffice = null;
        if (postOfficeIndex != null) {
            postOffice = postOfficeRepository.findById(postOfficeIndex).orElseThrow(
                    () -> new NotFoundException("Почтовое отделение не найдено"));
        }
        Event newEvent = Event.builder().status(status).postItem(postItem).postOffice(postOffice)
                .happenAt(LocalDateTime.now()).build();
        eventRepository.save(newEvent);
        return getPostItemStatus(id);
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

    public List<EventDto> getHistory(Long id) {
        List<Event> events = eventRepository.findAllByPostItem_Id(id);
        return eventMapper.toEventDtos(events);
    }
}
