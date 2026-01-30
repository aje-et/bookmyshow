package com.ajeet.bookmyshow.mapper;

import com.ajeet.bookmyshow.dto.TheatreCreateDTO;
import com.ajeet.bookmyshow.dto.TheatreDTO;
import com.ajeet.bookmyshow.model.Theatre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TheatreMapper {

    @Mapping(target = "partner.id", source = "partnerId")
    Theatre toEntity(TheatreCreateDTO dto);

    @Mapping(target = "partnerId", source = "partner.id")
    TheatreDTO toDTO(Theatre theatre);
}
