package com.campusshare.model;

/**
 * Classe représentant une catégorie d'annonces.
 * Les catégories permettent de classer et filtrer les annonces.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class Categorie {
    
    private String id;
    private String nom;
    private String description;
    private String icone; // Emoji ou nom d'icône
    
    /**
     * Constructeur de la catégorie.
     * 
     * @param id Identifiant unique
     * @param nom Nom de la catégorie
     * @param description Description de la catégorie
     * @param icone Icône représentative
     */
    public Categorie(String id, String nom, String description, String icone) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.icone = icone;
    }
    
    /**
     * Constructeur simplifié.
     * 
     * @param id Identifiant unique
     * @param nom Nom de la catégorie
     */
    public Categorie(String id, String nom) {
        this(id, nom, "", "📦");
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIcone() {
        return icone;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
    
    @Override
    public String toString() {
        return icone + " " + nom;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categorie categorie = (Categorie) obj;
        return id.equals(categorie.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
