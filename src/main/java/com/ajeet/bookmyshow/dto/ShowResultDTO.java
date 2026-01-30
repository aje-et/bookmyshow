package com.ajeet.bookmyshow.dto;

import java.math.BigDecimal;

public class ShowResultDTO {
    private Long showId;
    private String startTime; // ISO-8601
    private BigDecimal price;
    private int availableSeats;
    private int totalSeats;
    private String bookingUrl;

    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public String getBookingUrl() { return bookingUrl; }
    public void setBookingUrl(String bookingUrl) { this.bookingUrl = bookingUrl; }
}
