package com.ajeet.bookmyshow.mapper;

import com.ajeet.bookmyshow.dto.AuditoriumCreateDTO;
import com.ajeet.bookmyshow.dto.AuditoriumDTO;
import com.ajeet.bookmyshow.model.Auditorium;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditoriumMapper {

    @Mapping(target = "theatre.id", source = "theatreId")
    Auditorium toEntity(AuditoriumCreateDTO dto);

    @Mapping(target = "theatreId", source = "theatre.id")
    AuditoriumDTO toDTO(Auditorium auditorium);
}
