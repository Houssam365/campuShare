package com.campusshare.model;

import com.campusshare.model.annonce.Annonce;
import com.campusshare.strategy.StrategyTarification;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Classe représentant une réservation d'un bien ou service.
 * Utilise le pattern Strategy pour le calcul du prix.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class Reservation {
    
    private String id;
    private Annonce annonce;
    private Utilisateur demandeur;       // Celui qui fait la demande
    private Utilisateur proprietaire;    // Propriétaire de l'annonce
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime dateCreation;
    private StatutReservation statut;
    private double prixTotal;
    private String messageAccompagnement;
    private StrategyTarification strategyTarification; // Pattern Strategy
    
    /**
     * Constructeur de la réservation.
     * 
     * @param id Identifiant unique
     * @param annonce L'annonce concernée
     * @param demandeur L'utilisateur qui demande
     * @param dateDebut Date de début de la réservation
     * @param dateFin Date de fin de la réservation
     * @param strategyTarification Stratégie de calcul du prix
     */
    public Reservation(String id, Annonce annonce, Utilisateur demandeur,
                       LocalDateTime dateDebut, LocalDateTime dateFin,
                       StrategyTarification strategyTarification) {
        this.id = id;
        this.annonce = annonce;
        this.demandeur = demandeur;
        this.proprietaire = annonce.getProprietaire();
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateCreation = LocalDateTime.now();
        this.statut = StatutReservation.EN_ATTENTE;
        this.strategyTarification = strategyTarification;
        this.prixTotal = calculerPrix();
    }
    
    /**
     * Calcule le prix total de la réservation en utilisant la stratégie de tarification.
     * Pattern Strategy en action.
     * 
     * @return Le prix calculé
     */
    public double calculerPrix() {
        if (strategyTarification == null) {
            return 0.0;
        }
        Duration duree = Duration.between(dateDebut, dateFin);
        return strategyTarification.calculerPrix(annonce.getPrixBase(), duree);
    }
    
    /**
     * Confirme la réservation.
     */
    public void confirmer() {
        if (statut == StatutReservation.EN_ATTENTE) {
            statut = StatutReservation.CONFIRMEE;
        }
    }
    
    /**
     * Démarre la réservation (le bien est remis ou le service commence).
     */
    public void demarrer() {
        if (statut == StatutReservation.CONFIRMEE) {
            statut = StatutReservation.EN_COURS;
        }
    }
    
    /**
     * Termine la réservation.
     */
    public void terminer() {
        if (statut == StatutReservation.EN_COURS || statut == StatutReservation.CONFIRMEE) {
            statut = StatutReservation.TERMINEE;
        }
    }
    
    /**
     * Annule la réservation.
     */
    public void annuler() {
        if (statut == StatutReservation.EN_ATTENTE || statut == StatutReservation.CONFIRMEE) {
            statut = StatutReservation.ANNULEE;
        }
    }
    
    /**
     * Refuse la réservation.
     */
    public void refuser() {
        if (statut == StatutReservation.EN_ATTENTE) {
            statut = StatutReservation.REFUSEE;
        }
    }
    
    /**
     * Calcule la durée de la réservation en heures.
     * 
     * @return Durée en heures
     */
    public long getDureeEnHeures() {
        return Duration.between(dateDebut, dateFin).toHours();
    }
    
    /**
     * Vérifie si la réservation peut être évaluée.
     * 
     * @return true si la réservation est terminée
     */
    public boolean peutEtreEvaluee() {
        return statut == StatutReservation.TERMINEE;
    }
    
    // Getters et Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Annonce getAnnonce() {
        return annonce;
    }
    
    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }
    
    public Utilisateur getDemandeur() {
        return demandeur;
    }
    
    public void setDemandeur(Utilisateur demandeur) {
        this.demandeur = demandeur;
    }
    
    public Utilisateur getProprietaire() {
        return proprietaire;
    }
    
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
        this.prixTotal = calculerPrix();
    }
    
    public LocalDateTime getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
        this.prixTotal = calculerPrix();
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public StatutReservation getStatut() {
        return statut;
    }
    
    public double getPrixTotal() {
        return prixTotal;
    }
    
    public String getMessageAccompagnement() {
        return messageAccompagnement;
    }
    
    public void setMessageAccompagnement(String messageAccompagnement) {
        this.messageAccompagnement = messageAccompagnement;
    }
    
    public StrategyTarification getStrategyTarification() {
        return strategyTarification;
    }
    
    /**
     * Change la stratégie de tarification et recalcule le prix.
     * 
     * @param strategyTarification Nouvelle stratégie
     */
    public void setStrategyTarification(StrategyTarification strategyTarification) {
        this.strategyTarification = strategyTarification;
        this.prixTotal = calculerPrix();
    }
    
    @Override
    public String toString() {
        return String.format("Réservation[%s] %s\n  %s → %s\n  Demandeur: %s\n  Prix: %.2f€\n  Statut: %s",
                id,
                annonce.getTitre(),
                dateDebut.toLocalDate(),
                dateFin.toLocalDate(),
                demandeur.getNomComplet(),
                prixTotal,
                statut);
    }
}
