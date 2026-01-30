package com.ajeet.bookmyshow.mapper;

import com.ajeet.bookmyshow.dto.PartnerCreateDTO;
import com.ajeet.bookmyshow.dto.PartnerDTO;
import com.ajeet.bookmyshow.model.Partner;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerMapperTest {
    private final PartnerMapper mapper = Mappers.getMapper(PartnerMapper.class);

    @Test
    void toEntity_and_toDTO_basicFields() {
        PartnerCreateDTO create = new PartnerCreateDTO();
        create.setName("PartnerCo");
        create.setContactEmail("contact@partner.co");

        Partner entity = mapper.toEntity(create);
        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo("PartnerCo");
        assertThat(entity.getContactEmail()).isEqualTo("contact@partner.co");

        PartnerDTO dto = mapper.toDTO(entity);
        assertThat(dto.getName()).isEqualTo("PartnerCo");
        assertThat(dto.getContactEmail()).isEqualTo("contact@partner.co");
    }
}
