package com.campusshare.model;

/**
 * Énumération représentant les différents statuts d'une transaction.
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public enum StatutTransaction {
    /**
     * Transaction en attente de traitement
     */
    EN_ATTENTE("En attente"),

    /**
     * Transaction validée et complétée
     */
    VALIDE("Validée"),

    /**
     * Transaction refusée
     */
    REFUSE("Refusée"),

    /**
     * Transaction annulée
     */
    ANNULE("Annulée");

    private final String libelle;

    StatutTransaction(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
