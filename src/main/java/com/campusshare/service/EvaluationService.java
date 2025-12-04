package com.campusshare.service;

import com.campusshare.model.Evaluation;
import com.campusshare.model.Reservation;
import com.campusshare.model.StatutReservation;
import com.campusshare.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service de gestion des évaluations.
 * Permet aux utilisateurs de s'évaluer mutuellement après une transaction.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class EvaluationService {
    
    private List<Evaluation> evaluations;
    
    /**
     * Constructeur du service.
     */
    public EvaluationService() {
        this.evaluations = new ArrayList<>();
    }
    
    /**
     * Crée une évaluation après une réservation terminée.
     * 
     * @param reservation La réservation concernée
     * @param evaluateur Celui qui évalue
     * @param note Note de 1 à 5
     * @param commentaire Commentaire
     * @return L'évaluation créée
     */
    public Evaluation evaluer(Reservation reservation, Utilisateur evaluateur,
                               int note, String commentaire) {
        
        // Vérifications
        if (!reservation.peutEtreEvaluee()) {
            throw new IllegalStateException(
                    "La réservation doit être terminée pour être évaluée");
        }
        
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        
        // Déterminer qui est évalué
        Utilisateur evaluer;
        if (evaluateur.equals(reservation.getDemandeur())) {
            evaluer = reservation.getProprietaire();
        } else if (evaluateur.equals(reservation.getProprietaire())) {
            evaluer = reservation.getDemandeur();
        } else {
            throw new IllegalArgumentException(
                    "L'évaluateur doit être participant à la réservation");
        }
        
        // Vérifier que l'évaluateur n'a pas déjà évalué cette réservation
        if (aDejaEvalue(evaluateur, reservation)) {
            throw new IllegalStateException("Vous avez déjà évalué cette réservation");
        }
        
        // Créer l'évaluation
        String id = genererIdEvaluation();
        Evaluation evaluation = new Evaluation(id, evaluateur, evaluer, 
                note, commentaire, reservation.getId());
        
        evaluations.add(evaluation);
        
        // Mettre à jour la réputation de l'utilisateur évalué
        evaluer.ajouterEvaluation(evaluation);
        
        System.out.println("⭐ Évaluation créée:");
        System.out.println("   " + evaluateur.getNomComplet() + " → " + evaluer.getNomComplet());
        System.out.println("   " + evaluation.getNoteEnEtoiles());
        System.out.println("   \"" + commentaire + "\"");
        System.out.println("   Nouvelle réputation de " + evaluer.getNomComplet() + 
                ": " + String.format("%.2f", evaluer.getReputation()) + "★");
        
        return evaluation;
    }
    
    /**
     * Évaluation rapide du propriétaire par le demandeur.
     */
    public Evaluation evaluerProprietaire(Reservation reservation, int note, String commentaire) {
        return evaluer(reservation, reservation.getDemandeur(), note, commentaire);
    }
    
    /**
     * Évaluation rapide du demandeur par le propriétaire.
     */
    public Evaluation evaluerDemandeur(Reservation reservation, int note, String commentaire) {
        return evaluer(reservation, reservation.getProprietaire(), note, commentaire);
    }
    
    /**
     * Vérifie si un utilisateur a déjà évalué une réservation.
     */
    public boolean aDejaEvalue(Utilisateur evaluateur, Reservation reservation) {
        return evaluations.stream()
                .anyMatch(e -> e.getEvaluateur().equals(evaluateur) && 
                        e.getReservationId().equals(reservation.getId()));
    }
    
    /**
     * Retourne les évaluations reçues par un utilisateur.
     */
    public List<Evaluation> getEvaluationsRecues(Utilisateur utilisateur) {
        return evaluations.stream()
                .filter(e -> e.getEvaluer().equals(utilisateur))
                .collect(Collectors.toList());
    }
    
    /**
     * Retourne les évaluations données par un utilisateur.
     */
    public List<Evaluation> getEvaluationsDonnees(Utilisateur utilisateur) {
        return evaluations.stream()
                .filter(e -> e.getEvaluateur().equals(utilisateur))
                .collect(Collectors.toList());
    }
    
    /**
     * Retourne les évaluations d'une réservation.
     */
    public List<Evaluation> getEvaluationsReservation(Reservation reservation) {
        return evaluations.stream()
                .filter(e -> e.getReservationId().equals(reservation.getId()))
                .collect(Collectors.toList());
    }
    
    /**
     * Calcule la note moyenne d'un utilisateur.
     */
    public double calculerMoyenne(Utilisateur utilisateur) {
        List<Evaluation> evals = getEvaluationsRecues(utilisateur);
        if (evals.isEmpty()) {
            return 0.0;
        }
        return evals.stream()
                .mapToInt(Evaluation::getNote)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Affiche un résumé des évaluations d'un utilisateur.
     */
    public String getResumeEvaluations(Utilisateur utilisateur) {
        List<Evaluation> evals = getEvaluationsRecues(utilisateur);
        if (evals.isEmpty()) {
            return "Aucune évaluation pour le moment";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("📊 %s - %.1f★ (%d avis)\n", 
                utilisateur.getNomComplet(),
                utilisateur.getReputation(),
                evals.size()));
        
        // Distribution des notes
        int[] distribution = new int[5];
        for (Evaluation eval : evals) {
            distribution[eval.getNote() - 1]++;
        }
        
        for (int i = 4; i >= 0; i--) {
            sb.append(String.format("   %d★: %d avis\n", i + 1, distribution[i]));
        }
        
        return sb.toString();
    }
    
    /**
     * Génère un ID unique pour une évaluation.
     */
    private String genererIdEvaluation() {
        return "EVAL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public List<Evaluation> getEvaluations() {
        return evaluations;
    }
    
    public int getNombreEvaluations() {
        return evaluations.size();
    }
}
