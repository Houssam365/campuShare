package com.campusshare.model;

/**
 * Énumération des différents statuts possibles pour une annonce.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public enum StatutAnnonce {

    ACTIVE("Active", "🟢"),
    RESERVEE("Réservée", "🟡"),
    INDISPONIBLE("Indisponible", "🔴"),
    EXPIREE("Expirée", "⏰"),
    TERMINEE("Terminée", "⚫"),
    SUPPRIMEE("Supprimée", "🗑️");
    
    private final String libelle;
    private final String icone;
    
    StatutAnnonce(String libelle, String icone) {
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
