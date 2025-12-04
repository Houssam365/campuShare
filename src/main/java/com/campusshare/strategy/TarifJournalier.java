package com.campusshare.strategy;

import java.time.Duration;

/**
 * Stratégie de tarification à la journée.
 * Idéale pour la location de biens (vélos, matériel, etc.)
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class TarifJournalier implements StrategyTarification {
    
    private double reductionSemaine; // Réduction si >= 7 jours
    
    /**
     * Constructeur avec réduction personnalisée.
     * 
     * @param reductionSemaine Pourcentage de réduction pour les locations longues (0.0 à 1.0)
     */
    public TarifJournalier(double reductionSemaine) {
        this.reductionSemaine = reductionSemaine;
    }
    
    /**
     * Constructeur par défaut (20% de réduction pour une semaine).
     */
    public TarifJournalier() {
        this.reductionSemaine = 0.20; // 20% de réduction
    }
    
    /**
     * Calcule le prix: prix de base × nombre de jours.
     * Applique une réduction si durée >= 7 jours.
     * Minimum 1 jour facturé.
     * 
     * @param prixBase Prix de base par jour
     * @param duree Durée de la réservation
     * @return Prix total
     */
    @Override
    public double calculerPrix(double prixBase, Duration duree) {
        long jours = duree.toDays();
        if (jours < 1) jours = 1; // Minimum 1 jour
        
        double prixTotal = prixBase * jours;
        
        // Réduction pour location longue durée
        if (jours >= 7) {
            prixTotal *= (1 - reductionSemaine);
        }
        
        return Math.round(prixTotal * 100.0) / 100.0; // Arrondi à 2 décimales
    }
    
    @Override
    public String getNom() {
        return "Tarif Journalier";
    }
    
    @Override
    public String getDescription() {
        return String.format("Facturation à la journée (-%d%% si >= 7 jours)", 
                (int)(reductionSemaine * 100));
    }
    
    public double getReductionSemaine() {
        return reductionSemaine;
    }
    
    public void setReductionSemaine(double reductionSemaine) {
        this.reductionSemaine = reductionSemaine;
    }
    
    @Override
    public String toString() {
        return "📅 " + getNom();
    }
}
