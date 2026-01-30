package com.ajeet.bookmyshow.mapper;

import com.ajeet.bookmyshow.dto.TheatreCreateDTO;
import com.ajeet.bookmyshow.dto.TheatreDTO;
import com.ajeet.bookmyshow.model.Theatre;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class TheatreMapperTest {
    private final TheatreMapper mapper = Mappers.getMapper(TheatreMapper.class);

    @Test
    void toEntity_and_toDTO_roundTrip_partnerIdMapping() {
        TheatreCreateDTO create = new TheatreCreateDTO();
        create.setName("Cineplex");
        create.setCity("CityX");
        create.setPartnerId(42L);

        Theatre theatre = mapper.toEntity(create);
        assertThat(theatre).isNotNull();
        assertThat(theatre.getName()).isEqualTo("Cineplex");
        assertThat(theatre.getCity()).isEqualTo("CityX");
        // partner id should be applied to theatre.partner.id (mapstruct will create nested object if null)
        assertThat(theatre.getPartner()).isNotNull();
        assertThat(theatre.getPartner().getId()).isEqualTo(42L);

        TheatreDTO dto = mapper.toDTO(theatre);
        assertThat(dto.getPartnerId()).isEqualTo(42L);
    }
}
