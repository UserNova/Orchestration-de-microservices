package com.example.service_voiture.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;
    private String matricule;
    private String model;

    // Nom exact du champ persistant = clientId
    private Long clientId;

    @Transient
    @ManyToOne
    private Client client;
}
