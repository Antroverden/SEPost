package com.skyeng.SEPost.dto;

import com.skyeng.SEPost.entity.Event.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    PostItemDto postItemDto;
    PostOfficeDto postOfficeDto;
    Status status;
    LocalDateTime happenAt;
}
