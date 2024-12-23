package com.example.microservice_products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produits")
public class ControllerProduit {
    @Autowired
    private ProduitRepository produitRepository;


    @GetMapping
    public List<Produit> getDelayedProduits() throws InterruptedException {
        Thread.sleep(5000); // Simulate delay of 5 seconds
        return produitRepository.findAll();
    }

    @PostMapping("create")
    public Produit createCommande(@RequestBody Produit produit) {
        produitRepository.save(produit);
        return produit;
    }

}
