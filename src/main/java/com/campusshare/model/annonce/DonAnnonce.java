package com.campusshare.model.annonce;

import com.campusshare.model.Categorie;
import com.campusshare.model.Utilisateur;

/**
 * Classe représentant une annonce de don gratuit.
 * Le don est un partage solidaire entre étudiants.
 * Exemples: vêtements, livres, nourriture non périssable, petit mobilier, etc.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class DonAnnonce extends Annonce {
    
    private String etatObjet; // Neuf, Très bon état, Bon état, À rénover
    private String raisonDon; // Déménagement, Fin d'études, Surplus, etc.
    private boolean retraitSurPlace; // L'objet doit être récupéré sur place
    private String conditionsRecuperation; // Conditions particulières
    private int quantiteDisponible; // Nombre d'objets disponibles
    
    /**
     * Constructeur de DonAnnonce.
     * 
     * @param id Identifiant unique
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     */
    public DonAnnonce(String id, String titre, String description,
                      Utilisateur proprietaire, Categorie categorie) {
        super(id, titre, description, proprietaire, categorie);
        this.prixBase = 0.0; // Un don est toujours gratuit
        this.etatObjet = "Bon état";
        this.retraitSurPlace = true;
        this.quantiteDisponible = 1;
    }
    
    @Override
    public TypeAnnonce getType() {
        return TypeAnnonce.DON;
    }
    
    @Override
    public String getDetailsSpecifiques() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Détails du don ===\n");
        sb.append("🎁 DON GRATUIT\n");
        sb.append("État: ").append(etatObjet).append("\n");
        sb.append("Quantité disponible: ").append(quantiteDisponible).append("\n");
        
        if (raisonDon != null && !raisonDon.isEmpty()) {
            sb.append("Raison du don: ").append(raisonDon).append("\n");
        }
        
        sb.append("Retrait sur place: ").append(retraitSurPlace ? "Oui" : "Non").append("\n");
        
        if (conditionsRecuperation != null && !conditionsRecuperation.isEmpty()) {
            sb.append("Conditions: ").append(conditionsRecuperation).append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Pour un don, le prix est toujours 0.
     * Cette méthode est surchargée pour garantir la gratuité.
     */
    @Override
    public void setPrixBase(double prixBase) {
        // Un don est toujours gratuit
        super.setPrixBase(0.0);
    }
    
    /**
     * Décrémente la quantité disponible lors d'une récupération.
     * 
     * @return true si un objet a été réservé, false si plus rien n'est disponible
     */
    public boolean reserverUnite() {
        if (quantiteDisponible > 0) {
            quantiteDisponible--;
            if (quantiteDisponible == 0) {
                notifyObservers("Le don '" + getTitre() + "' n'est plus disponible.");
            }
            return true;
        }
        return false;
    }
    
    // Getters et Setters spécifiques
    
    public String getEtatObjet() {
        return etatObjet;
    }
    
    public void setEtatObjet(String etatObjet) {
        this.etatObjet = etatObjet;
    }
    
    public String getRaisonDon() {
        return raisonDon;
    }
    
    public void setRaisonDon(String raisonDon) {
        this.raisonDon = raisonDon;
    }
    
    public boolean isRetraitSurPlace() {
        return retraitSurPlace;
    }
    
    public void setRetraitSurPlace(boolean retraitSurPlace) {
        this.retraitSurPlace = retraitSurPlace;
    }
    
    public String getConditionsRecuperation() {
        return conditionsRecuperation;
    }
    
    public void setConditionsRecuperation(String conditionsRecuperation) {
        this.conditionsRecuperation = conditionsRecuperation;
    }
    
    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }
    
    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }
}
