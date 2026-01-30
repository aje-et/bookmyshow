package com.ajeet.bookmyshow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeatCreateDTO {
    @NotBlank
    private String label;

    @NotBlank
    private String type; // REGULAR, PREMIUM, VIP

    @NotNull
    private Long auditoriumId;

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getAuditoriumId() { return auditoriumId; }
    public void setAuditoriumId(Long auditoriumId) { this.auditoriumId = auditoriumId; }
}
