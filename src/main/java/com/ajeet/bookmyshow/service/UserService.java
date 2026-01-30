package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.model.User;
import java.util.List;

public interface UserService {
    User create(User user);
    List<User> listAll();
}
