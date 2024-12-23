package com.example.microservice_commandes;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CommandeHealthIndicator implements HealthIndicator {

    private final CommandeRepository commandeRepository;

    public CommandeHealthIndicator(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Health health() {
        boolean hasCommandes = commandeRepository.count() > 0;
        if (hasCommandes) {
            return Health.up().build();
        } else {
            return Health.down().withDetail("Erreur", "Aucune commande trouvée").build();
        }
    }
}
