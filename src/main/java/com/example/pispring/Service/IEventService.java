package com.example.pispring.Service;

import com.example.pispring.Entities.DonationEvent;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<DonationEvent> getAllDonationEvents();
    Optional<DonationEvent> getDonationEventById(int id);
    DonationEvent saveDonationEvent(DonationEvent donationEvent);
    DonationEvent updateDonationEvent(DonationEvent donationEvent);
    void deleteDonationEvent(int id);
}
