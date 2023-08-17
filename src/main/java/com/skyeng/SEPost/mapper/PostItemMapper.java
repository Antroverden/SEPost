package com.skyeng.SEPost.mapper;

import com.skyeng.SEPost.dto.PostItemDto;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.entity.PostItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostItemMapper {

    PostItemDto toPostItemDto(PostItem postItem);

    PostItemDtoWithStatus toPostItemDtoWithStatus(PostItem postItem);

    PostItem toPostItem(PostItemDtoWithStatus postItemDtoWithStatus);

    void updatePostItem(@MappingTarget PostItem postItem, PostItemDtoWithStatus postItemDtoWithStatus);
}
