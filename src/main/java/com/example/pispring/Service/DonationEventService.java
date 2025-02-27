package com.example.pispring.Service;

import com.example.pispring.Entities.*;
import com.example.pispring.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DonationEventService implements IEventService {

    @Autowired
    private DonationEventRepository donationEventRepository;

    // Injection du repository via le constructeur
    public DonationEventService(DonationEventRepository donationEventRepository) {
        this.donationEventRepository = donationEventRepository;
    }

    @Override
    public List<DonationEvent> getAllDonationEvents() {
        return donationEventRepository.findAll();
    }

    @Override
    public Optional<DonationEvent> getDonationEventById(int id) {
        return donationEventRepository.findById(id);
    }

    @Override
    public DonationEvent saveDonationEvent(DonationEvent donationEvent) {
        return donationEventRepository.save(donationEvent);
    }

    @Override
    public DonationEvent updateDonationEvent(DonationEvent donationEvent) {
        return donationEventRepository.save(donationEvent);
    }

    @Override
    public void deleteDonationEvent(int id) {
        donationEventRepository.deleteById(id);
    }
}
