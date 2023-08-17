package com.skyeng.SEPost.mapper;

import com.skyeng.SEPost.dto.EventDto;
import com.skyeng.SEPost.entity.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto toEventDto(Event event);

    List<EventDto> toEventDtos(List<Event> events);
}
