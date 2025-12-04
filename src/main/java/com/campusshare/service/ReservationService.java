package com.campusshare.service;

import com.campusshare.adapter.Calendrier;
import com.campusshare.model.*;
import com.campusshare.model.annonce.Annonce;
import com.campusshare.strategy.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service de gestion des réservations.
 * 
 * Utilise:
 * - Pattern Strategy pour le calcul des prix
 * - Pattern Adapter pour l'intégration calendrier
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class ReservationService {
    
    private List<Reservation> reservations;
    private Calendrier calendrier; // Pattern Adapter - optionnel
    
    /**
     * Constructeur du service.
     */
    public ReservationService() {
        this.reservations = new ArrayList<>();
    }
    
    /**
     * Constructeur avec calendrier externe.
     * 
     * @param calendrier Adaptateur de calendrier
     */
    public ReservationService(Calendrier calendrier) {
        this();
        this.calendrier = calendrier;
    }
    
    // ==================== Création de réservations ====================
    
    /**
     * Crée une réservation avec une stratégie de tarification spécifique.
     * Pattern Strategy en action.
     * 
     * @param annonce L'annonce à réserver
     * @param demandeur L'utilisateur qui réserve
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @param strategy Stratégie de tarification
     * @return La réservation créée
     */
    public Reservation creerReservation(Annonce annonce, Utilisateur demandeur,
                                         LocalDateTime dateDebut, LocalDateTime dateFin,
                                         StrategyTarification strategy) {
        // Validation
        if (!annonce.estDisponible()) {
            throw new IllegalStateException("Cette annonce n'est pas disponible");
        }
        
        if (demandeur.equals(annonce.getProprietaire())) {
            throw new IllegalArgumentException("Vous ne pouvez pas réserver votre propre annonce");
        }
        
        // Création de la réservation avec la stratégie
        String id = genererIdReservation();
        Reservation reservation = new Reservation(id, annonce, demandeur, 
                dateDebut, dateFin, strategy);
        
        reservations.add(reservation);
        demandeur.getReservationsEffectuees().add(reservation);
        
        // Notification au propriétaire via le pattern Observer (si configuré)
        annonce.notifyObservers(String.format(
                "Nouvelle demande de réservation de %s pour '%s'",
                demandeur.getNomComplet(),
                annonce.getTitre()
        ));
        
        System.out.println("✅ Réservation créée: " + id);
        System.out.println("   Stratégie: " + strategy.getNom());
        System.out.println("   Prix calculé: " + String.format("%.2f€", reservation.getPrixTotal()));
        
        return reservation;
    }
    
    /**
     * Crée une réservation avec tarif horaire.
     */
    public Reservation reserverAvecTarifHoraire(Annonce annonce, Utilisateur demandeur,
                                                 LocalDateTime dateDebut, LocalDateTime dateFin) {
        return creerReservation(annonce, demandeur, dateDebut, dateFin, new TarifHoraire());
    }
    
    /**
     * Crée une réservation avec tarif journalier.
     */
    public Reservation reserverAvecTarifJournalier(Annonce annonce, Utilisateur demandeur,
                                                    LocalDateTime dateDebut, LocalDateTime dateFin) {
        return creerReservation(annonce, demandeur, dateDebut, dateFin, new TarifJournalier());
    }
    
    /**
     * Crée une réservation gratuite.
     */
    public Reservation reserverGratuit(Annonce annonce, Utilisateur demandeur,
                                        LocalDateTime dateDebut, LocalDateTime dateFin) {
        return creerReservation(annonce, demandeur, dateDebut, dateFin, new TarifGratuit());
    }
    
    /**
     * Crée une réservation forfaitaire.
     */
    public Reservation reserverForfait(Annonce annonce, Utilisateur demandeur,
                                        LocalDateTime dateDebut, LocalDateTime dateFin) {
        return creerReservation(annonce, demandeur, dateDebut, dateFin, new TarifForfaitaire());
    }
    
    // ==================== Gestion du cycle de vie ====================
    
    /**
     * Confirme une réservation et l'ajoute au calendrier si disponible.
     */
    public void confirmerReservation(Reservation reservation) {
        reservation.confirmer();
        
        // Marquer l'annonce comme réservée
        reservation.getAnnonce().changerStatut(StatutAnnonce.RESERVEE);
        
        // Intégration calendrier via l'Adapter
        if (calendrier != null) {
            calendrier.ajouterEvenement(reservation);
        }
        
        // Notification
        reservation.getAnnonce().notifyObservers(
                "Réservation confirmée pour: " + reservation.getAnnonce().getTitre()
        );
        
        System.out.println("✅ Réservation confirmée: " + reservation.getId());
    }
    
    /**
     * Démarre une réservation (le bien est remis / le service commence).
     */
    public void demarrerReservation(Reservation reservation) {
        reservation.demarrer();
        System.out.println("🔄 Réservation démarrée: " + reservation.getId());
    }
    
    /**
     * Termine une réservation.
     */
    public void terminerReservation(Reservation reservation) {
        reservation.terminer();
        
        // Rendre l'annonce à nouveau disponible
        reservation.getAnnonce().changerStatut(StatutAnnonce.ACTIVE);
        
        // Supprimer du calendrier
        if (calendrier != null) {
            calendrier.supprimerEvenement(reservation.getId());
        }
        
        System.out.println("✔️ Réservation terminée: " + reservation.getId());
    }
    
    /**
     * Annule une réservation.
     */
    public void annulerReservation(Reservation reservation) {
        reservation.annuler();
        
        // Rendre l'annonce disponible si elle était réservée
        if (reservation.getAnnonce().getStatut() == StatutAnnonce.RESERVEE) {
            reservation.getAnnonce().changerStatut(StatutAnnonce.ACTIVE);
        }
        
        // Supprimer du calendrier
        if (calendrier != null) {
            calendrier.supprimerEvenement(reservation.getId());
        }
        
        // Notification
        reservation.getAnnonce().notifyObservers(
                "Réservation annulée pour: " + reservation.getAnnonce().getTitre()
        );
        
        System.out.println("❌ Réservation annulée: " + reservation.getId());
    }
    
    /**
     * Refuse une réservation.
     */
    public void refuserReservation(Reservation reservation) {
        reservation.refuser();
        System.out.println("🚫 Réservation refusée: " + reservation.getId());
    }
    
    // ==================== Recherche ====================
    
    /**
     * Trouve une réservation par son ID.
     */
    public Reservation trouverParId(String id) {
        return reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retourne les réservations d'un demandeur.
     */
    public List<Reservation> getReservationsDemandeur(Utilisateur demandeur) {
        return reservations.stream()
                .filter(r -> r.getDemandeur().equals(demandeur))
                .collect(Collectors.toList());
    }
    
    /**
     * Retourne les réservations pour les annonces d'un propriétaire.
     */
    public List<Reservation> getReservationsProprietaire(Utilisateur proprietaire) {
        return reservations.stream()
                .filter(r -> r.getProprietaire().equals(proprietaire))
                .collect(Collectors.toList());
    }
    
    /**
     * Retourne les réservations en attente pour un propriétaire.
     */
    public List<Reservation> getReservationsEnAttente(Utilisateur proprietaire) {
        return reservations.stream()
                .filter(r -> r.getProprietaire().equals(proprietaire))
                .filter(r -> r.getStatut() == StatutReservation.EN_ATTENTE)
                .collect(Collectors.toList());
    }
    
    /**
     * Retourne les réservations par statut.
     */
    public List<Reservation> getReservationsParStatut(StatutReservation statut) {
        return reservations.stream()
                .filter(r -> r.getStatut() == statut)
                .collect(Collectors.toList());
    }
    
    // ==================== Utilitaires ====================
    
    /**
     * Génère un ID unique pour une réservation.
     */
    private String genererIdReservation() {
        return "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * Change la stratégie de tarification d'une réservation et recalcule le prix.
     */
    public void changerStrategie(Reservation reservation, StrategyTarification nouvelleStrategy) {
        System.out.println("🔄 Changement de stratégie de tarification...");
        System.out.println("   Ancienne stratégie: " + reservation.getStrategyTarification().getNom());
        System.out.println("   Ancien prix: " + String.format("%.2f€", reservation.getPrixTotal()));
        
        reservation.setStrategyTarification(nouvelleStrategy);
        
        System.out.println("   Nouvelle stratégie: " + nouvelleStrategy.getNom());
        System.out.println("   Nouveau prix: " + String.format("%.2f€", reservation.getPrixTotal()));
    }
    
    // ==================== Getters/Setters ====================
    
    public List<Reservation> getReservations() {
        return reservations;
    }
    
    public Calendrier getCalendrier() {
        return calendrier;
    }
    
    public void setCalendrier(Calendrier calendrier) {
        this.calendrier = calendrier;
    }
    
    public int getNombreReservations() {
        return reservations.size();
    }
}
