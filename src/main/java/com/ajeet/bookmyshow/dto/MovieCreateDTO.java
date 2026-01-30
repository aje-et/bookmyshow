package com.ajeet.bookmyshow.dto;

import jakarta.validation.constraints.NotBlank;

public class MovieCreateDTO {
    @NotBlank
    private String title;

    private String language;
    private String genres; // comma separated

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }
}
