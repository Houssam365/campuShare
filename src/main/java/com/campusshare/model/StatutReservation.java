package com.campusshare.model;

/**
 * Énumération des différents statuts possibles pour une réservation.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public enum StatutReservation {
    
    EN_ATTENTE("En attente", "⏳"),
    CONFIRMEE("Confirmée", "✅"),
    EN_COURS("En cours", "🔄"),
    TERMINEE("Terminée", "✔️"),
    ANNULEE("Annulée", "❌"),
    REFUSEE("Refusée", "🚫");
    
    private final String libelle;
    private final String icone;
    
    StatutReservation(String libelle, String icone) {
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
