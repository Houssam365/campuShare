package com.campusshare.model;

import com.campusshare.model.annonce.Annonce;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un utilisateur de l'application CampusShare.
 * Un utilisateur est un étudiant du campus qui peut publier des annonces,
 * effectuer des réservations et recevoir des évaluations.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class Utilisateur {
    
    private String id;
    private String nom;
    private String prenom;
    private String email; // Email universitaire (.edu)
    private String motDePasse;
    private double reputation; // Note moyenne (0-5)
    private int nombreEvaluations;
    private List<Annonce> annoncesPubliees;
    private List<Reservation> reservationsEffectuees;
    private List<Evaluation> evaluationsRecues;
    private List<String> categoriesSuivies; // Pour le pattern Observer
    
    /**
     * Constructeur de l'utilisateur.
     * 
     * @param id Identifiant unique
     * @param nom Nom de famille
     * @param prenom Prénom
     * @param email Email universitaire
     * @param motDePasse Mot de passe hashé
     */
    public Utilisateur(String id, String nom, String prenom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.reputation = 0.0;
        this.nombreEvaluations = 0;
        this.annoncesPubliees = new ArrayList<>();
        this.reservationsEffectuees = new ArrayList<>();
        this.evaluationsRecues = new ArrayList<>();
        this.categoriesSuivies = new ArrayList<>();
    }
    
    /**
     * Ajoute une évaluation et met à jour la réputation.
     * 
     * @param evaluation L'évaluation à ajouter
     */
    public void ajouterEvaluation(Evaluation evaluation) {
        evaluationsRecues.add(evaluation);
        // Recalcul de la moyenne
        double somme = reputation * nombreEvaluations + evaluation.getNote();
        nombreEvaluations++;
        reputation = somme / nombreEvaluations;
    }
    
    /**
     * Vérifie si l'email est un email universitaire valide.
     * 
     * @return true si l'email est valide
     */
    public boolean estEmailUniversitaireValide() {
        return email != null && (email.endsWith(".edu") || 
               email.endsWith(".univ-") || 
               email.contains("@etu.") ||
               email.contains("@student."));
    }
    
    /**
     * Ajoute une catégorie à suivre (pour les notifications).
     * 
     * @param categorie La catégorie à suivre
     */
    public void suivreCategorie(String categorie) {
        if (!categoriesSuivies.contains(categorie)) {
            categoriesSuivies.add(categorie);
        }
    }
    
    /**
     * Retire une catégorie suivie.
     * 
     * @param categorie La catégorie à ne plus suivre
     */
    public void nePlusSuivreCategorie(String categorie) {
        categoriesSuivies.remove(categorie);
    }
    
    // Getters et Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public double getReputation() {
        return reputation;
    }
    
    public int getNombreEvaluations() {
        return nombreEvaluations;
    }
    
    public List<Annonce> getAnnoncesPubliees() {
        return annoncesPubliees;
    }
    
    public List<Reservation> getReservationsEffectuees() {
        return reservationsEffectuees;
    }
    
    public List<Evaluation> getEvaluationsRecues() {
        return evaluationsRecues;
    }
    
    public List<String> getCategoriesSuivies() {
        return categoriesSuivies;
    }
    
    /**
     * Retourne le nom complet de l'utilisateur.
     * 
     * @return Prénom + Nom
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    @Override
    public String toString() {
        return String.format("Utilisateur[%s] %s %s (%.1f★ - %d avis)", 
                id, prenom, nom, reputation, nombreEvaluations);
    }
}
