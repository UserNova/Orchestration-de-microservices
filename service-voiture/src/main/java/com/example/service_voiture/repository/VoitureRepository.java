package com.example.service_voiture.repository;

import com.example.service_voiture.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    // Cette méthode est nécessaire pour findByClientId
    List<Voiture> findByClientId(Long clientId);
}
