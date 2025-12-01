package com.example.service_voiture;

import com.example.service_voiture.feign.ClientService;
import com.example.service_voiture.model.Client;
import com.example.service_voiture.model.Voiture;
import com.example.service_voiture.repository.VoitureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ServiceVoitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVoitureApplication.class, args);
    }

    @Bean
    CommandLineRunner initialiserBaseH2(VoitureRepository voitureRepository, ClientService clientService) {
        return args -> {
            // Récupération des clients depuis SERVICE-CLIENT
            Client c1 = clientService.clientById(2L);
            Client c2 = clientService.clientById(1L);

            System.out.println("**************************");
            System.out.println("Id est :" + c2.getId());
            System.out.println("Nom est :" + c2.getNom());
            System.out.println("**************************");
            System.out.println("Id est :" + c1.getId());
            System.out.println("Nom est :" + c1.getNom());
            System.out.println("Age est :" + c1.getAge());
            System.out.println("**************************");

            // Création des voitures avec id_client et client rempli pour JSON
            Voiture v1 = new Voiture(null, "Toyota", "A 25 333", "Corolla", c2.getId(), c2);
            Voiture v2 = new Voiture(null, "Renault", "B 6 3456", "Megane", c2.getId(), c2);
            Voiture v3 = new Voiture(null, "Peugeot", "A 55 4444", "301", c1.getId(), c1);

            // Sauvegarde dans la base H2
            voitureRepository.save(v1);
            voitureRepository.save(v2);
            voitureRepository.save(v3);

            // Affichage pour vérification
            System.out.println(v1);
            System.out.println(v2);
            System.out.println(v3);
        };
    }
}
