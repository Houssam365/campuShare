package com.campusshare.model.annonce;

import com.campusshare.model.Categorie;
import com.campusshare.model.Utilisateur;

/**
 * Classe représentant une annonce de bien à prêter ou louer.
 * Exemples: livres, vélos, matériel électronique, meubles, etc.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class BienAnnonce extends Annonce {
    
    private String etat; // Neuf, Très bon état, Bon état, Usé
    private String marque;
    private String modele;
    private boolean cautionRequise;
    private double montantCaution;
    private int dureeMaxPretJours; // Durée maximale de prêt en jours
    
    /**
     * Constructeur de BienAnnonce.
     * 
     * @param id Identifiant unique
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     */
    public BienAnnonce(String id, String titre, String description,
                       Utilisateur proprietaire, Categorie categorie) {
        super(id, titre, description, proprietaire, categorie);
        this.etat = "Bon état";
        this.cautionRequise = false;
        this.montantCaution = 0.0;
        this.dureeMaxPretJours = 7; // Par défaut 1 semaine
    }
    
    @Override
    public TypeAnnonce getType() {
        return TypeAnnonce.BIEN;
    }
    
    @Override
    public String getDetailsSpecifiques() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Détails du bien ===\n");
        sb.append("État: ").append(etat).append("\n");
        if (marque != null && !marque.isEmpty()) {
            sb.append("Marque: ").append(marque).append("\n");
        }
        if (modele != null && !modele.isEmpty()) {
            sb.append("Modèle: ").append(modele).append("\n");
        }
        sb.append("Durée max de prêt: ").append(dureeMaxPretJours).append(" jours\n");
        if (cautionRequise) {
            sb.append("⚠️ Caution requise: ").append(String.format("%.2f€", montantCaution)).append("\n");
        }
        return sb.toString();
    }
    
    // Getters et Setters spécifiques
    
    public String getEtat() {
        return etat;
    }
    
    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    public String getMarque() {
        return marque;
    }
    
    public void setMarque(String marque) {
        this.marque = marque;
    }
    
    public String getModele() {
        return modele;
    }
    
    public void setModele(String modele) {
        this.modele = modele;
    }
    
    public boolean isCautionRequise() {
        return cautionRequise;
    }
    
    public void setCautionRequise(boolean cautionRequise) {
        this.cautionRequise = cautionRequise;
    }
    
    public double getMontantCaution() {
        return montantCaution;
    }
    
    public void setMontantCaution(double montantCaution) {
        this.montantCaution = montantCaution;
        if (montantCaution > 0) {
            this.cautionRequise = true;
        }
    }
    
    public int getDureeMaxPretJours() {
        return dureeMaxPretJours;
    }
    
    public void setDureeMaxPretJours(int dureeMaxPretJours) {
        this.dureeMaxPretJours = dureeMaxPretJours;
    }
}
