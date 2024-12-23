package com.example.microservice_products;

import jakarta.persistence.*;
import lombok.Data;

@Entity

@Data
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double prix;
}

