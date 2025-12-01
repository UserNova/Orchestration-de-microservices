package com.example.service_voiture.service;

import com.example.service_voiture.model.Voiture;
import com.example.service_voiture.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoitureService {

    @Autowired
    private VoitureRepository voitureRepository;

    public Voiture enregistrerVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    public Voiture getVoiture(Long id) throws Exception {
        return voitureRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture non trouvée avec ID : " + id));
    }

    public Voiture updateVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    public void deleteVoiture(Long id) {
        voitureRepository.deleteById(id);
    }
}
