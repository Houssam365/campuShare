package com.campusshare.model;

import com.campusshare.model.annonce.Annonce;

/**
 * Classe représentant un étudiant de l'application CampusShare.
 * Un étudiant hérite d'Utilisateur et possède des points campus
 * utilisables pour les transactions.
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public class Etudiant extends Utilisateur {

    private String numEtudiant;
    private int soldePoints;
    private String campus;

    /**
     * Constructeur de l'étudiant.
     *
     * @param id Identifiant unique
     * @param nom Nom de famille
     * @param prenom Prénom
     * @param email Email universitaire
     * @param motDePasse Mot de passe hashé
     * @param numEtudiant Numéro étudiant
     * @param campus Nom du campus
     */
    public Etudiant(String id, String nom, String prenom, String email,
                    String motDePasse, String numEtudiant, String campus) {
        super(id, nom, prenom, email, motDePasse);
        this.numEtudiant = numEtudiant;
        this.campus = campus;
        this.soldePoints = 100; // Solde initial
    }

    /**
     * Crédite des points au compte de l'étudiant.
     *
     * @param montant Le montant de points à créditer
     */
    public void crediterPoints(int montant) {
        if (montant > 0) {
            this.soldePoints += montant;
            System.out.println("[CREDIT] +" + montant + " points -> Nouveau solde: " + this.soldePoints + " points");
        }
    }

    /**
     * Débite des points du compte de l'étudiant.
     *
     * @param montant Le montant de points à débiter
     * @return true si le débit a réussi, false si solde insuffisant
     */
    public boolean debiterPoints(int montant) {
        if (montant <= 0) {
            return false;
        }

        if (this.soldePoints >= montant) {
            this.soldePoints -= montant;
            System.out.println("[DEBIT] -" + montant + " points -> Nouveau solde: " + this.soldePoints + " points");
            return true;
        } else {
            System.out.println("[ECHEC] Solde insuffisant: " + this.soldePoints + " points (requis: " + montant + ")");
            return false;
        }
    }

    /**
     * Demande d'échange pour une annonce.
     *
     * @param annonce L'annonce concernée
     */
    public void demanderEchange(Annonce annonce) {
        System.out.println(getNomComplet() + " demande un échange pour: " + annonce.getTitre());
    }

    /**
     * Calcule la réputation de l'étudiant.
     *
     * @return La réputation (note moyenne)
     */
    public double calculerReputation() {
        return getReputation();
    }

    // Getters et Setters

    public String getNumEtudiant() {
        return numEtudiant;
    }

    public void setNumEtudiant(String numEtudiant) {
        this.numEtudiant = numEtudiant;
    }

    public int getSoldePoints() {
        return soldePoints;
    }

    public void setSoldePoints(int soldePoints) {
        this.soldePoints = soldePoints;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    @Override
    public String toString() {
        return String.format("Etudiant[%s] %s - Campus: %s - Points: %d - Rep: %.1f★",
                numEtudiant, getNomComplet(), campus, soldePoints, getReputation());
    }
}
