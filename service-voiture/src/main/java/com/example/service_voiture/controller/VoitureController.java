package com.example.service_voiture.controller;

import com.example.service_voiture.feign.ClientService;
import com.example.service_voiture.model.Client;
import com.example.service_voiture.model.Voiture;
import com.example.service_voiture.repository.VoitureRepository;
import com.example.service_voiture.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private VoitureService voitureService;

    @Autowired
    private ClientService clientService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Voiture>> findAll() {
        try {
            List<Voiture> voitures = voitureRepository.findAll();

            // Remplir le champ client pour chaque voiture
            for (Voiture v : voitures) {
                v.setClient(clientService.clientById(v.getClientId()));
            }

            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voiture> findById(@PathVariable Long id) {
        try {
            Voiture voiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture Introuvable"));

            voiture.setClient(clientService.clientById(voiture.getClientId()));
            return ResponseEntity.ok(voiture);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
        try {
            Client client = clientService.clientById(id);
            if (client != null) {
                List<Voiture> voitures = voitureRepository.findByClientId(id);

                // Remplir le champ client pour chaque voiture
                for (Voiture v : voitures) {
                    v.setClient(client);
                }

                return ResponseEntity.ok(voitures);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<Voiture> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
        try {
            Client client = clientService.clientById(clientId);

            if (client != null) {
                voiture.setClient(client);
                voiture.setClientId(clientId);
                Voiture savedVoiture = voitureService.enregistrerVoiture(voiture);
                return ResponseEntity.ok(savedVoiture);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voiture> update(@PathVariable Long id, @RequestBody Voiture updatedVoiture) {
        try {
            Voiture existingVoiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture not found with ID: " + id));

            if (updatedVoiture.getMatricule() != null && !updatedVoiture.getMatricule().isEmpty()) {
                existingVoiture.setMatricule(updatedVoiture.getMatricule());
            }
            if (updatedVoiture.getMarque() != null && !updatedVoiture.getMarque().isEmpty()) {
                existingVoiture.setMarque(updatedVoiture.getMarque());
            }
            if (updatedVoiture.getModel() != null && !updatedVoiture.getModel().isEmpty()) {
                existingVoiture.setModel(updatedVoiture.getModel());
            }

            Voiture savedVoiture = voitureRepository.save(existingVoiture);
            savedVoiture.setClient(clientService.clientById(savedVoiture.getClientId()));
            return ResponseEntity.ok(savedVoiture);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
