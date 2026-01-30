package com.ajeet.bookmyshow.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResponseDTO {
    private Long movieId;
    private String movieTitle;
    private String date; // yyyy-MM-dd
    private String city;
    private int page;
    private int size;
    private long totalResults;
    private List<TheatreSearchResultDTO> results = new ArrayList<>();

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public long getTotalResults() { return totalResults; }
    public void setTotalResults(long totalResults) { this.totalResults = totalResults; }
    public List<TheatreSearchResultDTO> getResults() { return results; }
    public void setResults(List<TheatreSearchResultDTO> results) { this.results = results; }
}
