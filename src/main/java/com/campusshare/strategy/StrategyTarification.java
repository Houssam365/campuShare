package com.campusshare.strategy;

import java.time.Duration;

/**
 * Interface Strategy pour le calcul du prix des réservations.
 * 
 * Pattern Strategy: permet de définir une famille d'algorithmes de tarification,
 * de les encapsuler et de les rendre interchangeables.
 * 
 * Avantages:
 * - Facile d'ajouter de nouvelles stratégies sans modifier le code existant
 * - Le code client (Reservation) reste simple et ne connaît pas les détails
 * - Respect du principe Open/Closed (ouvert à l'extension, fermé à la modification)
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public interface StrategyTarification {
    
    /**
     * Calcule le prix total d'une réservation.
     * 
     * @param prixBase Le prix de base de l'annonce
     * @param duree La durée de la réservation
     * @return Le prix total calculé
     */
    double calculerPrix(double prixBase, Duration duree);
    
    /**
     * Retourne le nom de la stratégie de tarification.
     * 
     * @return Le nom descriptif de la stratégie
     */
    String getNom();
    
    /**
     * Retourne une description de la stratégie.
     * 
     * @return La description
     */
    String getDescription();
}
