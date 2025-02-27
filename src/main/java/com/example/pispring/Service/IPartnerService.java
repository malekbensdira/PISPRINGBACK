package com.example.pispring.Service;

import com.example.pispring.Entities.Partner;

import java.util.List;
import java.util.Optional;

public interface IPartnerService {
    List<Partner> getAllPartners();
    Optional<Partner> getPartnerById(int id);
    Partner addPartnerToEvent(int eventId, Partner partner) ;
    Partner updatePartner(Partner partner);
    void deletePartner(int id);
}
