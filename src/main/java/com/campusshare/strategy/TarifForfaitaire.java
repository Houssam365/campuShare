package com.campusshare.strategy;

import java.time.Duration;

/**
 * Stratégie de tarification forfaitaire (prix fixe).
 * Le prix est fixe quelle que soit la durée.
 * Idéale pour les services à prestation unique (déménagement, trajet covoiturage, etc.)
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class TarifForfaitaire implements StrategyTarification {
    
    /**
     * Calcule le prix forfaitaire (simplement le prix de base).
     * La durée n'affecte pas le prix.
     * 
     * @param prixBase Le forfait
     * @param duree Ignorée
     * @return Le prix forfaitaire
     */
    @Override
    public double calculerPrix(double prixBase, Duration duree) {
        return prixBase;
    }
    
    @Override
    public String getNom() {
        return "Tarif Forfaitaire";
    }
    
    @Override
    public String getDescription() {
        return "Prix fixe quelle que soit la durée - Idéal pour prestations uniques";
    }
    
    @Override
    public String toString() {
        return "💰 " + getNom();
    }
}
