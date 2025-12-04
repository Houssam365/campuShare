package com.campusshare.strategy;

import com.campusshare.model.annonce.Annonce;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TriParPrix implements ITriStrategy {
    private boolean croissant;

    public TriParPrix(boolean croissant) {
        this.croissant = croissant;
    }

    @Override
    public String getNom() {
        return "Tri par prix " + (croissant ? "(Croissant)" : "(Décroissant)");
    }

    @Override
    public List<Annonce> trier(List<Annonce> annonces) {
        Comparator<Annonce> comparator = Comparator.comparingDouble(Annonce::getPrixEstime);
        if (!croissant) {
            comparator = comparator.reversed();
        }
        return annonces.stream().sorted(comparator).collect(Collectors.toList());
    }
}