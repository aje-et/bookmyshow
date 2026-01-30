package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.model.Partner;
import com.ajeet.bookmyshow.repository.PartnerRepository;
import com.ajeet.bookmyshow.service.PartnerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public Partner create(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public List<Partner> listAll() {
        return partnerRepository.findAll();
    }
}
