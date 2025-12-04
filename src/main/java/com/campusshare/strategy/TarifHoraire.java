package com.campusshare.strategy;

import java.time.Duration;

/**
 * Stratégie de tarification à l'heure.
 * Idéale pour les services ponctuels (cours particuliers, aide, etc.)
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class TarifHoraire implements StrategyTarification {
    
    private double tauxHoraire; // Prix supplémentaire par heure au-delà de la première
    
    /**
     * Constructeur avec taux horaire personnalisé.
     * 
     * @param tauxHoraire Prix par heure supplémentaire
     */
    public TarifHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }
    
    /**
     * Constructeur par défaut (taux = prix de base).
     */
    public TarifHoraire() {
        this.tauxHoraire = 1.0; // Multiplicateur par défaut
    }
    
    /**
     * Calcule le prix: prix de base × nombre d'heures × taux.
     * Minimum 1 heure facturée.
     * 
     * @param prixBase Prix de base par heure
     * @param duree Durée de la réservation
     * @return Prix total
     */
    @Override
    public double calculerPrix(double prixBase, Duration duree) {
        long heures = duree.toHours();
        if (heures < 1) heures = 1; // Minimum 1 heure
        return prixBase * heures * tauxHoraire;
    }
    
    @Override
    public String getNom() {
        return "Tarif Horaire";
    }
    
    @Override
    public String getDescription() {
        return String.format("Facturation à l'heure (taux: x%.1f)", tauxHoraire);
    }
    
    public double getTauxHoraire() {
        return tauxHoraire;
    }
    
    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }
    
    @Override
    public String toString() {
        return "⏰ " + getNom();
    }
}
