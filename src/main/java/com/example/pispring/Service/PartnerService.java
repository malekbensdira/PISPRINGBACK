package com.example.pispring.Service;

import com.example.pispring.Entities.*;
import com.example.pispring.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PartnerService implements IPartnerService{

    @Autowired
    private  PartnerRepository partnerRepository;

    @Autowired
    private DonationEventRepository eventRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository; }
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll(); }
    public Optional<Partner> getPartnerById(int id) {
        return partnerRepository.findById(id); }
    public Partner addPartnerToEvent(int eventId, Partner partner) {
        DonationEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        partner.setEvent(event);  // Associe le partenaire à l'event
        return partnerRepository.save(partner);}
    public Partner updatePartner(Partner partner) {
        return partnerRepository.save(partner); }
    public void deletePartner(int id) {
        partnerRepository.deleteById(id); }
}
