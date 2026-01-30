package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.model.Partner;
import java.util.List;

public interface PartnerService {
    Partner create(Partner partner);
    List<Partner> listAll();
}
