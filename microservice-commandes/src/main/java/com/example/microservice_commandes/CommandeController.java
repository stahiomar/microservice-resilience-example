package com.example.microservice_commandes;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController()
@RequestMapping("commandes")
public class CommandeController {
    @Autowired
    private CommandeRepository commandeRepository;
    private final WebClient.Builder webClientBuilder;

    public CommandeController(CommandeRepository commandeRepository, @LoadBalanced WebClient.Builder webClientBuilder) {
        this.commandeRepository = commandeRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("create")
    public Commande createCommande(@RequestBody Commande commande) {
        commandeRepository.save(commande);
        return commande;
    }

    @GetMapping("/produits")
    @CircuitBreaker(name = "produits", fallbackMethod = "fallbackProduits")
    public List<Object> getProduits() {
        return webClientBuilder.build()
                .get()
                .uri("http://microservice-produits/produits")
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    public List<Object> fallbackProduits(Throwable throwable) {
        return List.of("Service unavailable, try again later");
    }
}
