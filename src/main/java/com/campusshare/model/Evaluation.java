package com.campusshare.model;

import java.time.LocalDateTime;

/**
 * Classe représentant une évaluation laissée après une transaction.
 * Les évaluations permettent de construire la réputation des utilisateurs.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class Evaluation {
    
    private String id;
    private Utilisateur evaluateur;      // Celui qui évalue
    private Utilisateur evaluer;         // Celui qui est évalué
    private int note;                    // Note de 1 à 5
    private String commentaire;
    private LocalDateTime dateEvaluation;
    private String reservationId;        // Référence à la réservation concernée
    
    /**
     * Constructeur de l'évaluation.
     * 
     * @param id Identifiant unique
     * @param evaluateur L'utilisateur qui évalue
     * @param evaluer L'utilisateur évalué
     * @param note Note de 1 à 5
     * @param commentaire Commentaire optionnel
     * @param reservationId ID de la réservation associée
     */
    public Evaluation(String id, Utilisateur evaluateur, Utilisateur evaluer, 
                      int note, String commentaire, String reservationId) {
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        this.id = id;
        this.evaluateur = evaluateur;
        this.evaluer = evaluer;
        this.note = note;
        this.commentaire = commentaire;
        this.reservationId = reservationId;
        this.dateEvaluation = LocalDateTime.now();
    }
    
    /**
     * Retourne une représentation textuelle de la note avec des étoiles.
     * 
     * @return Chaîne d'étoiles
     */
    public String getNoteEnEtoiles() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(i < note ? "★" : "☆");
        }
        return sb.toString();
    }
    
    // Getters et Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Utilisateur getEvaluateur() {
        return evaluateur;
    }
    
    public void setEvaluateur(Utilisateur evaluateur) {
        this.evaluateur = evaluateur;
    }
    
    public Utilisateur getEvaluer() {
        return evaluer;
    }
    
    public void setEvaluer(Utilisateur evaluer) {
        this.evaluer = evaluer;
    }
    
    public int getNote() {
        return note;
    }
    
    public void setNote(int note) {
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        this.note = note;
    }
    
    public String getCommentaire() {
        return commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public LocalDateTime getDateEvaluation() {
        return dateEvaluation;
    }
    
    public String getReservationId() {
        return reservationId;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s\n\"%s\" - par %s", 
                getNoteEnEtoiles(), 
                dateEvaluation.toLocalDate(),
                commentaire,
                evaluateur.getNomComplet());
    }
}
