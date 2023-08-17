package com.skyeng.SEPost.dto;

import com.skyeng.SEPost.entity.Event;
import com.skyeng.SEPost.entity.PostItem.Type;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static com.skyeng.SEPost.entity.Event.*;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PostItemDtoWithStatus {

    Long id;
    Type type;
    Integer recipientIndex;
    String recipientAddress;
    String recipientName;
    Status status;
    PostOfficeDto postOfficeDto;
}
