package com.campusshare.strategy;

import com.campusshare.model.annonce.Annonce;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TriParDate implements ITriStrategy {
    @Override
    public String getNom() {
        return "Tri par date (Plus récent)";
    }

    @Override
    public List<Annonce> trier(List<Annonce> annonces) {
        return annonces.stream()
                .sorted(Comparator.comparing(Annonce::getDateCreation).reversed())
                .collect(Collectors.toList());
    }
}