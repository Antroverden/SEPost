package com.skyeng.SEPost.dto;

import com.skyeng.SEPost.entity.PostItem.Type;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PostItemDto {

    Long id;
    Type type;
    Integer recipientIndex;
    String recipientAddress;
    String recipientName;
}
