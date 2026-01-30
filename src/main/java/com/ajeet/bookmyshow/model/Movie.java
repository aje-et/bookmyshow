package com.ajeet.bookmyshow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String language;
    private String genres; // comma separated for simplicity

    public Movie() {}

    public Movie(String title, String language, String genres) {
        this.title = title;
        this.language = language;
        this.genres = genres;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }
}
