package com.skyeng.SEPost.mapper;

import com.skyeng.SEPost.dto.PostOfficeDto;
import com.skyeng.SEPost.entity.PostOffice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostOfficeMapper {

    PostOfficeDto toPostofficeDto(PostOffice postoffice);

    PostOffice toPostoffice(PostOfficeDto dto);
}
