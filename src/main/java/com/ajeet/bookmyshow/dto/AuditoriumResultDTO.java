package com.ajeet.bookmyshow.dto;

import java.util.ArrayList;
import java.util.List;

public class AuditoriumResultDTO {
    private Long auditoriumId;
    private String name;
    private String format; // optional
    private List<ShowResultDTO> shows = new ArrayList<>();

    public Long getAuditoriumId() { return auditoriumId; }
    public void setAuditoriumId(Long auditoriumId) { this.auditoriumId = auditoriumId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    public List<ShowResultDTO> getShows() { return shows; }
    public void setShows(List<ShowResultDTO> shows) { this.shows = shows; }
}
