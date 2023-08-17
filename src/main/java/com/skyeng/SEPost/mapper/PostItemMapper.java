package com.skyeng.SEPost.mapper;

import com.skyeng.SEPost.dto.PostItemDto;
import com.skyeng.SEPost.dto.PostItemDtoWithStatus;
import com.skyeng.SEPost.entity.PostItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostItemMapper {

    PostItem toPostItem(PostItemDto dto);

    PostItemDto toPostItemDto(PostItem postItem);

    PostItemDtoWithStatus toPostItemDtoWithStatus(PostItem postItem);

    List<PostItemDto> toPostItemDtos(List<PostItem> postItems);

    void updatePostItem(@MappingTarget PostItem postItem, PostItemDto dto);
}
