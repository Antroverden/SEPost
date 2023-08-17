package com.skyeng.SEPost.dto;

import com.skyeng.SEPost.entity.Event;
import com.skyeng.SEPost.entity.PostItem.Type;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PostItemDtoWithStatus {
    @NotBlank
    Long id;
    Type type;
    RecipientDto recipient;
    Event.Status status;
    PostOfficeDto postOfficeDto;
}
