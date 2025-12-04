package com.campusshare.model.annonce;

import com.campusshare.model.Categorie;
import com.campusshare.model.StatutAnnonce;
import com.campusshare.model.Utilisateur;
import com.campusshare.observer.Observable;
import com.campusshare.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant une annonce dans l'application.
 * Implémente le pattern Observer en tant que Subject (Observable).
 * 
 * Les sous-classes concrètes sont créées via le pattern Simple Factory.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public abstract class Annonce implements Observable {
    
    protected String id;
    protected String titre;
    protected String description;
    protected Utilisateur proprietaire;
    protected Categorie categorie;
    protected double prixBase;
    protected StatutAnnonce statut;
    protected LocalDateTime dateCreation;
    protected LocalDateTime dateModification;
    protected String localisation; // Ex: "Bâtiment A, Campus Nord"
    protected List<String> images; // URLs des images
    protected int nombreVues;
    
    // Pattern Observer - liste des observateurs intéressés par cette annonce
    private List<Observer> observers;
    
    /**
     * Constructeur de l'annonce.
     * 
     * @param id Identifiant unique
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     */
    public Annonce(String id, String titre, String description, 
                   Utilisateur proprietaire, Categorie categorie) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.proprietaire = proprietaire;
        this.categorie = categorie;
        this.prixBase = 0.0;
        this.statut = StatutAnnonce.ACTIVE;
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
        this.localisation = "";
        this.images = new ArrayList<>();
        this.nombreVues = 0;
        this.observers = new ArrayList<>();
    }
    
    // ==================== Pattern Observer - Implémentation ====================
    
    /**
     * Attache un observateur à cette annonce.
     * 
     * @param observer L'observateur à ajouter
     */
    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    /**
     * Détache un observateur de cette annonce.
     * 
     * @param observer L'observateur à retirer
     */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    
    /**
     * Notifie tous les observateurs d'un changement.
     * 
     * @param message Le message de notification
     */
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(this, message);
        }
    }
    
    // ==================== Méthodes métier ====================
    
    /**
     * Méthode abstraite pour obtenir le type d'annonce.
     * Chaque sous-classe retourne son type.
     * 
     * @return Le type d'annonce
     */
    public abstract TypeAnnonce getType();
    
    /**
     * Méthode abstraite pour afficher les détails spécifiques.
     * 
     * @return Chaîne avec les détails spécifiques au type
     */
    public abstract String getDetailsSpecifiques();
    
    /**
     * Incrémente le compteur de vues.
     */
    public void incrementerVues() {
        this.nombreVues++;
    }
    
    /**
     * Met à jour le statut et notifie les observateurs.
     * 
     * @param nouveauStatut Le nouveau statut
     */
    public void changerStatut(StatutAnnonce nouveauStatut) {
        StatutAnnonce ancienStatut = this.statut;
        this.statut = nouveauStatut;
        this.dateModification = LocalDateTime.now();
        
        // Notification aux observateurs
        notifyObservers(String.format("L'annonce '%s' est passée de %s à %s", 
                titre, ancienStatut, nouveauStatut));
    }
    
    /**
     * Vérifie si l'annonce est disponible pour une réservation.
     *
     * @return true si l'annonce est active
     */
    public boolean estDisponible() {
        return statut == StatutAnnonce.ACTIVE;
    }

    /**
     * Modifie la disponibilité de l'annonce.
     *
     * @param disponible true pour rendre disponible, false sinon
     */
    public void setEstDisponible(boolean disponible) {
        if (disponible && statut != StatutAnnonce.ACTIVE) {
            changerStatut(StatutAnnonce.ACTIVE);
        } else if (!disponible && statut == StatutAnnonce.ACTIVE) {
            changerStatut(StatutAnnonce.EXPIREE);
        }
    }

    /**
     * Retourne le prix estimé de l'annonce (alias pour prixBase).
     *
     * @return Le prix estimé
     */
    public double getPrixEstime() {
        return prixBase;
    }

    /**
     * Retourne l'auteur de l'annonce (alias pour proprietaire).
     *
     * @return L'utilisateur propriétaire
     */
    public Utilisateur getAuteur() {
        return proprietaire;
    }
    
    /**
     * Ajoute une image à l'annonce.
     * 
     * @param urlImage URL de l'image
     */
    public void ajouterImage(String urlImage) {
        images.add(urlImage);
        this.dateModification = LocalDateTime.now();
    }
    
    // ==================== Getters et Setters ====================
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
        this.dateModification = LocalDateTime.now();
        notifyObservers("Le titre de l'annonce a été modifié: " + titre);
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.dateModification = LocalDateTime.now();
    }
    
    public Utilisateur getProprietaire() {
        return proprietaire;
    }
    
    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
    }
    
    public Categorie getCategorie() {
        return categorie;
    }
    
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
        this.dateModification = LocalDateTime.now();
    }
    
    public double getPrixBase() {
        return prixBase;
    }
    
    public void setPrixBase(double prixBase) {
        this.prixBase = prixBase;
        this.dateModification = LocalDateTime.now();
        notifyObservers(String.format("Le prix de '%s' a été modifié: %.2f€", titre, prixBase));
    }
    
    public StatutAnnonce getStatut() {
        return statut;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public LocalDateTime getDateModification() {
        return dateModification;
    }
    
    public String getLocalisation() {
        return localisation;
    }
    
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
        this.dateModification = LocalDateTime.now();
    }
    
    public List<String> getImages() {
        return images;
    }
    
    public int getNombreVues() {
        return nombreVues;
    }
    
    public List<Observer> getObservers() {
        return observers;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s [%s]\n  %s\n  Prix: %.2f€ | %s | Vues: %d\n  Par: %s",
                getType().getIcone(),
                titre,
                statut,
                description.length() > 50 ? description.substring(0, 50) + "..." : description,
                prixBase,
                categorie,
                nombreVues,
                proprietaire.getNomComplet());
    }
}
