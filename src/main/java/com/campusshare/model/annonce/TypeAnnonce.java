package com.campusshare.model.annonce;

/**
 * Énumération des types d'annonces disponibles dans l'application.
 * Utilisée par la Simple Factory pour créer le bon type d'annonce.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public enum TypeAnnonce {
    
    BIEN("Bien à prêter/louer", "📦"),
    SERVICE("Service à proposer", "🛠️"),
    DON("Don", "🎁");
    
    private final String libelle;
    private final String icone;
    
    TypeAnnonce(String libelle, String icone) {
        this.libelle = libelle;
        this.icone = icone;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getIcone() {
        return icone;
    }
    
    @Override
    public String toString() {
        return icone + " " + libelle;
    }
}
