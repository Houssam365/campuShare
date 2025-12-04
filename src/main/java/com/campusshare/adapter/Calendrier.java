package com.campusshare.adapter;

import com.campusshare.model.Reservation;

/**
 * Interface cible pour la gestion des calendriers.
 * Définit les opérations que l'application attend d'un calendrier.
 * 
 * Pattern Adapter: Cette interface représente le contrat que notre
 * application utilise, indépendamment de l'implémentation concrète
 * du système de calendrier externe.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public interface Calendrier {
    
    /**
     * Ajoute une réservation au calendrier.
     * 
     * @param reservation La réservation à ajouter
     * @return true si l'ajout a réussi
     */
    boolean ajouterEvenement(Reservation reservation);
    
    /**
     * Supprime une réservation du calendrier.
     * 
     * @param reservationId L'ID de la réservation à supprimer
     * @return true si la suppression a réussi
     */
    boolean supprimerEvenement(String reservationId);
    
    /**
     * Modifie une réservation existante.
     * 
     * @param reservation La réservation modifiée
     * @return true si la modification a réussi
     */
    boolean modifierEvenement(Reservation reservation);
    
    /**
     * Vérifie si un créneau est disponible.
     * 
     * @param reservation La réservation à vérifier
     * @return true si le créneau est disponible
     */
    boolean verifierDisponibilite(Reservation reservation);
}
