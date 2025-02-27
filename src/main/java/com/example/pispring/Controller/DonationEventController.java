package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Service.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/donationEvent")
public class DonationEventController {
    private final DonationEventService donationEventService;
    public DonationEventController(DonationEventService donationEventService) { this.donationEventService = donationEventService; }
    @GetMapping
    public List<DonationEvent> getAllDonationEvents() {
        return donationEventService.getAllDonationEvents(); }
    @GetMapping("/{id}")
    public Optional<DonationEvent> getDonationEventById(@PathVariable int id) {
        return donationEventService.getDonationEventById(id); }
    @PostMapping("/add")
    public DonationEvent createDonationEvent(@RequestBody DonationEvent donationEvent) {
        return donationEventService.saveDonationEvent(donationEvent); }
    @PutMapping("/update/{idEvent}")
    public DonationEvent updateDonationEvent(@RequestBody DonationEvent donationEvent, @PathVariable int idEvent) {
        donationEvent.setIdEvent(idEvent);
        return donationEventService.updateDonationEvent(donationEvent); }
    @DeleteMapping("/delete/{id}")
    public void deleteDonationEvent(@PathVariable int id) {
        donationEventService.deleteDonationEvent(id); }
}