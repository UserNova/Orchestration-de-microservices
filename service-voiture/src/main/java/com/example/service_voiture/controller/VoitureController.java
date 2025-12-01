package com.example.service_voiture.web;

import com.example.service_voiture.feign.ClientService;
import com.example.service_voiture.model.Client;
import com.example.service_voiture.model.Voiture;
import com.example.service_voiture.repository.VoitureRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoitureController {

    private final VoitureRepository voitureRepository;
    private final ClientService clientService;

    public VoitureController(VoitureRepository voitureRepository, ClientService clientService) {
        this.voitureRepository = voitureRepository;
        this.clientService = clientService;
    }

    @GetMapping("/voitures")
    public List<Voiture> getAllVoitures() {

        List<Voiture> voitures = voitureRepository.findAll();

        // Remplir le client pour chaque voiture
        voitures.forEach(v -> {
            Client client = clientService.clientById(v.getId_client());
            v.setClient(client);
        });

        return voitures;
    }
}
