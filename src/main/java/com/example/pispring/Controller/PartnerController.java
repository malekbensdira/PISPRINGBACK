package com.example.pispring.Controller;

import com.example.pispring.Entities.*;
import com.example.pispring.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partner")
public class PartnerController {
    @Autowired
    private  PartnerService partnerService;
    public PartnerController(PartnerService partnerService) { this.partnerService = partnerService; }

    @GetMapping
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners(); }
    @GetMapping("/{id}")
    public Optional<Partner> getPartnerById(@PathVariable int id) {
        return partnerService.getPartnerById(id); }
    @PostMapping ("/add/{eventId}")
    public Partner createPartner(@RequestBody Partner partner, @PathVariable int eventId) {
        return partnerService.addPartnerToEvent(eventId, partner); }
    @PutMapping ("/update/{id}")
    public Partner updatePartner(@RequestBody Partner partner,@PathVariable int idPartner) {
        partner.setIdPartner(idPartner);
        return partnerService.updatePartner(partner); }
    @DeleteMapping("/delete/{id}")
    public void deletePartner(@PathVariable int id) {
        partnerService.deletePartner(id); }
}

