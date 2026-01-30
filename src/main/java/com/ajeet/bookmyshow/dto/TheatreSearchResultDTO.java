package com.ajeet.bookmyshow.dto;

import java.util.ArrayList;
import java.util.List;

public class TheatreSearchResultDTO {
    private Long theatreId;
    private String name;
    private String address;
    private String city;
    private List<AuditoriumResultDTO> auditoriums = new ArrayList<>();

    public Long getTheatreId() { return theatreId; }
    public void setTheatreId(Long theatreId) { this.theatreId = theatreId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public List<AuditoriumResultDTO> getAuditoriums() { return auditoriums; }
    public void setAuditoriums(List<AuditoriumResultDTO> auditoriums) { this.auditoriums = auditoriums; }
}
