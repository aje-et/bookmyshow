package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.model.Theatre;
import java.util.List;

public interface TheatreService {
    Theatre create(Theatre theatre);
    List<Theatre> listAll();
}
