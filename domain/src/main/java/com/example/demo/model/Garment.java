package com.example.demo.model;

import lombok.Value;

@Value
public class Garment {
    String category;       // p.ej. "jacket", "sneakers"
    String description;    // texto libre
    String shopLink;       // opcional (puede ser null)
}