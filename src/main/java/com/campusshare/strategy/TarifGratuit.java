package com.campusshare.strategy;

import java.time.Duration;

/**
 * Stratégie de tarification gratuite.
 * Utilisée pour les prêts solidaires entre étudiants et les dons.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class TarifGratuit implements StrategyTarification {
    
    /**
     * Le prix est toujours 0, quelle que soit la durée.
     * 
     * @param prixBase Ignoré
     * @param duree Ignorée
     * @return Toujours 0.0
     */
    @Override
    public double calculerPrix(double prixBase, Duration duree) {
        return 0.0;
    }
    
    @Override
    public String getNom() {
        return "Gratuit";
    }
    
    @Override
    public String getDescription() {
        return "Prêt ou don gratuit - Partage solidaire entre étudiants";
    }
    
    @Override
    public String toString() {
        return "🆓 " + getNom();
    }
}
