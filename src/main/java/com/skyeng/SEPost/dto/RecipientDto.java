package com.skyeng.SEPost.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RecipientDto {

    Integer index;
    String address;
    String name;
}
