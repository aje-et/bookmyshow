package com.ajeet.bookmyshow.mapper;

import com.ajeet.bookmyshow.dto.PartnerCreateDTO;
import com.ajeet.bookmyshow.dto.PartnerDTO;
import com.ajeet.bookmyshow.model.Partner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    Partner toEntity(PartnerCreateDTO dto);
    PartnerDTO toDTO(Partner partner);
}
