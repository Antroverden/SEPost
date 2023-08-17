package com.skyeng.SEPost.mapper;

import com.skyeng.SEPost.dto.EventDto;
import com.skyeng.SEPost.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PostItemMapper.class, PostOfficeMapper.class})
public interface EventMapper {

    @Mapping(source = "postItem", target = "postItemDto")
    @Mapping(source = "postOffice", target = "postOfficeDto")
    EventDto toEventDto(Event event);

    List<EventDto> toEventDtos(List<Event> events);
}
