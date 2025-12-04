package com.campusshare.service;

import com.campusshare.model.annonce.Annonce;
import com.campusshare.strategy.ITriStrategy;
import java.util.List;

public class MoteurRecherche {
    private ITriStrategy strategy;

    public void setStrategy(ITriStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Annonce> executerRecherche(List<Annonce> catalogue) {
        if (strategy == null) {
            return catalogue; // Pas de tri
        }
        System.out.println("🔍 Recherche avec stratégie: " + strategy.getNom());
        return strategy.trier(catalogue);
    }
} 